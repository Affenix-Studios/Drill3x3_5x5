package io.sniperjohnny.github.drill_3x3_and_5x5.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class SimpleMenu implements Menu{

    private final Map<Integer, Consumer<Player>> actions = new HashMap<>();
    private final Inventory inv;
    public SimpleMenu(Rows rows, String title ) {
        this.inv = Bukkit.createInventory(this, rows.getSize(), title);
    }
    @Override
    public void click(Player p, int slot) {
        final Consumer<Player> action = this.actions.get(slot);

        if (action != null) {
            action.accept(p);
        }
    }

    @Override
    public void setItem(int slot, ItemStack item) {
        setItem(slot, item, player -> {});
    }

    @Override
    public void setItem(int slot, ItemStack item, Consumer<Player> action) {
        this.actions.put(slot, action);
        getInventory().setItem(slot, item);
    }

    public abstract void onSetItems();

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
    public enum Rows {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5);

        private final int size;
        Rows(int rows) {
            this.size = rows * 9;
        }
        public int getSize() {
            return size;
        }
    }
}
