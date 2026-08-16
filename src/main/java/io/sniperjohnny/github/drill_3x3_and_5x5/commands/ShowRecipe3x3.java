package io.sniperjohnny.github.drill_3x3_and_5x5.commands;

import io.sniperjohnny.github.drill_3x3_and_5x5.guis.Recipe3x3Drill;
import io.sniperjohnny.github.drill_3x3_and_5x5.guis.Recipe5x5Drill;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowRecipe3x3 implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(sender instanceof final Player p))
        {
            sender.sendMessage("Der Befehl ist nur für spieler");
            return true;
        }
        new Recipe3x3Drill().open(p);
        return true;
    }
}