package io.sniperjohnny.github.drill_3x3_and_5x5.craftingRecipe;

import io.sniperjohnny.github.drill_3x3_and_5x5.customItem.DrillItem3x3;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Recipe3x3 {
    public static ShapedRecipe  register3x3recipe() {
        NamespacedKey key = DrillItem3x3.key;
        ItemStack drillitem = DrillItem3x3.getminerv2();

        ShapedRecipe recipe = new ShapedRecipe(key, drillitem);
        recipe.shape("DDD", "DPD", "DDD");
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('P', Material.NETHERITE_PICKAXE);



        return recipe;
    }
}
