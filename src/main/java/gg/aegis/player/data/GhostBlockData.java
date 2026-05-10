package gg.aegis.player.data;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import lombok.Getter;
import lombok.Setter;
import gg.aegis.player.AegisPlayer;
import gg.aegis.util.PacketProcessor;
import gg.aegis.util.collection.EvictedList;

@Getter @Setter
public class GhostBlockData implements PacketProcessor {

    private final EvictedList<Vector3i> placements = new EvictedList<>(6);

    private int fakeCollisionTicks;

    @Override
    public void process(AegisPlayer player, ProtocolPacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.PLAYER_FLYING) {
            PositionData positionData = player.data(PositionData.class);

            if(positionData.isServerGround()) {
                this.fakeCollisionTicks = 0;

            } else {
                if(positionData.isClientGround() && positionData.isMathGround()) {
                    boolean exempt = false;

                    for(Vector3i pos : placements) {
                        boolean x = Math.floor(positionData.getX()) == pos.getX();
                        boolean z = Math.floor(positionData.getZ()) == pos.getZ();

                        exempt |= x && z;
                    }

                    if(!exempt) {
                        this.fakeCollisionTicks = Math.min(5, fakeCollisionTicks + 1);
                    }
                }
            }
        } else if(event.getPacketType() == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {
            WrapperPlayClientPlayerBlockPlacement wrapper = new WrapperPlayClientPlayerBlockPlacement((PacketReceiveEvent) event);

            this.placements.add(wrapper.getBlockPosition());
        }
    }

}
