package io.sniperjohnny.github.drill_3x3_and_5x5.customItem;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class DrillItem5x5 {

    public static final NamespacedKey key = new NamespacedKey("drill_3x3_and_5x5", "is_hammer_5x5");

    public static ItemStack getminerv3() {
        ItemStack item = ItemStack.of(Material.NETHERITE_PICKAXE);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.EFFICIENCY, 7, true);
        meta.addEnchant(Enchantment.FORTUNE, 5, true);

        List<String> lore = new ArrayList<>();
        lore.add("§4 This is an Custom Drill in the 5 by 5 Version");
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Drill 5x5", NamedTextColor.DARK_GRAY));
        item.setData(DataComponentTypes.UNBREAKABLE);
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Drill 5x5", NamedTextColor.DARK_GRAY));

        return item;
    }
}