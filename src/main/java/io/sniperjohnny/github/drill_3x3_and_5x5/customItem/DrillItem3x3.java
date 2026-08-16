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

public class DrillItem3x3 {
    // Safe for static initialization before onEnable runs
    public static final NamespacedKey key = new NamespacedKey("drill_3x3_and_5x5", "is_hammer_3x3");

    public static ItemStack getminerv2() {
        ItemStack item = ItemStack.of(Material.NETHERITE_PICKAXE);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.EFFICIENCY, 8, true);
        meta.addEnchant(Enchantment.FORTUNE, 6, true);

        List<String> lore = new ArrayList<>();
        lore.add("§4 This is an Custom Drill in the 3 by 3 Version");
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Drill V2", NamedTextColor.DARK_GRAY));
        item.setData(DataComponentTypes.UNBREAKABLE);
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Drill V2", NamedTextColor.DARK_GRAY));

        return item;
    }
}