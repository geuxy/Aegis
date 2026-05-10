package gg.aegis.player.data;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientWindowConfirmation;

import gg.aegis.player.AegisPlayer;
import gg.aegis.util.PacketProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NetworkData implements PacketProcessor {

    private final Map<Short, Runnable> confirmations = new HashMap<>();

    private short transactionId;

    @Override
    public void process(AegisPlayer player, ProtocolPacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.WINDOW_CONFIRMATION) {
            WrapperPlayClientWindowConfirmation wrapper = new WrapperPlayClientWindowConfirmation((PacketReceiveEvent) event);

            Optional.ofNullable(confirmations.remove(wrapper.getActionId())).ifPresent(Runnable::run);
        }
    }

    public void confirm(Runnable runnable) {
        this.transactionId = transactionId > 0 ? Short.MIN_VALUE : (short) (transactionId + 1);
        this.confirmations.put(transactionId, runnable);
    }

}
