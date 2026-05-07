package gg.aegis.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private final Map<UUID, AegisPlayer> playerMap = new HashMap<>();

    public PlayerManager() {
        Bukkit.getOnlinePlayers().forEach(this::add);
    }

    public void add(Player player) {
        this.playerMap.put(player.getUniqueId(), new AegisPlayer(player));
    }

    public void remove(UUID uuid) {
        this.playerMap.remove(uuid);
    }

    public AegisPlayer get(UUID uuid) {
        return this.playerMap.get(uuid);
    }

}
