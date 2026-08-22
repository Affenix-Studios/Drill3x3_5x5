package io.sniperjohnny.github.soulforge.craftingRecipe;

import io.sniperjohnny.github.soulforge.customItem.TntPickaxe;
import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeTntPickaxe {
    public static ShapedRecipe registerTntPickaxeRecipe() {
        NamespacedKey key = ToolKeys.TNT_PICKAXE;
        ItemStack item = TntPickaxe.getTntPickaxe();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("TTT", "TPT", "TTT");
        recipe.setIngredient('T', Material.TNT);
        recipe.setIngredient('P', Material.NETHERITE_PICKAXE);

        return recipe;
    }
}
