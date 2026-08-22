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
public class TntPickaxe {

    public static ItemStack getTntPickaxe() {
        ItemStack item = ItemStack.of(Material.NETHERITE_PICKAXE);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.EFFICIENCY, 6, true);

        double chance = SoulForge.getTntChance();
        String chanceText = chance == Math.floor(chance) ? String.valueOf((int) chance) : String.valueOf(chance);
        meta.lore(List.of(
                Component.text(chanceText + "% chance to spawn TNT on block break", NamedTextColor.DARK_RED),
                Component.text("Upgrade me in /soulsshop", NamedTextColor.GRAY)
        ));

        meta.getPersistentDataContainer().set(ToolKeys.TNT_PICKAXE, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        item.setData(DataComponentTypes.ITEM_NAME, ItemStyle.name("TNT Pickaxe", NamedTextColor.RED));
        item.setData(DataComponentTypes.CUSTOM_NAME, ItemStyle.name("TNT Pickaxe", NamedTextColor.RED));
        item.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
        if (SoulForge.isTexturePackWanted()) {
            item.setData(DataComponentTypes.ITEM_MODEL, Key.key("soulforge", "item/tnt_pickaxe"));
        }

        return item;
    }
}
