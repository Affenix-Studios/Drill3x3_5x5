package io.sniperjohnny.github.drill_3x3_and_5x5.craftingRecipe;

import io.sniperjohnny.github.drill_3x3_and_5x5.customItem.DrillItem5x5;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Recipe5x5 {
    public static ShapedRecipe register5x5recipe() {
        NamespacedKey key = DrillItem5x5.key;
        ItemStack drillitem = DrillItem5x5.getminerv3(); // Fixed item result here

        ShapedRecipe recipe = new ShapedRecipe(key, drillitem);
        recipe.shape("NNN", "NPN", "NNN");
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        recipe.setIngredient('P', Material.NETHERITE_PICKAXE);

        return recipe;
    }
}