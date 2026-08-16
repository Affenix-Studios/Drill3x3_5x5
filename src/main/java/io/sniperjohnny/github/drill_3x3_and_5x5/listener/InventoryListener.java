package io.sniperjohnny.github.drill_3x3_and_5x5.listener;

import io.sniperjohnny.github.drill_3x3_and_5x5.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {
    @EventHandler
    public void onclick(InventoryClickEvent e) {
        final Inventory clickedInv = e.getClickedInventory();

        if(clickedInv == null) {
            return;
        }
        if(!(clickedInv.getHolder() instanceof final Menu menu))
        {
            return;
        }

        e.setCancelled(true);
        menu.click((Player) e.getWhoClicked(), e.getSlot());

    }
}