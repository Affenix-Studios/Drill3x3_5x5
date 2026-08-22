package io.sniperjohnny.github.soulforge.guis;

import io.sniperjohnny.github.soulforge.customItem.ItemStyle;
import io.sniperjohnny.github.soulforge.listener.CraftListener;
import io.sniperjohnny.github.soulforge.menu.SimpleMenu;
import io.sniperjohnny.github.soulforge.souls.ShopUpgrade;
import io.sniperjohnny.github.soulforge.souls.ShopUpgrades;
import io.sniperjohnny.github.soulforge.souls.SoulManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SoulsShopMenu extends SimpleMenu {

    private static final int SOULS_SLOT = 4;
    private static final String GOLD_BORDER = "\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501";
    private static final int[] SLOTS = {10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 21, 22};

    private Player viewer;

    public SoulsShopMenu(Player player) {
        super(Rows.THREE, Component.text("Soul Shop — ")
                .append(Component.text(SoulManager.getSouls(player) + " Souls", NamedTextColor.AQUA)));
        this.viewer = player;
    }

    @Override
    public void open(Player p) {
        this.viewer = p;
        super.open(p);
    }

    @Override
    public void onSetItems() {
        ItemStack grayOutline = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta outlineMeta = grayOutline.getItemMeta();
        outlineMeta.displayName(Component.text(" "));
        grayOutline.setItemMeta(outlineMeta);

        for (int i = 0; i < 27; i++) {
            if (i < 9 || i >= 18 || i % 9 == 0 || i % 9 == 8) {
                setItem(i, grayOutline);
            }
        }

        if (viewer == null) {
            return;
        }

        setItem(SOULS_SLOT, soulDisplay(viewer));

        for (int i = 0; i < ShopUpgrades.ALL.size(); i++) {
            addUnlock(SLOTS[i], ShopUpgrades.ALL.get(i));
        }
        setItem(24, infoItem());
    }

    private void addUnlock(int slot, ShopUpgrade upgrade) {
        int level = viewer != null ? SoulManager.getUnlockLevel(viewer, upgrade.key()) : 0;
        int max = upgrade.maxLevel();
        boolean maxed = level >= (max == 1 ? 1 : max);

        ItemStack item = upgrade.icon().clone();
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(upgrade.name(), maxed ? NamedTextColor.YELLOW : NamedTextColor.GOLD));
        List<Component> lore = meta.lore() == null ? new ArrayList<>() : new ArrayList<>(meta.lore());

        if (maxed) {
            lore.add(Component.text(GOLD_BORDER, NamedTextColor.GOLD));
            lore.add(Component.text(max == 1 ? "Unlocked" : "MAX level " + ItemStyle.roman(max), NamedTextColor.GREEN));
            lore.add(Component.text(max == 1 ? "Applies to every tool you craft" : "Fully upgraded",
                    NamedTextColor.DARK_GREEN));
            lore.add(Component.text(GOLD_BORDER, NamedTextColor.GOLD));
        } else if (max == 1) {
            lore.add(Component.text("Cost: " + ShopUpgrades.cost(upgrade) + " souls", NamedTextColor.GRAY));
            lore.add(Component.text("Click to unlock permanently", NamedTextColor.DARK_GRAY));
        } else {
            int nextCost = ShopUpgrades.cost(upgrade) * (level + 1);
            lore.add(Component.text("Level " + ItemStyle.roman(level + 1) + " of " + ItemStyle.roman(max),
                    NamedTextColor.GRAY));
            lore.add(Component.text("Cost: " + nextCost + " souls", NamedTextColor.GRAY));
            lore.add(Component.text("Click to upgrade", NamedTextColor.DARK_GRAY));
        }

        meta.lore(lore);
        item.setItemMeta(meta);

        setItem(slot, item, player -> purchaseUnlock(player, upgrade));
    }

    private void purchaseUnlock(Player player, ShopUpgrade upgrade) {
        int level = SoulManager.getUnlockLevel(player, upgrade.key());
        int max = upgrade.maxLevel();
        if (level >= max) {
            player.sendMessage(Component.text(upgrade.name() + " is already at max level.", NamedTextColor.GREEN));
            return;
        }
        int nextCost = ShopUpgrades.cost(upgrade) * (level + 1);
        if (!SoulManager.spendSouls(player, nextCost)) {
            player.sendMessage(Component.text("You don't have enough souls!", NamedTextColor.RED));
            return;
        }
        SoulManager.setUnlockLevel(player, upgrade.key(), level + 1);
        CraftListener.refreshInventory(player);
        if (max == 1) {
            player.sendMessage(Component.text(upgrade.name() + " unlocked! It now applies to every tool you craft.",
                    NamedTextColor.GREEN));
        } else {
            player.sendMessage(Component.text(upgrade.name() + " upgraded to level "
                    + ItemStyle.roman(level + 1) + "!", NamedTextColor.GREEN));
        }
        new SoulsShopMenu(player).open(player);
    }

    private ItemStack infoItem() {
        ItemStack item = new ItemStack(Material.SOUL_LANTERN);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("How it works", NamedTextColor.LIGHT_PURPLE));
        meta.lore(List.of(
                Component.text("Upgrades are permanent and per player.", NamedTextColor.GRAY),
                Component.text("Stackable upgrades get stronger each level.", NamedTextColor.GRAY),
                Component.text("Craft the tool again to receive the upgrades.", NamedTextColor.GRAY)
        ));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack soulDisplay(Player player) {
        ItemStack item = new ItemStack(Material.SOUL_SAND);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Your Souls: " + SoulManager.getSouls(player), NamedTextColor.AQUA));
        meta.lore(List.of(Component.text("Spend them on permanent upgrades below", NamedTextColor.GRAY)));
        item.setItemMeta(meta);
        return item;
    }
}
