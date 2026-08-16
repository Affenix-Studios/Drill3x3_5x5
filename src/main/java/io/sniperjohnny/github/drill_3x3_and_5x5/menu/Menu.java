package io.sniperjohnny.github.drill_3x3_and_5x5.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface Menu extends InventoryHolder {
    void click(Player p, int slot);

    void setItem(int slot, ItemStack item);

    void setItem(int slot, ItemStack item, Consumer<Player> action);

    void onSetItems();

    default void open(Player p) {
        onSetItems();
        p.openInventory(getInventory());
    }
}