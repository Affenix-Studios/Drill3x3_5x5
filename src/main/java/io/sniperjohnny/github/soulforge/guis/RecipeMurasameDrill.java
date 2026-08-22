package io.sniperjohnny.github.soulforge.guis;

import io.sniperjohnny.github.soulforge.customItem.Murasame;
import io.sniperjohnny.github.soulforge.menu.SimpleMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RecipeMurasameDrill extends SimpleMenu {

    public RecipeMurasameDrill() {
        super(Rows.FIVE, Component.text("Recipe for Murasame"));
    }

    @Override
    public void onSetItems() {
        final ItemStack grayOutline = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        final ItemStack resultArrow = new ItemStack(Material.ARROW);
        final ItemMeta resultArrowMeta = resultArrow.getItemMeta();
        resultArrowMeta.displayName(Component.text(" "));
        resultArrow.setItemMeta(resultArrowMeta);

        for (int i = 0; i < 45; i++) {
            setItem(i, grayOutline);
        }

        final ItemStack skull = new ItemStack(Material.WITHER_SKELETON_SKULL);
        final ItemStack netheriteBlock = new ItemStack(Material.NETHERITE_BLOCK);
        final ItemStack netheriteSword = new ItemStack(Material.NETHERITE_SWORD);

        setItem(10, skull);
        setItem(11, netheriteBlock);
        setItem(12, skull);
        setItem(19, netheriteBlock);
        setItem(20, netheriteSword);
        setItem(21, netheriteBlock);
        setItem(28, skull);
        setItem(29, netheriteBlock);
        setItem(30, skull);

        setItem(23, resultArrow);
        setItem(25, Murasame.getMurasame());
    }
}
