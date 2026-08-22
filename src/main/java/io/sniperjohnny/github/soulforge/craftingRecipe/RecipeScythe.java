package io.sniperjohnny.github.soulforge.craftingRecipe;

import io.sniperjohnny.github.soulforge.customItem.Scythe;
import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeScythe {

    public static ShapedRecipe registerScytheRecipe() {
        NamespacedKey key = ToolKeys.SCYTHE;
        ItemStack item = Scythe.getScythe();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape(" NN", "N  ", "S  ");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('S', Material.STICK);

        return recipe;
    }
}
