package gg.aegis.violation;

import gg.aegis.Anticheat;
import gg.aegis.check.BaseCheck;
import gg.aegis.player.AegisPlayer;
import gg.aegis.util.other.StringUtil;
import gg.aegis.util.text.TextBuilder;
import org.bukkit.Bukkit;

import java.util.*;

public class ViolationManager {

    private final Map<UUID, List<Violation>> violationsMap = new HashMap<>();

    public void addViolation(AegisPlayer player, BaseCheck check, TextBuilder message) {
        Violation violation = new Violation(check, message, System.currentTimeMillis());

        this.violationsMap.computeIfAbsent(
                player.getRaw().getUniqueId(),
                p -> new ArrayList<>()
        ).add(violation);

        sendAlert(getFailMessage(player, check));

        if(check.addViolation()) {
            // TODO: Add punishments through commands that can be set in the config
        }
    }

    public static String getFailMessage(AegisPlayer player, BaseCheck check) {
        return StringUtil.format("{}§6{} §7- §6{} ({}) §7- VL: §6{} §7",
                Anticheat.CHAT_PREFIX,
                player.getRaw().getName(),
                check.metadata().name(),
                check.metadata().type(),
                check.violations().getValue()
        );
    }

    public static void sendAlert(String message) {
        Bukkit.broadcastMessage(message);
    }

}
