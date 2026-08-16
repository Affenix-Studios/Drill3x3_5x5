package io.sniperjohnny.github.drill_3x3_and_5x5.listener;


import io.sniperjohnny.github.drill_3x3_and_5x5.Drill_3x3_and_5x5;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

public class BlockBreakListener implements Listener {
    private final Set<Location> pluginBreaking = new HashSet<>();

    @EventHandler
    public void onHammerBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block block = event.getBlock();

        if (pluginBreaking.contains(block.getLocation())) return;
        if (item == null || !item.hasItemMeta()) return;

        // Keys definieren
        NamespacedKey key3x3 = new NamespacedKey(Drill_3x3_and_5x5.getInstance(), "is_hammer_3x3");
        NamespacedKey key5x5 = new NamespacedKey(Drill_3x3_and_5x5.getInstance(), "is_hammer_5x5");

        int radius = 0;

        // Welcher Hammer ist es?
        if (item.getItemMeta().getPersistentDataContainer().has(key5x5, PersistentDataType.BYTE)) {
            radius = 2; // 5x5
        } else if (item.getItemMeta().getPersistentDataContainer().has(key3x3, PersistentDataType.BYTE)) {
            radius = 1; // 3x3
        }

        // Wenn es keiner der Hammer ist, abbrechen
        if (radius == 0) return;

        BlockFace face = player.getTargetBlockFace(5);
        if (face == null) return;

        // Die Schleife nutzt nun den dynamischen Radius
        for (int a = -radius; a <= radius; a++) {
            for (int b = -radius; b <= radius; b++) {
                if (a == 0 && b == 0) continue;

                Block relative;
                switch (face) {
                    case UP:
                    case DOWN:
                        relative = block.getRelative(a, 0, b);
                        break;
                    case EAST:
                    case WEST:
                        relative = block.getRelative(0, b, a);
                        break;
                    case NORTH:
                    case SOUTH:
                        relative = block.getRelative(a, b, 0);
                        break;
                    default:
                        return;
                }

                if (!relative.getType().isAir() && relative.getType() != Material.BEDROCK) {
                    // Hier kannst du deine Checks (Regionen, etc.) einbauen

                    pluginBreaking.add(relative.getLocation());
                    relative.breakNaturally(item);
                    pluginBreaking.remove(relative.getLocation());
                }
            }
        }
    }
}