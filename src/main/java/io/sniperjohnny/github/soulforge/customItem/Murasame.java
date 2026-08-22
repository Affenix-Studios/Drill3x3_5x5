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
public class Murasame {

    public static ItemStack getMurasame() {
        ItemStack item = ItemStack.of(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.SHARPNESS, 9, true);
        meta.addEnchant(Enchantment.LOOTING, 10, true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 4, true);
        meta.addEnchant(Enchantment.SWEEPING_EDGE, 10, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);

        meta.lore(List.of(
                Component.text("One-shots mobs with a spreading curse.", NamedTextColor.DARK_RED),
                Component.text("Its powers do not work on players.", NamedTextColor.DARK_GRAY)
        ));

        meta.getPersistentDataContainer().set(ToolKeys.MURASAME, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        item.setData(DataComponentTypes.ITEM_NAME, ItemStyle.blade("Murasame", NamedTextColor.DARK_RED));
        item.setData(DataComponentTypes.UNBREAKABLE);
        item.setData(DataComponentTypes.CUSTOM_NAME, ItemStyle.blade("Murasame", NamedTextColor.DARK_RED));
        item.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
        if (SoulForge.isTexturePackWanted()) {
            item.setData(DataComponentTypes.ITEM_MODEL, Key.key("soulforge", "item/murasame"));
        }

        return item;
    }
}
