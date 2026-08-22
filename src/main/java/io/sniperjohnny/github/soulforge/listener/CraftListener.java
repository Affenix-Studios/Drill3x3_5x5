package io.sniperjohnny.github.soulforge.listener;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.sniperjohnny.github.soulforge.SoulForge;
import io.sniperjohnny.github.soulforge.customItem.ItemStyle;
import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import io.sniperjohnny.github.soulforge.souls.SoulManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class CraftListener implements Listener {

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        if (!(event.getView().getPlayer() instanceof Player player)) {
            return;
        }
        if (event.getRecipe() == null) {
            return;
        }
        event.getInventory().setResult(applyUnlocks(player, event.getRecipe().getResult()));
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        ItemStack result = event.getCurrentItem();
        if (result == null || !result.hasItemMeta()
                || !result.getItemMeta().getPersistentDataContainer().has(ToolKeys.MURASAME, PersistentDataType.BYTE)) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (!SoulManager.hasUnlock(player, SoulManager.MURASAME_CRAFT)) {
            event.setCancelled(true);
            player.sendMessage(Component.text(
                    "You haven't unlocked the ability to craft Murasame. Unlock it in /soulsshop!",
                    NamedTextColor.RED));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        refreshInventory(event.getPlayer());
    }

    public static ItemStack applyUnlocks(Player player, ItemStack base) {
        if (base == null || !base.hasItemMeta()) {
            return base;
        }
        ItemStack result = base.clone();
        ItemMeta meta = result.getItemMeta();

        if (meta.getPersistentDataContainer().has(ToolKeys.TNT_PICKAXE, PersistentDataType.BYTE)) {
            List<Component> lines = new ArrayList<>();
            int fortune = SoulManager.getUnlockLevel(player, SoulManager.TNT_FORTUNE);
            int silkTouch = SoulManager.getUnlockLevel(player, SoulManager.TNT_SILKTOUCH);
            if (silkTouch > 0) {
                meta.removeEnchant(Enchantment.FORTUNE);
                meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
                lines.add(upgradeLine("Silk Touch"));
            } else if (fortune > 0) {
                meta.addEnchant(Enchantment.FORTUNE, fortune, true);
                lines.add(upgradeLine("Fortune " + ItemStyle.roman(fortune)));
            }
            if (SoulManager.getUnlockLevel(player, SoulManager.TNT_UNBREAKABLE) > 0) {
                result.setData(DataComponentTypes.UNBREAKABLE);
                lines.add(upgradeLine("Unbreakable"));
            }
            appendLore(meta, lines);
            result.setItemMeta(meta);
            return result;
        }

        if (meta.getPersistentDataContainer().has(ToolKeys.TIMBER_AXE, PersistentDataType.BYTE)) {
            List<Component> lines = new ArrayList<>();
            if (SoulManager.getUnlockLevel(player, SoulManager.TIMBER_COOLDOWN) > 0) {
                meta.getPersistentDataContainer().set(ToolKeys.TIMBER_NO_COOLDOWN, PersistentDataType.BYTE, (byte) 1);
                lines.add(upgradeLine("No Cooldown"));
            }
            int efficiency = SoulManager.getUnlockLevel(player, SoulManager.TIMBER_EFFICIENCY);
            if (efficiency > 0) {
                meta.addEnchant(Enchantment.EFFICIENCY, efficiency, true);
                lines.add(upgradeLine("Efficiency " + ItemStyle.roman(efficiency)));
            }
            int fortune = SoulManager.getUnlockLevel(player, SoulManager.TIMBER_FORTUNE);
            int silkTouch = SoulManager.getUnlockLevel(player, SoulManager.TIMBER_SILKTOUCH);
            if (silkTouch > 0) {
                meta.removeEnchant(Enchantment.FORTUNE);
                meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
                lines.add(upgradeLine("Silk Touch"));
            } else if (fortune > 0) {
                meta.addEnchant(Enchantment.FORTUNE, fortune, true);
                lines.add(upgradeLine("Fortune " + ItemStyle.roman(fortune)));
            }
            appendLore(meta, lines);
            result.setItemMeta(meta);
            return result;
        }

        if (meta.getPersistentDataContainer().has(ToolKeys.SCYTHE, PersistentDataType.BYTE)) {
            List<Component> lines = new ArrayList<>();
            int efficiency = SoulManager.getUnlockLevel(player, SoulManager.SCYTHE_EFFICIENCY);
            if (efficiency > 0) {
                meta.addEnchant(Enchantment.EFFICIENCY, efficiency, true);
                lines.add(upgradeLine("Efficiency " + ItemStyle.roman(efficiency)));
            }
            int looting = SoulManager.getUnlockLevel(player, SoulManager.SCYTHE_LOOTING);
            if (looting > 0) {
                meta.addEnchant(Enchantment.LOOTING, looting, true);
                lines.add(upgradeLine("Looting " + ItemStyle.roman(looting)));
            }
            int sharpness = SoulManager.getUnlockLevel(player, SoulManager.SCYTHE_SHARPNESS);
            if (sharpness > 0) {
                meta.addEnchant(Enchantment.SHARPNESS, sharpness, true);
                lines.add(upgradeLine("Sharpness " + ItemStyle.roman(sharpness)));
            }
            int harvest = SoulManager.getUnlockLevel(player, SoulManager.SCYTHE_SOUL_HARVEST);
            if (harvest > 0) {
                int boost = harvest * (int) SoulForge.getScytheSoulBoost();
                meta.getPersistentDataContainer().set(ToolKeys.SCYTHE_SOUL_BOOST, PersistentDataType.INTEGER, boost);
                lines.add(upgradeLine("Soul Harvest +" + boost + "%"));
            }
            appendLore(meta, lines);
            result.setItemMeta(meta);
            return result;
        }

        if (meta.getPersistentDataContainer().has(ToolKeys.MURASAME, PersistentDataType.BYTE)) {
            if (!SoulManager.hasUnlock(player, SoulManager.MURASAME_CRAFT)) {
                return null;
            }
            return result;
        }

        return base;
    }

    public static void refreshInventory(Player player) {
        ItemStack[] contents = player.getInventory().getContents();
        boolean changed = false;
        for (int i = 0; i < contents.length; i++) {
            ItemStack refreshed = refreshTool(player, contents[i]);
            if (refreshed != contents[i]) {
                contents[i] = refreshed;
                changed = true;
            }
        }
        if (changed) {
            player.getInventory().setContents(contents);
        }
    }

    private static ItemStack refreshTool(Player player, ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta.getPersistentDataContainer().has(ToolKeys.SCYTHE, PersistentDataType.BYTE)) {
            return refreshScythe(player, item);
        }
        if (meta.getPersistentDataContainer().has(ToolKeys.TIMBER_AXE, PersistentDataType.BYTE)) {
            return refreshTimberAxe(player, item);
        }
        if (meta.getPersistentDataContainer().has(ToolKeys.TNT_PICKAXE, PersistentDataType.BYTE)) {
            return refreshTntPickaxe(player, item);
        }
        return item;
    }

    private static ItemStack refreshScythe(Player player, ItemStack item) {
        ItemStack out = item.clone();
        ItemMeta meta = out.getItemMeta();
        meta.removeEnchant(Enchantment.SHARPNESS);
        meta.removeEnchant(Enchantment.EFFICIENCY);
        meta.removeEnchant(Enchantment.LOOTING);

        List<Component> lines = new ArrayList<>();
        int efficiency = SoulManager.getUnlockLevel(player, SoulManager.SCYTHE_EFFICIENCY);
        if (efficiency > 0) {
            meta.addEnchant(Enchantment.EFFICIENCY, efficiency, true);
            lines.add(upgradeLine("Efficiency " + ItemStyle.roman(efficiency)));
        }
        int looting = SoulManager.getUnlockLevel(player, SoulManager.SCYTHE_LOOTING);
        if (looting > 0) {
            meta.addEnchant(Enchantment.LOOTING, looting, true);
            lines.add(upgradeLine("Looting " + ItemStyle.roman(looting)));
        }
        int sharpness = SoulManager.getUnlockLevel(player, SoulManager.SCYTHE_SHARPNESS);
        if (sharpness > 0) {
            meta.addEnchant(Enchantment.SHARPNESS, sharpness, true);
            lines.add(upgradeLine("Sharpness " + ItemStyle.roman(sharpness)));
        }
        int harvest = SoulManager.getUnlockLevel(player, SoulManager.SCYTHE_SOUL_HARVEST);
        if (harvest > 0) {
            int boost = harvest * (int) SoulForge.getScytheSoulBoost();
            meta.getPersistentDataContainer().set(ToolKeys.SCYTHE_SOUL_BOOST, PersistentDataType.INTEGER, boost);
            lines.add(upgradeLine("Soul Harvest +" + boost + "%"));
        } else {
            meta.getPersistentDataContainer().remove(ToolKeys.SCYTHE_SOUL_BOOST);
        }

        out.setItemMeta(meta);
        replaceUpgradeLore(out, lines);
        return out;
    }

    private static ItemStack refreshTimberAxe(Player player, ItemStack item) {
        ItemStack out = item.clone();
        ItemMeta meta = out.getItemMeta();
        meta.removeEnchant(Enchantment.EFFICIENCY);
        meta.removeEnchant(Enchantment.FORTUNE);
        meta.removeEnchant(Enchantment.SILK_TOUCH);

        if (SoulManager.getUnlockLevel(player, SoulManager.TIMBER_COOLDOWN) > 0) {
            meta.getPersistentDataContainer().set(ToolKeys.TIMBER_NO_COOLDOWN, PersistentDataType.BYTE, (byte) 1);
        } else {
            meta.getPersistentDataContainer().remove(ToolKeys.TIMBER_NO_COOLDOWN);
        }
        int efficiency = SoulManager.getUnlockLevel(player, SoulManager.TIMBER_EFFICIENCY);
        if (efficiency > 0) {
            meta.addEnchant(Enchantment.EFFICIENCY, efficiency, true);
        }
        int fortune = SoulManager.getUnlockLevel(player, SoulManager.TIMBER_FORTUNE);
        int silkTouch = SoulManager.getUnlockLevel(player, SoulManager.TIMBER_SILKTOUCH);
        if (silkTouch > 0) {
            meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        } else if (fortune > 0) {
            meta.addEnchant(Enchantment.FORTUNE, fortune, true);
        }

        out.setItemMeta(meta);
        return out;
    }

    private static ItemStack refreshTntPickaxe(Player player, ItemStack item) {
        ItemStack out = item.clone();
        ItemMeta meta = out.getItemMeta();
        meta.removeEnchant(Enchantment.FORTUNE);
        meta.removeEnchant(Enchantment.SILK_TOUCH);

        List<Component> lines = new ArrayList<>();
        int fortune = SoulManager.getUnlockLevel(player, SoulManager.TNT_FORTUNE);
        int silkTouch = SoulManager.getUnlockLevel(player, SoulManager.TNT_SILKTOUCH);
        if (silkTouch > 0) {
            meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
            lines.add(upgradeLine("Silk Touch"));
        } else if (fortune > 0) {
            meta.addEnchant(Enchantment.FORTUNE, fortune, true);
            lines.add(upgradeLine("Fortune " + ItemStyle.roman(fortune)));
        }
        if (SoulManager.getUnlockLevel(player, SoulManager.TNT_UNBREAKABLE) > 0) {
            out.setData(DataComponentTypes.UNBREAKABLE);
            lines.add(upgradeLine("Unbreakable"));
        }

        out.setItemMeta(meta);
        replaceUpgradeLore(out, lines);
        return out;
    }

    private static void replaceUpgradeLore(ItemStack item, List<Component> newUpgradeLines) {
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = meta.lore();
        List<Component> kept = new ArrayList<>();
        if (lore != null) {
            for (Component line : lore) {
                String text = PlainTextComponentSerializer.plainText().serialize(line);
                if (!text.startsWith("\u2726 ")) {
                    kept.add(line);
                }
            }
        }
        kept.addAll(newUpgradeLines);
        meta.lore(kept);
        item.setItemMeta(meta);
    }

    private static Component upgradeLine(String text) {
        return Component.text("\u2726 " + text, NamedTextColor.GOLD);
    }

    private static void appendLore(ItemMeta meta, List<Component> lines) {
        if (lines.isEmpty()) {
            return;
        }
        List<Component> lore = meta.lore() == null ? new ArrayList<>() : new ArrayList<>(meta.lore());
        lore.addAll(lines);
        meta.lore(lore);
    }
}
