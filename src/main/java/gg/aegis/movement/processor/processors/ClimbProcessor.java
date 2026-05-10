package gg.aegis.movement.processor.processors;

import gg.aegis.movement.data.MovementRegistry;
import gg.aegis.movement.processor.BaseProcessor;
import gg.aegis.player.AegisPlayer;
import gg.aegis.player.data.PositionData;

public class ClimbProcessor implements BaseProcessor {

    public static final double MAX_HORIZONTAL_SPEED = 0.15F;
    public static final double MAX_VERTICAL_SPEED = 0.15D;

    @Override
    public MovementRegistry process(AegisPlayer player, MovementRegistry registry) {
        if(!player.isClimbing() && !player.isLastClimbing()) {
            return registry;
        }

        boolean certain = player.data(PositionData.class).isPositionChanged();

        if(certain) {
            registry.getBounds().forceSetMinX(-MAX_HORIZONTAL_SPEED);
            registry.getBounds().forceSetMaxX(MAX_HORIZONTAL_SPEED);
            registry.getBounds().forceSetMinZ(-MAX_HORIZONTAL_SPEED);
            registry.getBounds().forceSetMaxZ(MAX_HORIZONTAL_SPEED);
            registry.addTag("definite-climb");
        }

        if(!certain || player.isLastClimbing()) {
            registry.getBounds().setMinX(-MAX_HORIZONTAL_SPEED);
            registry.getBounds().setMaxX(MAX_HORIZONTAL_SPEED);
            registry.getBounds().setMinZ(-MAX_HORIZONTAL_SPEED);
            registry.getBounds().setMaxZ(MAX_HORIZONTAL_SPEED);
            registry.addTag("uncertain-climb");
        }

        /* TODO: If delta Y is higher than 0.15D, lower until it reaches 0.15D */
        registry.getBounds().setMinY(-MAX_VERTICAL_SPEED);
        registry.getBounds().setMaxY(MAX_VERTICAL_SPEED);

        /* TODO: Set min motionY to 0 when they are sneaking */

        return registry;
    }

}