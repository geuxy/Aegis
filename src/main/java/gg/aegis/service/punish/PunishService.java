package gg.aegis.service.punish;

import gg.aegis.player.AegisPlayer;
import gg.aegis.util.text.TextBuilder;

public class PunishService {

    private static final PunishmentType DEFAULT_PUNISHMENT = PunishmentType.NONE;

    public static void run(AegisPlayer player, TextBuilder message) {
        DEFAULT_PUNISHMENT.punish(player, message);
    }

}
