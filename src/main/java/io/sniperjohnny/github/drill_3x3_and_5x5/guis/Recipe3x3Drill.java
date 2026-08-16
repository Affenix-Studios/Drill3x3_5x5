package io.sniperjohnny.github.drill_3x3_and_5x5.guis;

import io.sniperjohnny.github.drill_3x3_and_5x5.customItem.DrillItem3x3;
import io.sniperjohnny.github.drill_3x3_and_5x5.customItem.DrillItem5x5;
import io.sniperjohnny.github.drill_3x3_and_5x5.menu.SimpleMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipe3x3Drill extends SimpleMenu {

    public Recipe3x3Drill() {
        super(Rows.FIVE, "This is the recipe for the items you need to revive someone");
    }

    @Override
    public void onSetItems() {
        final ItemStack drillitem3x3 = DrillItem3x3.getminerv2();


        final ItemStack gray_outline = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        final ItemMeta grayoutlineMeta = gray_outline.getItemMeta();
        grayoutlineMeta.displayName(Component.text(" "));
        gray_outline.setItemMeta(grayoutlineMeta);

        final ItemStack resultArrow = new ItemStack(Material.ARROW);
        final ItemMeta resultArrowMeta = resultArrow.getItemMeta();
        resultArrowMeta.displayName(Component.text(" "));
        resultArrow.setItemMeta(resultArrowMeta);



        //Gray Outline
        setItem(0, gray_outline);
        setItem(1, gray_outline);
        setItem(2, gray_outline);
        setItem(3, gray_outline);
        setItem(4, gray_outline);
        setItem(5, gray_outline);
        setItem(6, gray_outline);
        setItem(7, gray_outline);
        setItem(8, gray_outline);
        setItem(9, gray_outline);
        setItem(10, new ItemStack(Material.DIAMOND_BLOCK));
        setItem(11, new ItemStack(Material.DIAMOND_BLOCK));
        setItem(12, new ItemStack(Material.DIAMOND_BLOCK));
        setItem(13, gray_outline);
        setItem(14, gray_outline);
        setItem(15, gray_outline);
        setItem(16, gray_outline);
        setItem(17, gray_outline);
        setItem(18, gray_outline);
        setItem(19, new ItemStack(Material.DIAMOND_BLOCK));
        setItem(20, new ItemStack(Material.NETHERITE_PICKAXE));
        setItem(21, new ItemStack(Material.DIAMOND_BLOCK));
        setItem(22, gray_outline);
        setItem(23, resultArrow);
        setItem(24, gray_outline);
        setItem(25, drillitem3x3);
        setItem(26, gray_outline);
        setItem(27, gray_outline);
        setItem(28, new ItemStack(Material.DIAMOND_BLOCK));
        setItem(29, new ItemStack(Material.DIAMOND_BLOCK));
        setItem(30, new ItemStack(Material.DIAMOND_BLOCK));
        setItem(31, gray_outline);
        setItem(32, gray_outline);
        setItem(33, gray_outline);
        setItem(34, gray_outline);
        setItem(35, gray_outline);
        setItem(36, gray_outline);
        setItem(37, gray_outline);
        setItem(38, gray_outline);
        setItem(39, gray_outline);
        setItem(40, gray_outline);
        setItem(41, gray_outline);
        setItem(42, gray_outline);
        setItem(43, gray_outline);
        setItem(44, gray_outline);
    }
}
