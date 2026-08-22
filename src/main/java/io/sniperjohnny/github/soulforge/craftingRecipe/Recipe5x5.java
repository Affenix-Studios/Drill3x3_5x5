package io.sniperjohnny.github.soulforge.craftingRecipe;

import io.sniperjohnny.github.soulforge.customItem.DrillItem5x5;
import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Recipe5x5 {
    public static ShapedRecipe register5x5recipe() {
        NamespacedKey key = ToolKeys.DRILL_5X5;
        ItemStack drillitem = DrillItem5x5.getminerv3();

        ShapedRecipe recipe = new ShapedRecipe(key, drillitem);
        recipe.shape("NNN", "NPN", "NNN");
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        recipe.setIngredient('P', Material.NETHERITE_PICKAXE);

        return recipe;
    }
}