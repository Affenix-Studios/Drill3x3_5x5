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

public class SoulsGiveCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command is only for players.", NamedTextColor.DARK_RED));
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(Component.text("Usage: /soulsgive <player> <amount>", NamedTextColor.RED));
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(Component.text("The amount must be a number.", NamedTextColor.RED));
            return true;
        }
        if (amount <= 0) {
            player.sendMessage(Component.text("The amount must be positive.", NamedTextColor.RED));
            return true;
        }

        OfflinePlayer target = getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            player.sendMessage(Component.text("Player \"" + args[0] + "\" not found.", NamedTextColor.RED));
            return true;
        }

        if (SoulManager.getSouls(player) < amount) {
            player.sendMessage(Component.text("You don't have enough souls!", NamedTextColor.RED));
            return true;
        }

        SoulManager.spendSouls(player, amount);
        SoulManager.addSouls(target, amount);

        player.sendMessage(Component.text("You gave " + amount + " souls to " + target.getName()
                        + ". You now have " + SoulManager.getSouls(player) + " souls. ", NamedTextColor.GREEN)
                .append(SoulChat.shopLink("Your Souls: " + SoulManager.getSouls(player))));

        Player online = target.getPlayer();
        if (online != null) {
            online.sendMessage(Component.text("You received " + amount + " souls from " + player.getName()
                            + "! You now have " + SoulManager.getSouls(target) + " souls. ", NamedTextColor.AQUA)
                    .append(SoulChat.shopLink("Your Souls: " + SoulManager.getSouls(target))));
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
