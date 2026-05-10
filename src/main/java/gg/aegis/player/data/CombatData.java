package gg.aegis.player.data;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;

import lombok.Getter;
import gg.aegis.player.AegisPlayer;
import gg.aegis.util.PacketProcessor;

import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class CombatData implements PacketProcessor {

    private final CopyOnWriteArrayList<Number> swings = new CopyOnWriteArrayList<>();

    private int clicksPerSecond;

    @Override
    public void process(AegisPlayer player, ProtocolPacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.ANIMATION) {
            this.swings.add(event.getTimestamp());
            this.clicksPerSecond = swings.stream().mapToInt(Number::intValue).sum();

        } else if(event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity wrapper = new WrapperPlayClientInteractEntity((PacketReceiveEvent) event);

        } else if(event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION
                || event.getPacketType() == PacketType.Play.Client.PLAYER_ROTATION
                || event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION) {
            long current = System.currentTimeMillis();

            if(!swings.isEmpty()) {
                swings.stream().filter(s -> current - s.longValue() > 1000).forEach(swings::remove);
            }
        }
    }

}
