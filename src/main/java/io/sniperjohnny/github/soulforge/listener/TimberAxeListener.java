package io.sniperjohnny.github.soulforge.listener;

import io.sniperjohnny.github.soulforge.SoulForge;
import io.sniperjohnny.github.soulforge.customItem.ItemStyle;
import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class TimberAxeListener implements Listener {

    public TimberAxeListener() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateAxeLore(player);
                }
            }
        }.runTaskTimer(SoulForge.getInstance(), 20, 10);
    }

    private static final BlockFace[] FACES = {
            BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST
    };

    private final Set<Location> pluginBreaking = new HashSet<>();
    private final Map<UUID, Long> lastFell = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()
                || !item.getItemMeta().getPersistentDataContainer().has(ToolKeys.TIMBER_AXE, PersistentDataType.BYTE)) {
            return;
        }
        if (pluginBreaking.contains(event.getBlock().getLocation())) {
            return;
        }
        if (!Tag.LOGS.isTagged(event.getBlock().getType())) {
            return;
        }

        boolean noCooldown = item.getItemMeta().getPersistentDataContainer()
                .has(ToolKeys.TIMBER_NO_COOLDOWN, PersistentDataType.BYTE);

        long now = System.currentTimeMillis();
        long cooldownMillis = (long) (SoulForge.getTimberCooldownSeconds() * 1000);
        Long last = lastFell.get(player.getUniqueId());
        boolean onCooldown = !noCooldown && last != null && now - last < cooldownMillis;

        if (onCooldown) {
            return;
        }

        int felled = fellTree(player, event.getBlock(), item);
        if (felled > 0 && !noCooldown) {
            startCooldown(player, now, cooldownMillis);
        }
    }

    private int fellTree(Player player, Block start, ItemStack item) {
        Material logType = start.getType();
        int maxLogs = (int) SoulForge.getTimberMaxLogs();
        ArrayDeque<Block> queue = new ArrayDeque<>();
        Set<Location> visited = new HashSet<>();
        queue.add(start);
        visited.add(start.getLocation());
        int broken = 0;
        while (!queue.isEmpty() && broken < maxLogs) {
            Block current = queue.poll();
            for (BlockFace face : FACES) {
                Block relative = current.getRelative(face);
                Location loc = relative.getLocation();
                if (visited.contains(loc)) {
                    continue;
                }
                visited.add(loc);
                if (relative.getType() == logType) {
                    pluginBreaking.add(loc);
                    relative.breakNaturally(item);
                    pluginBreaking.remove(loc);
                    broken++;
                    queue.add(relative);
                }
            }
        }
        return broken;
    }

    private void startCooldown(Player player, long start, long cooldownMillis) {
        lastFell.put(player.getUniqueId(), start);

        player.setCooldown(Material.NETHERITE_AXE, (int) Math.ceil(cooldownMillis / 50.0));

        BossBar bar = BossBar.bossBar(Component.text("Timber Axe Cooldown", NamedTextColor.GOLD),
                1.0f, BossBar.Color.RED, BossBar.Overlay.PROGRESS);
        player.showBossBar(bar);

        new BukkitRunnable() {
            @Override
            public void run() {
                long remaining = cooldownMillis - (System.currentTimeMillis() - start);
                if (remaining <= 0) {
                    cancel();
                    player.hideBossBar(bar);
                    return;
                }
                int seconds = (int) Math.ceil(remaining / 1000.0);
                bar.name(Component.text("Timber Axe Cooldown — " + seconds + "s", NamedTextColor.GOLD));
                bar.progress((float) Math.max(0.0, (double) remaining / cooldownMillis));
            }
        }.runTaskTimer(SoulForge.getInstance(), 0, 2);
    }

    private void updateAxeLore(Player player) {
        ItemStack[] contents = player.getInventory().getContents();
        boolean changed = false;
        for (ItemStack item : contents) {
            changed |= updateSingleAxeLore(player, item);
        }
        if (changed) {
            player.getInventory().setContents(contents);
        }
        ItemStack offHand = player.getInventory().getItemInOffHand();
        if (updateSingleAxeLore(player, offHand)) {
            player.getInventory().setItemInOffHand(offHand);
        }
    }

    private boolean updateSingleAxeLore(Player player, ItemStack item) {
        if (item == null || !item.hasItemMeta()
                || !item.getItemMeta().getPersistentDataContainer().has(ToolKeys.TIMBER_AXE, PersistentDataType.BYTE)) {
            return false;
        }
        List<Component> target = buildLore(player, item);
        if (target.equals(item.getItemMeta().lore())) {
            return false;
        }
        item.editMeta(meta -> meta.lore(target));
        return true;
    }

    private List<Component> buildLore(Player player, ItemStack item) {
        List<Component> lore = new ArrayList<>();
        if (item.getItemMeta().getPersistentDataContainer()
                .has(ToolKeys.TIMBER_NO_COOLDOWN, PersistentDataType.BYTE)) {
            lore.add(Component.text("Fells whole trees — no cooldown", NamedTextColor.GREEN));
        } else {
            long remaining = remainingCooldown(player.getUniqueId());
            if (remaining > 0) {
                lore.add(Component.text("On cooldown — " + (remaining + 999) / 1000 + "s left", NamedTextColor.RED));
            } else {
                lore.add(Component.text("Fells whole trees — ready", NamedTextColor.GREEN));
            }
        }
        lore.addAll(upgradeLines(item));
        lore.add(Component.text("Upgrade me in /soulsshop", NamedTextColor.GRAY));
        return lore;
    }

    private List<Component> upgradeLines(ItemStack item) {
        List<Component> lines = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        int efficiency = meta.getEnchantLevel(org.bukkit.enchantments.Enchantment.EFFICIENCY);
        if (efficiency > 0) {
            lines.add(Component.text("\u2726 Efficiency " + ItemStyle.roman(efficiency), NamedTextColor.GOLD));
        }
        int fortune = meta.getEnchantLevel(org.bukkit.enchantments.Enchantment.FORTUNE);
        if (fortune > 0) {
            lines.add(Component.text("\u2726 Fortune " + ItemStyle.roman(fortune), NamedTextColor.GOLD));
        }
        if (meta.getEnchantLevel(org.bukkit.enchantments.Enchantment.SILK_TOUCH) > 0) {
            lines.add(Component.text("\u2726 Silk Touch", NamedTextColor.GOLD));
        }
        return lines;
    }

    private long remainingCooldown(UUID uuid) {
        Long last = lastFell.get(uuid);
        if (last == null) {
            return 0;
        }
        long cooldownMillis = (long) (SoulForge.getTimberCooldownSeconds() * 1000);
        return Math.max(0, last + cooldownMillis - System.currentTimeMillis());
    }
}
