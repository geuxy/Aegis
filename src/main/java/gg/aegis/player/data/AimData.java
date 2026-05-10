package gg.aegis.player.data;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import lombok.Getter;
import gg.aegis.player.AegisPlayer;
import gg.aegis.util.PacketProcessor;
import gg.aegis.util.collection.EvictedList;
import gg.aegis.util.other.Vector2;

@Getter
public class AimData implements PacketProcessor {

    private final EvictedList<Vector2> pastRotations = new EvictedList<>(200);
    private final EvictedList<Vector2> pastDeltas = new EvictedList<>(60);

    private float yaw, pitch, lastYaw, lastPitch;
    private float deltaYaw, deltaPitch, lastDeltaYaw, lastDeltaPitch;

    private boolean rotating, lastRotating;

    @Override
    public void process(AegisPlayer player, ProtocolPacketEvent event) {
        if(WrapperPlayClientPlayerFlying.isFlying(event.getPacketType())) {
            WrapperPlayClientPlayerFlying wrapper = new WrapperPlayClientPlayerFlying((PacketReceiveEvent) event);

            this.lastYaw = this.yaw;
            this.lastPitch = this.pitch;
            this.lastRotating = this.rotating;

            if(wrapper.hasRotationChanged()) {
                this.yaw = wrapper.getLocation().getYaw();
                this.pitch = wrapper.getLocation().getPitch();
                this.rotating = true;

            } else {
                this.rotating = false;
            }

            this.lastDeltaYaw = this.deltaYaw;
            this.lastDeltaPitch = this.deltaPitch;
            this.deltaYaw = this.yaw - this.lastYaw;
            this.deltaPitch = this.pitch - this.lastPitch;

            this.pastRotations.add(new Vector2(this.yaw, this.pitch));
            this.pastDeltas.add(new Vector2(this.deltaYaw, this.deltaPitch));
        }
    }

}
