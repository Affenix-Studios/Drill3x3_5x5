package io.sniperjohnny.github.soulforge.craftingRecipe;

import io.sniperjohnny.github.soulforge.customItem.Murasame;
import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeMurasame {

    public static ShapedRecipe registerMurasameRecipe() {
        NamespacedKey key = ToolKeys.MURASAME;
        ItemStack item = Murasame.getMurasame();

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("SNS", "NWN", "SNS");
        recipe.setIngredient('S', Material.WITHER_SKELETON_SKULL);
        recipe.setIngredient('W', Material.NETHERITE_SWORD);
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);

        return recipe;
    }
}
