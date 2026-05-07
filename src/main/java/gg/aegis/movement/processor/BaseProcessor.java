package gg.aegis.movement.processor;

import gg.aegis.movement.data.MovementRegistry;
import gg.aegis.player.AegisPlayer;

@FunctionalInterface
public interface BaseProcessor {

    MovementRegistry process(AegisPlayer player, MovementRegistry registry);

}
