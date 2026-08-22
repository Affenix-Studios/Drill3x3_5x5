package io.sniperjohnny.github.soulforge.guis;

import io.sniperjohnny.github.soulforge.customItem.TntPickaxe;
import io.sniperjohnny.github.soulforge.menu.SimpleMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RecipeTntPickaxeDrill extends SimpleMenu {

    public RecipeTntPickaxeDrill() {
        super(Rows.FIVE, Component.text("Recipe for the TNT Pickaxe"));
    }

    @Override
    public void onSetItems() {
        final ItemStack tntPickaxe = TntPickaxe.getTntPickaxe();

        final ItemStack grayOutline = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        final ItemMeta grayOutlineMeta = grayOutline.getItemMeta();
        grayOutlineMeta.displayName(Component.text(" "));
        grayOutline.setItemMeta(grayOutlineMeta);

        final ItemStack resultArrow = new ItemStack(Material.ARROW);
        final ItemMeta resultArrowMeta = resultArrow.getItemMeta();
        resultArrowMeta.displayName(Component.text(" "));
        resultArrow.setItemMeta(resultArrowMeta);

        setItem(0, grayOutline);
        setItem(1, grayOutline);
        setItem(2, grayOutline);
        setItem(3, grayOutline);
        setItem(4, grayOutline);
        setItem(5, grayOutline);
        setItem(6, grayOutline);
        setItem(7, grayOutline);
        setItem(8, grayOutline);
        setItem(9, grayOutline);
        setItem(10, new ItemStack(Material.TNT));
        setItem(11, new ItemStack(Material.TNT));
        setItem(12, new ItemStack(Material.TNT));
        setItem(13, grayOutline);
        setItem(14, grayOutline);
        setItem(15, grayOutline);
        setItem(16, grayOutline);
        setItem(17, grayOutline);
        setItem(18, grayOutline);
        setItem(19, new ItemStack(Material.TNT));
        setItem(20, new ItemStack(Material.NETHERITE_PICKAXE));
        setItem(21, new ItemStack(Material.TNT));
        setItem(22, grayOutline);
        setItem(23, resultArrow);
        setItem(24, grayOutline);
        setItem(25, tntPickaxe);
        setItem(26, grayOutline);
        setItem(27, grayOutline);
        setItem(28, new ItemStack(Material.TNT));
        setItem(29, new ItemStack(Material.TNT));
        setItem(30, new ItemStack(Material.TNT));
        setItem(31, grayOutline);
        setItem(32, grayOutline);
        setItem(33, grayOutline);
        setItem(34, grayOutline);
        setItem(35, grayOutline);
        setItem(36, grayOutline);
        setItem(37, grayOutline);
        setItem(38, grayOutline);
        setItem(39, grayOutline);
        setItem(40, grayOutline);
        setItem(41, grayOutline);
        setItem(42, grayOutline);
        setItem(43, grayOutline);
        setItem(44, grayOutline);
    }
}
