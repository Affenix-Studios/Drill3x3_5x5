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
public class TimberAxe {

    public static ItemStack getTimberAxe() {
        ItemStack item = ItemStack.of(Material.NETHERITE_AXE);
        ItemMeta meta = item.getItemMeta();

        double cooldown = SoulForge.getTimberCooldownSeconds();
        String cooldownText = cooldown == Math.floor(cooldown) ? String.valueOf((int) cooldown) : String.valueOf(cooldown);
        meta.lore(List.of(
                Component.text("Fells whole trees. " + cooldownText + "s cooldown between fells.", NamedTextColor.DARK_RED),
                Component.text("Upgrade me in /soulsshop", NamedTextColor.GRAY)
        ));

        meta.getPersistentDataContainer().set(ToolKeys.TIMBER_AXE, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        item.setData(DataComponentTypes.ITEM_NAME, ItemStyle.name("Timber Axe", NamedTextColor.GOLD));
        item.setData(DataComponentTypes.CUSTOM_NAME, ItemStyle.name("Timber Axe", NamedTextColor.GOLD));
        item.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
        if (SoulForge.isTexturePackWanted()) {
            item.setData(DataComponentTypes.ITEM_MODEL, Key.key("soulforge", "item/timber_axe"));
        }

        return item;
    }
}
