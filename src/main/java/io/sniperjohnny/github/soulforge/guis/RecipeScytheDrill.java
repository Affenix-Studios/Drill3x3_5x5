package io.sniperjohnny.github.soulforge.guis;

import io.sniperjohnny.github.soulforge.customItem.Scythe;
import io.sniperjohnny.github.soulforge.menu.SimpleMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RecipeScytheDrill extends SimpleMenu {

    public RecipeScytheDrill() {
        super(Rows.FIVE, Component.text("Recipe for the Scythe of the Grim Reaper"));
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

        final ItemStack netherite = new ItemStack(Material.NETHERITE_INGOT);
        final ItemStack stick = new ItemStack(Material.STICK);

        setItem(11, netherite);
        setItem(12, netherite);
        setItem(19, netherite);
        setItem(28, stick);

        setItem(23, resultArrow);
        setItem(25, Scythe.getScythe());
    }
}
