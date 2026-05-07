package gg.aegis.player.data;

import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import gg.aegis.player.AegisPlayer;

@FunctionalInterface
public interface PacketProcessor {

    void process(AegisPlayer player, ProtocolPacketEvent event);

}
