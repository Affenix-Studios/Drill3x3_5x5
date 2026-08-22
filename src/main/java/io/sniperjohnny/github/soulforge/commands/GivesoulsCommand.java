package io.sniperjohnny.github.soulforge.commands;

import io.sniperjohnny.github.soulforge.souls.SoulManager;
import io.sniperjohnny.github.soulforge.util.SoulChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GivesoulsCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Component.text("Usage: /givesouls <player> <amount>", NamedTextColor.RED));
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Component.text("The amount must be a number.", NamedTextColor.RED));
            return true;
        }
        if (amount < 0) {
            sender.sendMessage(Component.text("The amount must not be negative.", NamedTextColor.RED));
            return true;
        }

        OfflinePlayer target = getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(Component.text("Player \"" + args[0] + "\" not found.", NamedTextColor.RED));
            return true;
        }

        SoulManager.addSouls(target, amount);
        sender.sendMessage(Component.text("Gave " + amount + " souls to " + target.getName() + ". They now have "
                + SoulManager.getSouls(target) + " souls.", NamedTextColor.GREEN));

        Player online = target.getPlayer();
        if (online != null) {
            int souls = SoulManager.getSouls(target);
            online.sendMessage(Component.text("You received " + amount + " souls! You now have "
                            + souls + " souls. ", NamedTextColor.AQUA)
                    .append(SoulChat.shopLink("Your Souls: " + souls)));
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    private OfflinePlayer getOfflinePlayer(String name) {
        OfflinePlayer cached = Bukkit.getOfflinePlayerIfCached(name);
        return cached != null ? cached : Bukkit.getOfflinePlayer(name);
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                               @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            List<String> players = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                players.add(p.getName());
            }
            StringUtil.copyPartialMatches(args[0], players, completions);
        }
        return completions;
    }
}
