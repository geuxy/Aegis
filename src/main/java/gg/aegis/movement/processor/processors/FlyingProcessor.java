package gg.aegis.movement.processor.processors;

import com.github.retrooper.packetevents.protocol.potion.PotionTypes;
import gg.aegis.movement.data.MovementRegistry;
import gg.aegis.movement.processor.BaseProcessor;
import gg.aegis.player.AegisPlayer;
import gg.aegis.player.data.impl.AbilityData;
import gg.aegis.player.data.impl.PositionData;
import gg.aegis.util.collision.CollisionUtil;
import gg.aegis.util.entity.BoundingBox;
import gg.aegis.util.other.TriStateBoolean;

public class FlyingProcessor implements BaseProcessor {

    private static final int EFFECT_AMPLIFIER_UNTIL_NEGATIVE = -5;

    @Override
    public MovementRegistry process(AegisPlayer player, MovementRegistry registry) {
        PositionData positionData = player.data(PositionData.class);

        double predictedDeltaY = positionData.getLastDeltaY();

        if(!positionData.isOnLoadedBlock()) {
            predictedDeltaY = positionData.getLastY() > 0D ? -0.1D : 0D;

        } else {
            predictedDeltaY -= 0.08D;
        }

        predictedDeltaY *= 0.98F;

        if(predictedDeltaY > -0.005D && predictedDeltaY < 0.005D) {
            predictedDeltaY = 0;
        }

        int jumpAmp = player.data(AbilityData.class).getEffectAmplifier(PotionTypes.JUMP_BOOST);

        TriStateBoolean jumping = isPossiblyJumping(player, positionData, jumpAmp);

        if(jumping.isPossible()) {
            if(positionData.isLastClientGround()) {
                predictedDeltaY = 0.42F + (0.1F * jumpAmp);
            }
        }

        double x = positionData.getLastX();
        double y = positionData.getLastY();
        double z = positionData.getLastZ();
        double dx = positionData.getDeltaX();
        double dz = positionData.getDeltaZ();

        BoundingBox playerBox = new BoundingBox(x - 0.3D, y, z - 0.3D, x + 0.3D, y + 1.8D, z + 0.3D);

        predictedDeltaY = CollisionUtil.collideY(player.getRaw().getWorld(), playerBox, dx, predictedDeltaY, dz);

        registry.getBounds().forceSetY(predictedDeltaY);

        return registry;
    }

    private TriStateBoolean isPossiblyJumping(AegisPlayer player, PositionData positionData, int jumpEffectAmplifier) {
        boolean groundCheck = positionData.isLastClientGround() && !positionData.isClientGround();
        boolean negativeJump = jumpEffectAmplifier <= EFFECT_AMPLIFIER_UNTIL_NEGATIVE && positionData.getDeltaY() < 0;
        boolean positiveJump = jumpEffectAmplifier > EFFECT_AMPLIFIER_UNTIL_NEGATIVE && positionData.getDeltaY() >= 0;

        boolean uncertainGround = player.isTakingVelocity() || player.isClimbing() || player.isInLiquid();
        boolean uncertainAir = player.isInLiquid();

        if(negativeJump || positiveJump) {
            if(groundCheck) {
                return uncertainGround ? TriStateBoolean.UNCERTAIN : TriStateBoolean.TRUE;
            }

            return uncertainAir ? TriStateBoolean.UNCERTAIN : TriStateBoolean.FALSE;
        }

        return TriStateBoolean.FALSE;
    }

}
