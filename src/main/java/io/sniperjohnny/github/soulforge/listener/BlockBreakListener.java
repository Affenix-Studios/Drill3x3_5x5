package io.sniperjohnny.github.soulforge.listener;


import io.sniperjohnny.github.soulforge.SoulForge;
import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class BlockBreakListener implements Listener {
    private final Set<Location> pluginBreaking = new HashSet<>();

    @EventHandler
    public void onHammerBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block block = event.getBlock();

        if (pluginBreaking.contains(block.getLocation())) return;
        if (item == null || !item.hasItemMeta()) return;

        int radius = 0;
        boolean isTntPickaxe = false;

        if (item.getItemMeta().getPersistentDataContainer().has(ToolKeys.DRILL_5X5, PersistentDataType.BYTE)) {
            radius = 2;
        } else if (item.getItemMeta().getPersistentDataContainer().has(ToolKeys.DRILL_3X3, PersistentDataType.BYTE)) {
            radius = 1;
        } else if (item.getItemMeta().getPersistentDataContainer().has(ToolKeys.TNT_PICKAXE, PersistentDataType.BYTE)) {
            isTntPickaxe = true;
        }

        if (radius > 0) {
            BlockFace face = player.getTargetBlockFace(5);
            if (face == null) return;

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
                        pluginBreaking.add(relative.getLocation());
                        relative.breakNaturally(item);
                        pluginBreaking.remove(relative.getLocation());
                    }
                }
            }
        }

        if (isTntPickaxe && ThreadLocalRandom.current().nextDouble() * 100 < SoulForge.getTntChance()) {
            World world = block.getWorld();
            TNTPrimed tnt = world.spawn(block.getLocation().add(0.5, 0.5, 0.5), TNTPrimed.class);
            tnt.setFuseTicks(40);
        }
    }
}