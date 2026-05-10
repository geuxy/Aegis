package gg.aegis.player.data;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityVelocity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerPositionAndLook;

import lombok.Getter;

import lombok.Setter;
import gg.aegis.player.AegisPlayer;
import gg.aegis.util.PacketProcessor;
import gg.aegis.util.collection.EvictedList;
import gg.aegis.util.math.MathUtil;
import gg.aegis.util.math.MinecraftMath;

import gg.aegis.util.entity.BoundingBox;
import gg.aegis.util.other.Vector3;
import gg.aegis.util.world.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayDeque;
import java.util.Deque;

@Getter
public class PositionData implements PacketProcessor {

    private double x, y, z, lastX, lastY, lastZ;
    private double deltaX, deltaY, deltaZ, lastDeltaX, lastDeltaY, lastDeltaZ;
    private double speed, lastSpeed;

    private boolean clientGround, lastClientGround;
    private boolean mathGround, lastMathGround;
    private boolean serverGround, lastServerGround;
    @Setter
    private boolean predictGround, lastPredictGround;

    private boolean positionChanged;

    private double velocityX, velocityY, velocityZ;

    private final EvictedList<Vector3> previousLocations = new EvictedList<>(20);

    private final Deque<Vector3> teleports = new ArrayDeque<>();

    private float slipperiness, lastSlipperiness;
    private boolean onLoadedBlock;

    @Override
    public void process(AegisPlayer player, ProtocolPacketEvent event) {
        if(WrapperPlayClientPlayerFlying.isFlying(event.getPacketType())) {
            WrapperPlayClientPlayerFlying wrapper = new WrapperPlayClientPlayerFlying((PacketReceiveEvent) event);

            this.positionChanged = wrapper.hasPositionChanged();

            this.lastX = x;
            this.lastY = y;
            this.lastZ = z;
            this.lastDeltaX = deltaX;
            this.lastDeltaY = deltaY;
            this.lastDeltaZ = deltaZ;
            this.lastSpeed = speed;

            if(positionChanged) {
                this.x = wrapper.getLocation().getX();
                this.y = wrapper.getLocation().getY();
                this.z = wrapper.getLocation().getZ();
            }

            this.deltaX = x - lastX;
            this.deltaY = y - lastY;
            this.deltaZ = z - lastZ;
            this.speed = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

            this.lastClientGround = clientGround;
            this.lastMathGround = mathGround;
            this.lastServerGround = serverGround;
            this.clientGround = wrapper.isOnGround();
            this.mathGround = this.y % 0.015625 == 0;
            this.serverGround = false; // Do later during bounding box part

            player.onFlying();

            Location location = new Location(player.getRaw().getWorld(), x, y, z);

            Block block = BlockUtil.getBlock(new Location(player.getRaw().getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));

            // Climbables
            if (block != null) {
                player.setClimbing(player.isClimbing() || BlockUtil.isClimbable(block.getType()));
            }

            below:
            {
                Block blockBelow = BlockUtil.getBlock(new Location(player.getRaw().getWorld(), Math.floor(x), Math.floor(y - 1), Math.floor(z)));

                if (blockBelow == null) {
                    break below;
                }

                Material material = blockBelow.getType();
                Location zeroBlock = blockBelow.getLocation().clone();
                zeroBlock.setY(0);

                player.setOnSlime(player.isOnSlime() || material == Material.SLIME_BLOCK);
                player.setOnSoulSand(player.isOnSoulSand() || material == Material.SOUL_SAND);
                player.setOnIce(player.isOnIce() || BlockUtil.isIce(material));
                this.lastSlipperiness = this.slipperiness;
                this.slipperiness = BlockUtil.getSlipperiness(player);
                this.onLoadedBlock = BlockUtil.isLoaded(zeroBlock);
            }

            teleport:
            {
                if (!(wrapper.hasPositionChanged() && wrapper.hasRotationChanged() && !wrapper.isOnGround())) {
                    break teleport;
                }

                for (Vector3 vec : teleports) {
                    if (vec.getX() == x && vec.getY() == y && vec.getZ() == z) {
                        player.setTeleporting(true);
                        this.teleports.remove(vec);
                    }
                }
            }

            this.handleBoundingBox(player);

        } else if(event.getPacketType() == PacketType.Play.Server.PLAYER_POSITION_AND_LOOK) {
            WrapperPlayServerPlayerPositionAndLook wrapper = new WrapperPlayServerPlayerPositionAndLook((PacketSendEvent) event);

            this.teleports.add(new Vector3(wrapper.getX(), wrapper.getY(), wrapper.getZ()));

        } else if(event.getPacketType() == PacketType.Play.Server.ENTITY_VELOCITY) {
            WrapperPlayServerEntityVelocity wrapper = new WrapperPlayServerEntityVelocity((PacketSendEvent) event);

            if(wrapper.getEntityId() == player.getRaw().getEntityId()) {
                player.data(NetworkData.class).confirm(() -> {
                    this.velocityX = wrapper.getVelocity().getX();
                    this.velocityY = wrapper.getVelocity().getY();
                    this.velocityZ = wrapper.getVelocity().getZ();
                    player.setTakingVelocity(true);
                });
            }
        }
    }

    private synchronized void handleBoundingBox(AegisPlayer player) {
        BoundingBox boundingBox = new BoundingBox(x - 0.3D, y, z - 0.3D, x + 0.3D, y + 1.8D, z + 0.3D);

        if(!positionChanged) {
            boundingBox.expand(MathUtil.UNCERTAIN_VALUE, MathUtil.UNCERTAIN_VALUE, MathUtil.UNCERTAIN_VALUE);
        }

        BoundingBox expandedBoundingBox = boundingBox.expand(0, 1, 0);

        expandedBoundingBox.checkSurrounding(player.getRaw().getWorld(), block -> {
            Material material = block.getType();
            String name = material.name();

            double flooredMinY = MinecraftMath.floor_double(boundingBox.minY);
            double flooredMaxY = MinecraftMath.floor_double(boundingBox.maxY);

            if (block.getY() < flooredMinY) {
                this.serverGround |= BlockUtil.isSolid(material);
                player.setOnIrregularBlock(player.isOnIrregularBlock() || BlockUtil.isIrregular(name));

            } else {
                if(block.getY() > flooredMaxY) {
                    player.setBlockAbove(player.isBlockAbove() || BlockUtil.isSolid(material));

                } else {
                    player.setInLiquid(player.isInLiquid() || BlockUtil.isLiquid(material));
                    player.setInWeb(player.isInWeb() || material == Material.WEB);
                    player.setOnPiston(player.isOnPiston() || BlockUtil.isPiston(material));
                }
            }
        });
    }

}
