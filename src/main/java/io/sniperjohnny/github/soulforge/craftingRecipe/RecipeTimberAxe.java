package io.sniperjohnny.github.soulforge.craftingRecipe;

import io.sniperjohnny.github.soulforge.customItem.TimberAxe;
import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeTimberAxe {

    public static ShapedRecipe registerTimberAxeRecipe() {
        NamespacedKey key = ToolKeys.TIMBER_AXE;
        ItemStack item = TimberAxe.getTimberAxe();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("LLL", "LNL", " S ");
        recipe.setIngredient('L', new RecipeChoice.MaterialChoice(Tag.LOGS));
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('S', Material.STICK);

        return recipe;
    }
}
