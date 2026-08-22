package io.sniperjohnny.github.soulforge.customItem;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.sniperjohnny.github.soulforge.SoulForge;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class Scythe {

    public static ItemStack getScythe() {
        ItemStack item = ItemStack.of(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();

        double chance = SoulForge.getScytheChance();
        String chanceText = chance == Math.floor(chance) ? String.valueOf((int) chance) : String.valueOf(chance);
        meta.lore(List.of(Component.text(chanceText + "% chance to harvest a soul from slain mobs", NamedTextColor.DARK_RED)));

        meta.getPersistentDataContainer().set(ToolKeys.SCYTHE, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        item.setData(DataComponentTypes.ITEM_NAME, ItemStyle.name("\u2726 Scythe of the Grim Reaper", NamedTextColor.DARK_RED));
        item.setData(DataComponentTypes.UNBREAKABLE);
        item.setData(DataComponentTypes.CUSTOM_NAME, ItemStyle.name("\u2726 Scythe of the Grim Reaper", NamedTextColor.DARK_RED));
        item.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
        if (SoulForge.isTexturePackWanted()) {
            item.setData(DataComponentTypes.ITEM_MODEL, Key.key("soulforge", "item/scythe"));
        }

        return item;
    }
}
