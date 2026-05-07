package gg.aegis.util.world;

import lombok.experimental.UtilityClass;

import gg.aegis.player.AegisPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

@UtilityClass
public class BlockUtil {

    /**
     * Checks if the given material `name` is any type of irregular block (A block that has an unusual size.)
     *
     * @author Tecnio (<a href="https://github.com/Tecnio/antihaxerman/blob/master/src/main/java/me/tecnio/ahm/data/tracker/impl/PositionTracker.java">...</a>)
     * @param name the material name to check.
     * @return true if material `name` is any type of irregular block.
     */
    public boolean isIrregular(String name) {
        return name.contains("STAIR") || name.contains("PATH") || name.contains("SNOW")
            || name.contains("SLAB") || name.contains("STEP") || name.contains("CARPET")
            || name.contains("CHEST") || name.contains("DOOR") || name.contains("ENDER")
            || name.contains("SLIME") || name.contains("LILY") || name.contains("COMPARATOR")
            || name.contains("REPEATER") || name.contains("FENCE") || name.contains("BREWING")
            || name.contains("SKULL") || name.contains("HEAD");
    }

    /**
     * Gets the slipperiness value of the material / block specified
     *
     * @author Tecnio (<a href="https://github.com/Tecnio/antihaxerman/blob/master/src/main/java/me/tecnio/ahm/data/tracker/impl/PositionTracker.java">...</a>)
     * @param material the material / block to get its slipperiness value from
     * @return slipperiness value of the block
     */
    public float getSlipperiness(Material material) {
        switch (material) {
            case SLIME_BLOCK: return 0.8F;
            case ICE:
            case PACKED_ICE: return 0.98F;
            default: return 0.6F;
        }
    }

    /**
     * Gets the slipperiness value of the material / block that the player is on
     *
     * @author Tecnio (modified) (<a href="https://github.com/Tecnio/antihaxerman/blob/master/src/main/java/me/tecnio/ahm/data/tracker/impl/PositionTracker.java">...</a>)
     * @param player the player that is standing on the block / material to get slipperiness from
     * @return slipperiness value of the block below the players feet
     */
    public float getSlipperiness(AegisPlayer player) {
        if(player.isOnSlime()) return 0.8F;
        else if(player.isOnIce()) return 0.98F;
        else return 0.6F;
    }

    /**
     * Checks if the given `material` is any type of piston block
     *
     * @param material the material to check.
     * @return true if `material` is any type of piston block.
     */
    public boolean isPiston(Material material) {
        return isMaterial(material,
            Material.PISTON_BASE,
            Material.PISTON_EXTENSION,
            Material.PISTON_STICKY_BASE,
            Material.PISTON_MOVING_PIECE
        );
    }

    /**
     * Checks if the given `material` is any type of climbable
     *
     * @param material the material to check.
     * @return true if `material` is any type of climbable.
     */
    public boolean isClimbable(Material material) {
        return isMaterial(material,
            Material.LADDER,
            Material.VINE
        );
    }

    /**
     * Checks if the given `material` is any type of liquid
     *
     * @param material the material to check.
     * @return true if `material` is any type of liquid.
     */
    public boolean isLiquid(Material material) {
        return isMaterial(material,
            Material.WATER,
            Material.STATIONARY_WATER,
            Material.LAVA,
            Material.STATIONARY_LAVA
        );
    }

    /**
     * Checks if the given `material` is any type of ice.
     *
     * @param material the material to check.
     * @return true if `material` is any type of ice.
     */
    public boolean isIce(Material material) {
        return isMaterial(material,
            Material.ICE,
            Material.PACKED_ICE
        );
    }

    /**
     * Gets the block in the world based on the given `location`.
     *
     * @param location The blocks' location.
     * @return the block that is in the given `location`.
     */
    public Block getBlock(Location location) {
        if (location.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
            return location.getWorld().getBlockAt(location);

        } else {
            return null;
        }
    }

    public boolean isLoaded(Location location) {
        return location.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4) && location.getChunk().isLoaded();
    }

    public boolean isSolid(Material material) {
        return material.isSolid() || material == Material.WATER_LILY || material == Material.LADDER;
    }

    /**
     * Checks if `material` is equal to any of the provided `materials`
     *
     * @param material The material to check.
     * @param materials The list of materials that `material` should be.
     * @return true if `materials` contains `material`
     */
    public boolean isMaterial(Material material, Material... materials) {
        if(material == null || materials == null) {
            return false;
        }

        for(Material m : materials) {
            if(m == material) {
                return true;
            }
        }

        return false;
    }

}
