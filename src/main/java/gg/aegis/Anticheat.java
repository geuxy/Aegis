package gg.aegis;

import com.github.retrooper.packetevents.PacketEvents;

import gg.aegis.service.alert.AlertService;
import lombok.Getter;

import gg.aegis.check.CheckManager;
import gg.aegis.listeners.BukkitListener;
import gg.aegis.listeners.PacketEventsListener;
import gg.aegis.movement.MovementEngine;
import gg.aegis.player.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public enum Anticheat {

    INSTANCE;

    private final PlayerManager playerManager = new PlayerManager();
    private final CheckManager checkManager = new CheckManager();

    private final MovementEngine movementEngine = new MovementEngine();

    public static final String NAME = "Aegis";
    public static final String VERSION = "1.0.0";
    public static final String CHAT_PREFIX = "§6" + NAME + "§7 »§r ";

    public static final Logger LOGGER = Logger.getLogger(NAME);

    private JavaPlugin plugin;

    public void init(JavaPlugin plugin) {
        if(this.plugin != null) {
            LOGGER.warning("Aegis is already initialised");
            return;
        }

        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(new BukkitListener(), this.plugin);
        PacketEvents.getAPI().getEventManager().registerListener(new PacketEventsListener());
    }

    public void stop() {
        PacketEvents.getAPI().getEventManager().unregisterAllListeners();
        HandlerList.unregisterAll(plugin);
    }

}
