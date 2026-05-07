package gg.aegis.listeners;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;

import gg.aegis.Anticheat;
import gg.aegis.player.AegisPlayer;

public class PacketEventsListener extends PacketListenerAbstract {

    public PacketEventsListener() {
        super(PacketListenerPriority.LOWEST);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        AegisPlayer player = Anticheat.INSTANCE.getPlayerManager().get(event.getUser().getUUID());

        if(player != null) {
            player.handle(event);
        }
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        AegisPlayer player = Anticheat.INSTANCE.getPlayerManager().get(event.getUser().getUUID());

        if(player != null) {
            player.handle(event);
        }

    }

}
