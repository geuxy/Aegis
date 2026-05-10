package gg.aegis.player.exempt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import gg.aegis.player.AegisPlayer;
import gg.aegis.player.data.PositionData;

import java.util.function.Function;

@Getter @RequiredArgsConstructor
public enum Exempt {

    // TODO: Restrict a bit more
    EXISTED(p -> p.getLivingTicks() <= 100),
    IRREGULAR_BLOCK(AegisPlayer::isOnIrregularBlock),
    LIQUID(p -> p.isInLiquid() || p.isLastInLiquid()),
    CLIMBABLE(p -> p.isClimbing() || p.isLastClimbing()),
    SLIME(p -> p.isOnSlime() || p.isLastOnSlime()),
    SOUL_SAND(p -> p.isOnSoulSand() || p.isLastOnSoulSand()),
    ICE(p -> p.isOnIce() || p.isLastOnIce()),
    WEB(p -> p.isInWeb() || p.isLastInWeb()),
    PISTON(p -> p.isOnPiston() || p.isLastOnPiston()),
    BLOCK_ABOVE(p -> p.isBlockAbove() || p.isLastBlockAbove()),
    TELEPORT(p -> p.isTeleporting() || p.isLastTeleporting()),
    VELOCITY(p -> p.isTakingVelocity() || p.isLastTakingVelocity()),
    STEP(p -> {
        PositionData movement = p.data(PositionData.class);
        double deltaY = movement.getDeltaY();

        return movement.isClientGround() && deltaY > 0 && deltaY <= 0.6F && !p.isTeleporting() && (p.isOnIrregularBlock() || p.isLastOnIrregularBlock());
    });

    private final Function<AegisPlayer, Boolean> function;

}
