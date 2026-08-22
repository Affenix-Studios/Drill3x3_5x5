package io.sniperjohnny.github.soulforge.customItem;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.sniperjohnny.github.soulforge.SoulForge;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class DrillItem5x5 {

    public static ItemStack getminerv3() {
        ItemStack item = ItemStack.of(Material.NETHERITE_PICKAXE);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.EFFICIENCY, 7, true);
        meta.addEnchant(Enchantment.FORTUNE, 5, true);

        meta.lore(List.of(Component.text("This is a custom drill in the 5x5 version", NamedTextColor.DARK_RED)));
        meta.getPersistentDataContainer().set(ToolKeys.DRILL_5X5, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        item.setData(DataComponentTypes.ITEM_NAME, ItemStyle.name("Drill 5x5", NamedTextColor.DARK_PURPLE));
        item.setData(DataComponentTypes.UNBREAKABLE);
        item.setData(DataComponentTypes.CUSTOM_NAME, ItemStyle.name("Drill 5x5", NamedTextColor.DARK_PURPLE));
        item.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
        if (SoulForge.isTexturePackWanted()) {
            item.setData(DataComponentTypes.ITEM_MODEL, Key.key("soulforge", "item/drill_5x5"));
        }

        return item;
    }
}
