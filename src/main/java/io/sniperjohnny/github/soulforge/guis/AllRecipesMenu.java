package io.sniperjohnny.github.soulforge.guis;

import io.sniperjohnny.github.soulforge.customItem.DrillItem3x3;
import io.sniperjohnny.github.soulforge.customItem.DrillItem5x5;
import io.sniperjohnny.github.soulforge.customItem.Murasame;
import io.sniperjohnny.github.soulforge.customItem.Scythe;
import io.sniperjohnny.github.soulforge.customItem.TimberAxe;
import io.sniperjohnny.github.soulforge.customItem.TntPickaxe;
import io.sniperjohnny.github.soulforge.menu.SimpleMenu;
import io.sniperjohnny.github.soulforge.souls.SoulManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AllRecipesMenu extends SimpleMenu {

    public AllRecipesMenu() {
        super(Rows.THREE, Component.text("All Recipes"));
    }

    @Override
    public void onSetItems() {
        final ItemStack grayOutline = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        final ItemMeta grayOutlineMeta = grayOutline.getItemMeta();
        grayOutlineMeta.displayName(Component.text(" "));
        grayOutline.setItemMeta(grayOutlineMeta);

        final ItemStack searchFeather = new ItemStack(Material.FEATHER);
        final ItemMeta searchFeatherMeta = searchFeather.getItemMeta();
        searchFeatherMeta.displayName(Component.text("Search recipes"));
        searchFeatherMeta.lore(List.of(Component.text("Click and type a recipe name in chat")));
        searchFeather.setItemMeta(searchFeatherMeta);

        for (int i = 0; i < 27; i++) {
            if (i < 9 || i >= 18 || i % 9 == 0 || i % 9 == 8) {
                setItem(i, grayOutline);
            }
        }

        setItem(10, DrillItem3x3.getminerv2(), player -> new Recipe3x3Drill().open(player));
        setItem(11, Scythe.getScythe(), player -> new RecipeScytheDrill().open(player));
        setItem(12, TimberAxe.getTimberAxe(), player -> new RecipeTimberAxeDrill().open(player));
        setItem(13, DrillItem5x5.getminerv3(), player -> new Recipe5x5Drill().open(player));
        setItem(14, TntPickaxe.getTntPickaxe(), player -> new RecipeTntPickaxeDrill().open(player));
        setItem(15, Murasame.getMurasame(), player -> {
            if (SoulManager.hasUnlock(player, SoulManager.MURASAME_CRAFT)) {
                new RecipeMurasameDrill().open(player);
            } else {
                player.sendMessage(Component.text(
                        "Murasame's recipe is locked. Unlock it in /soulsshop!", NamedTextColor.RED));
            }
        });

        setItem(22, searchFeather, RecipeSearchDialog::open);
    }
}
