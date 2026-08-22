package io.sniperjohnny.github.soulforge.souls;

import org.bukkit.inventory.ItemStack;

public record ShopUpgrade(String key, String name, String costPath, int defaultCost, int maxLevel, ItemStack icon) {
}
