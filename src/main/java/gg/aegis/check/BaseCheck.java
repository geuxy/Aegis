package gg.aegis.check;

import gg.aegis.service.alert.AlertService;
import gg.aegis.service.punish.PunishService;
import gg.aegis.util.PacketProcessor;

import gg.aegis.player.AegisPlayer;
import gg.aegis.util.other.StringUtil;
import gg.aegis.util.text.TextBuilder;
import gg.aegis.util.other.Score;

public abstract class BaseCheck implements PacketProcessor {

    public final CheckMetadata metadata = CheckMetadata.of(this.getClass());

    public final Score buffer = new Score(0, 10);
    public final Score violations = new Score(0, 10);

    protected void fail(AegisPlayer player) {
        this.fail(player, null);
    }

    protected void fail(AegisPlayer player, TextBuilder message) {
        this.violations.rise(1);

        if(this.violations.isMaximum()) {
            this.violations.reset();
            PunishService.run(player, TextBuilder.of("You were detected cheating"));
        }

        String baseMessage = StringUtil.format(
                "{} failed {} ({})",
                player.getRaw().getName(),
                this.metadata.name(),
                this.metadata.unique()
        );

        AlertService.alert(TextBuilder.of(baseMessage).add(message));
    }

}