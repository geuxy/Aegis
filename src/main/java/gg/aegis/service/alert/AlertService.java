package gg.aegis.service.alert;

import gg.aegis.util.text.TextBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class AlertService {

    public static void alert(TextBuilder text) {
        Bukkit.broadcastMessage(text.flatten());
    }

}
