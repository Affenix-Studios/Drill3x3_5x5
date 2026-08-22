package io.sniperjohnny.github.soulforge.guis;

import io.sniperjohnny.github.soulforge.customItem.TimberAxe;
import io.sniperjohnny.github.soulforge.menu.SimpleMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RecipeTimberAxeDrill extends SimpleMenu {

    public RecipeTimberAxeDrill() {
        super(Rows.FIVE, Component.text("Recipe for the Timber Axe"));
    }

    @Override
    public void onSetItems() {
        final ItemStack grayOutline = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        final ItemMeta grayOutlineMeta = grayOutline.getItemMeta();
        grayOutlineMeta.displayName(Component.text(" "));
        grayOutline.setItemMeta(grayOutlineMeta);

        final ItemStack resultArrow = new ItemStack(Material.ARROW);
        final ItemMeta resultArrowMeta = resultArrow.getItemMeta();
        resultArrowMeta.displayName(Component.text(" "));
        resultArrow.setItemMeta(resultArrowMeta);

        for (int i = 0; i < 45; i++) {
            setItem(i, grayOutline);
        }

        final ItemStack log = new ItemStack(Material.OAK_LOG);
        final ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT);
        final ItemStack stick = new ItemStack(Material.STICK);

        setItem(10, log);
        setItem(11, log);
        setItem(12, log);
        setItem(19, log);
        setItem(20, netherite);
        setItem(21, log);
        setItem(29, stick);

        setItem(23, resultArrow);
        setItem(25, TimberAxe.getTimberAxe());
    }
}
