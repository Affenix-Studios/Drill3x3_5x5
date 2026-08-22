package io.sniperjohnny.github.soulforge.customItem;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.Consumer;

public final class DrillEnchantments {

    private DrillEnchantments() {
    }

    private enum DrillType {
        DRILL_3X3(6, 4),
        DRILL_5X5(7, 5);

        private final int efficiencyLevel;
        private final int fortuneLevel;

        DrillType(int efficiencyLevel, int fortuneLevel) {
            this.efficiencyLevel = efficiencyLevel;
            this.fortuneLevel = fortuneLevel;
        }
    }

    public static int applyToInventory(Player player, Consumer<ItemStack> action) {
        int count = 0;
        PlayerInventory inventory = player.getInventory();

        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (isDrill(item)) {
                action.accept(item);
                count++;
            }
        }
        inventory.setContents(contents);

        ItemStack offHand = inventory.getItemInOffHand();
        if (isDrill(offHand)) {
            action.accept(offHand);
            inventory.setItemInOffHand(offHand);
            count++;
        }

        return count;
    }

    public static boolean isDrill(ItemStack item) {
        return getType(item) != null;
    }

    public static void applyFortune(ItemStack item) {
        DrillType type = getType(item);
        if (type == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        meta.removeEnchantments();
        meta.addEnchant(Enchantment.EFFICIENCY, type.efficiencyLevel, true);
        meta.addEnchant(Enchantment.FORTUNE, type.fortuneLevel, true);
        item.setItemMeta(meta);
    }

    public static void applySilkTouch(ItemStack item) {
        DrillType type = getType(item);
        if (type == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        meta.removeEnchantments();
        meta.addEnchant(Enchantment.EFFICIENCY, type.efficiencyLevel, true);
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        item.setItemMeta(meta);
    }

    private static DrillType getType(ItemStack item) {
        if (item == null || item.getType().isAir() || !item.hasItemMeta()) {
            return null;
        }

        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        if (pdc.has(ToolKeys.DRILL_5X5, PersistentDataType.BYTE)) {
            return DrillType.DRILL_5X5;
        }
        if (pdc.has(ToolKeys.DRILL_3X3, PersistentDataType.BYTE)) {
            return DrillType.DRILL_3X3;
        }
        return null;
    }
}
