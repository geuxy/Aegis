package gg.aegis.check.checks.ground;

import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;

import gg.aegis.check.BaseCheck;
import gg.aegis.check.CheckData;
import gg.aegis.check.CheckType;
import gg.aegis.player.AegisPlayer;
import gg.aegis.player.data.impl.GhostBlockData;
import gg.aegis.player.data.impl.PositionData;

@CheckData(
        name = "Ground",
        unique = "Positive",
        type = CheckType.NETWORK
)
public class CheckGroundPositive extends BaseCheck {

    @Override
    public void handle(AegisPlayer player, ProtocolPacketEvent event) {
        if(WrapperPlayClientPlayerFlying.isFlying(event.getPacketType()) && !player.isTeleporting()) {
            PositionData positionData = player.data(PositionData.class);

            boolean clientGround = positionData.isClientGround();
            boolean mathGround = positionData.isMathGround();
            boolean serverGround = positionData.isServerGround();
            boolean predictGround = positionData.isPredictGround();

            boolean collision = player.data(GhostBlockData.class).getFakeCollisionTicks() > 2 && mathGround;

            if((collision || !mathGround) && clientGround && !predictGround && !serverGround) {
                this.fail(player);
            }
        }
    }

}

