package io.sniperjohnny.github.soulforge.commands;

import io.sniperjohnny.github.soulforge.guis.RecipeTntPickaxeDrill;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowRecipeTntPickaxe implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(sender instanceof final Player p)) {
            sender.sendMessage("Der Befehl ist nur für spieler");
            return true;
        }
        new RecipeTntPickaxeDrill().open(p);
        return true;
    }
}
