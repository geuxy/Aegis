package gg.aegis.player.data;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.potion.PotionType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerAbilities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEffect;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerRemoveEntityEffect;

import lombok.Getter;

import gg.aegis.player.AegisPlayer;
import gg.aegis.util.PacketProcessor;

import java.util.concurrent.ConcurrentHashMap;

@Getter
public class AbilityData implements PacketProcessor {

    private final ConcurrentHashMap<PotionType, Integer> effects = new ConcurrentHashMap<>();

    private float walkSpeed;

    private ClientVersion version;

    @Override
    public void process(AegisPlayer player, ProtocolPacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Server.REMOVE_ENTITY_EFFECT) {
            WrapperPlayServerRemoveEntityEffect wrapper = new WrapperPlayServerRemoveEntityEffect((PacketSendEvent) event);

            if(wrapper.getEntityId() == player.getRaw().getEntityId()) {
                this.effects.remove(wrapper.getPotionType());
            }

        } else if(event.getPacketType() == PacketType.Play.Server.ENTITY_EFFECT) {
            WrapperPlayServerEntityEffect wrapper = new WrapperPlayServerEntityEffect((PacketSendEvent) event);

            if(wrapper.getEntityId() == player.getRaw().getEntityId()) {
                this.effects.put(wrapper.getPotionType(), wrapper.getEffectAmplifier());
            }

        } else if(event.getPacketType() == PacketType.Play.Client.PLAYER_ABILITIES) {
            WrapperPlayClientPlayerAbilities wrapper = new WrapperPlayClientPlayerAbilities((PacketReceiveEvent) event);

            this.walkSpeed = wrapper.getWalkSpeed().isPresent() ? wrapper.getWalkSpeed().get() : 0.1F;
            this.version = wrapper.getClientVersion();
        }
    }

    public int getEffectAmplifier(PotionType type) {
        Integer amplifier = effects.get(type);

        if(amplifier != null) {
            return amplifier;
        }

        return 0;
    }

}
