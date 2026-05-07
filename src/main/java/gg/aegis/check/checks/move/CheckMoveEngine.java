package gg.aegis.check.checks.move;

import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import gg.aegis.Anticheat;
import gg.aegis.check.BaseCheck;
import gg.aegis.check.CheckData;
import gg.aegis.check.CheckType;
import gg.aegis.movement.data.MovementResult;
import gg.aegis.player.AegisPlayer;
import gg.aegis.player.data.impl.PositionData;

@CheckData(
        name = "Move",
        unique = "Engine",
        type = CheckType.MOVEMENT
)
public class CheckMoveEngine extends BaseCheck {

    @Override
    public void handle(AegisPlayer player, ProtocolPacketEvent event) {
        if(!WrapperPlayClientPlayerFlying.isFlying(event.getPacketType()) || player.isTeleporting()) {
            return;
        }

        MovementResult engineResult = Anticheat.INSTANCE.getMovementEngine().run(player);
        PositionData posData = player.data(PositionData.class);

        if(!engineResult.isWithinBounds(
                posData.getDeltaX(),
                posData.getDeltaY(),
                posData.getDeltaZ()
        )) {
            this.fail(player);
        }
    }

}

