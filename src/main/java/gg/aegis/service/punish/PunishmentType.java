package gg.aegis.service.punish;

import gg.aegis.player.AegisPlayer;
import gg.aegis.util.text.TextBuilder;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

import java.util.function.BiConsumer;

@RequiredArgsConstructor
public enum PunishmentType {

    NONE((p, m) -> {}),
    KICK((p, m) -> p.getRaw().kickPlayer(m == null ? "Disconnected" : m.flatten())),
    BAN((p, m) -> {
        Bukkit.getBannedPlayers().add(p.getRaw());
        PunishmentType.KICK.punish(p, m);
    });

    private final BiConsumer<AegisPlayer, TextBuilder> punishment;

    public void punish(AegisPlayer player, TextBuilder message) {
        this.punishment.accept(player, message);
    }

}
