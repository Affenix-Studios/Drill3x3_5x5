package io.sniperjohnny.github.soulforge.commands;

import io.sniperjohnny.github.soulforge.customItem.DrillEnchantments;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FortuneCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You need to be a player to use this command.");
            return true;
        }

        int changed = DrillEnchantments.applyToInventory(player, DrillEnchantments::applyFortune);

        if (changed == 0) {
            player.sendMessage("No drills found in your inventory.");
        } else {
            player.sendMessage("Fortune applied to " + changed + " drill(s).");
        }
        return true;
    }
}
