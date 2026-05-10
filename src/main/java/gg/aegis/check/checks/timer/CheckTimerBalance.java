package gg.aegis.check.checks.timer;

import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;

import gg.aegis.check.BaseCheck;
import gg.aegis.check.CheckData;
import gg.aegis.check.CheckType;
import gg.aegis.player.AegisPlayer;
import gg.aegis.util.other.Score;
import gg.aegis.util.text.TextBuilder;

@CheckData(
        name = "Timer",
        unique = "Balance",
        type = CheckType.NETWORK
)
public class CheckTimerBalance extends BaseCheck {

    public static final long MILLIS_PER_TICK = 50;
    public static final double BUFFER_DECAY = 0.002D;

    private final Score balance = new Score();
    private long lastFlying;

    @Override
    public void process(AegisPlayer player, ProtocolPacketEvent event) {
        if(!WrapperPlayClientPlayerFlying.isFlying(event.getPacketType())) {
            return;
        }

        long current = event.getTimestamp();

        if (player.isTeleporting()) {
            this.balance.decline(MILLIS_PER_TICK);
        }

        if(lastFlying != 0) {
            this.balance.rise(MILLIS_PER_TICK);
            this.balance.decline(current - lastFlying);

            if(balance.isAbove(MILLIS_PER_TICK) && buffer.rise(1).isAbove(1)) {
                this.fail(player, TextBuilder.of("balance", this.balance.getValue()));
                this.balance.decline(MILLIS_PER_TICK);

            } else {
                this.buffer.decline(BUFFER_DECAY);
            }
        }
        this.lastFlying = current;
    }

}
