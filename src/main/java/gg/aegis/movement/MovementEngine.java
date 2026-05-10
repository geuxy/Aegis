package gg.aegis.movement;

import gg.aegis.movement.data.MovementRegistry;
import gg.aegis.movement.data.MovementResult;
import gg.aegis.movement.processor.processors.ClimbProcessor;
import gg.aegis.movement.processor.processors.FlyingProcessor;
import gg.aegis.player.AegisPlayer;

public class MovementEngine {

    private final FlyingProcessor flyingProcessor = new FlyingProcessor();
    private final ClimbProcessor climbProcessor = new ClimbProcessor();

    public void prepare() {

    }

    public MovementResult run(AegisPlayer player) {
        // Pre
        this.prepare();

        MovementRegistry registry = new MovementRegistry();

        // Processors
        registry = this.flyingProcessor.process(player, registry);
        registry = this.climbProcessor.process(player, registry);

        // Post
        this.cleanup();

        return new MovementResult(
                registry.getBounds(),
                registry.getTags()
        );
    }

    public void cleanup() {

    }

}
