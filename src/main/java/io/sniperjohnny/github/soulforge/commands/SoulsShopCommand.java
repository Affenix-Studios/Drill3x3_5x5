package io.sniperjohnny.github.soulforge.commands;

import io.sniperjohnny.github.soulforge.guis.SoulsShopMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SoulsShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command is only for players.", NamedTextColor.DARK_RED));
            return true;
        }
        new SoulsShopMenu(player).open(player);
        return true;
    }
}
