package io.sniperjohnny.github.soulforge.craftingRecipe;

import io.sniperjohnny.github.soulforge.customItem.DrillItem3x3;
import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Recipe3x3 {
    public static ShapedRecipe  register3x3recipe() {
        NamespacedKey key = ToolKeys.DRILL_3X3;
        ItemStack drillitem = DrillItem3x3.getminerv2();

        ShapedRecipe recipe = new ShapedRecipe(key, drillitem);
        recipe.shape("DDD", "DPD", "DDD");
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('P', Material.NETHERITE_PICKAXE);



        return recipe;
    }
}
