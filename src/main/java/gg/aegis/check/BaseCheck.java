package gg.aegis.check;

import com.github.retrooper.packetevents.event.ProtocolPacketEvent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lombok.experimental.Accessors;
import gg.aegis.Anticheat;
import gg.aegis.player.AegisPlayer;
import gg.aegis.util.text.TextBuilder;
import gg.aegis.util.other.Score;

@Getter @Accessors(fluent = true) @RequiredArgsConstructor
public abstract class BaseCheck {

    private final CheckMetadata metadata = CheckMetadata.of(this.getClass());

    protected final Score buffer = new Score(0, 10);
    protected final Score violations = new Score(0, 10);

    public abstract void handle(AegisPlayer player, ProtocolPacketEvent event);

    protected void fail(AegisPlayer player) {
        Anticheat.INSTANCE.getViolationManager().addViolation(player, this, null);
    }

    protected void fail(AegisPlayer player, TextBuilder message) {
        Anticheat.INSTANCE.getViolationManager().addViolation(player, this, message);
    }

    /*
     * Returns true if the player has reached the maximum
     * amount of violations, usually resulting in punishment.
     */
    public boolean addViolation() {
        this.violations.rise(1);

        if(this.violations.isMaximum()) {
            this.violations.reset();
            return true;
        }
        return false;
    }

}