package gg.aegis.util.collision;

import lombok.experimental.UtilityClass;
import gg.aegis.Anticheat;
import gg.aegis.util.entity.BoundingBox;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CollisionUtil {

    private static final BoundingBox FULL_BOX = new BoundingBox(0D, 0D, 0D, 1D, 1D, 1D);
    private static final BoundingBox CHEST_BOX = new BoundingBox(0.0625D, 0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
    private static final BoundingBox CARPET_BOX = new BoundingBox(0, 0, 0, 1D, 0.0625D, 1D);
    private static final BoundingBox SLAB_BOX = new BoundingBox(0, 0, 0, 1D, 0.5D, 1D);
    private static final BoundingBox BED_BOX = new BoundingBox(0, 0, 0, 1D, 0.5625D, 1D);
    private static final BoundingBox CACTUS_BOX = new BoundingBox(0.0625D, 0D, 0.0625D, 0.9375D, 1D, 0.9375D);
    private static final BoundingBox CAKE_BOX = new BoundingBox(0.0625D, 0D, 0.0625D, 0.9375D, 0.5D, 0.93675D);
    private static final BoundingBox REDSTONE_DIODE = new BoundingBox(0D, 0D, 0D, 1D, 0.125D, 1D);

    public static double collideY(World world, BoundingBox playerBB, double dx, double dy, double dz) {
        BoundingBox sweep = playerBB.addCoord(dx, dy, dz).expand(0, 0, 0, 1, 1, 1);

        List<BoundingBox> boxes = new ArrayList<>();

        sweep.checkSurrounding(world, block -> {
            BoundingBox blockBB = getBoxForMaterial(block);

            if (blockBB != null) {
                blockBB = blockBB.offset(
                        block.getX(),
                        block.getY(),
                        block.getZ()
                );

                Anticheat.LOGGER.info("BLOCK " + blockBB.toString());
                Anticheat.LOGGER.info("PLAYER " + playerBB.toString());

                boxes.add(blockBB);
            }
        });

        for(BoundingBox box : boxes) {
            dy = box.calculateYOffset(playerBB, dy);
        }

        return dy;
    }

    private BoundingBox getBoxForMaterial(Block block) {
        if(block.getType() != Material.AIR) {
            return FULL_BOX;
        }

        return null;
        /*BoundingBox box;

        Material material = block.getType();
        String name = material.name().toUpperCase();

        switch(material) {
            case DIODE:
                box = REDSTONE_DIODE;
                break;

            case CHEST:
            case ENDER_CHEST:
                box = CHEST_BOX;
                break;

            case CARPET:
                box = CARPET_BOX;
                break;

            case BED_BLOCK:
                box = BED_BOX;
                break;

            case CACTUS:
                box = CACTUS_BOX;
                break;

            case CAKE_BLOCK:
                box = CAKE_BOX;
                break;

            default:
                if(name.contains("SLAB")) {
                    box = SLAB_BOX;

                } else {
                    if(material.isSolid()) {
                        box = FULL_BOX;

                    } else {
                        box = null;
                    }
                }
                break;
        }

        if(box == null) {
            return null;
        }

        return box.copy();*/
    }

}
