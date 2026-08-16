package io.sniperjohnny.github.drill_3x3_and_5x5;

import io.sniperjohnny.github.drill_3x3_and_5x5.commands.OpCustomItemGiver;
import io.sniperjohnny.github.drill_3x3_and_5x5.commands.ShowRecipe3x3;
import io.sniperjohnny.github.drill_3x3_and_5x5.commands.ShowRecipe5x5;
import io.sniperjohnny.github.drill_3x3_and_5x5.craftingRecipe.Recipe3x3;
import io.sniperjohnny.github.drill_3x3_and_5x5.craftingRecipe.Recipe5x5;
import io.sniperjohnny.github.drill_3x3_and_5x5.listener.BlockBreakListener;
import io.sniperjohnny.github.drill_3x3_and_5x5.listener.InventoryListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Drill_3x3_and_5x5 extends JavaPlugin {

    private static Drill_3x3_and_5x5 instance;

    @Override
    public void onEnable() {
        // 1. Assign instance first
        instance = this;

        // 2. Register recipes AFTER instance is set
        getServer().addRecipe(Recipe3x3.register3x3recipe());
        getServer().addRecipe(Recipe5x5.register5x5recipe());

        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getCommand("opci").setExecutor(new OpCustomItemGiver());
        getCommand("5x5recipe").setExecutor(new ShowRecipe5x5());
        getCommand("3x3recipe").setExecutor(new ShowRecipe3x3());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Drill_3x3_and_5x5 getInstance() {
        return instance;
    }
}