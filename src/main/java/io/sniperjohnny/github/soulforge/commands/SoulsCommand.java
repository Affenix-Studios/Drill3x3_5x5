package io.sniperjohnny.github.soulforge.commands;

import io.sniperjohnny.github.soulforge.souls.ShopUpgrades;
import io.sniperjohnny.github.soulforge.souls.SoulManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
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

public class SoulsCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command is only for players.", NamedTextColor.DARK_RED));
            return true;
        }

        if (args.length == 0) {
            int souls = SoulManager.getSouls(player);
            List<Component> hoverLines = new ArrayList<>();
            hoverLines.add(Component.text("Your Souls: " + souls, NamedTextColor.AQUA));
            hoverLines.add(Component.text(" ", NamedTextColor.GRAY));
            hoverLines.addAll(ShopUpgrades.costLines(player));
            Component shopLink = Component.text("Soul Shop", NamedTextColor.GOLD)
                    .hoverEvent(HoverEvent.showText(Component.join(Component.newline(), hoverLines)))
                    .clickEvent(ClickEvent.runCommand("/soulsshop"));
            player.sendMessage(Component.text("You have " + souls + " souls. ", NamedTextColor.AQUA)
                    .append(shopLink));
            return true;
        }

        if (!player.hasPermission("souls.seeothers")) {
            player.sendMessage(Component.text("You don't have permission to see other players' souls.",
                    NamedTextColor.RED));
            return true;
        }

        OfflinePlayer target = getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            player.sendMessage(Component.text("Player \"" + args[0] + "\" not found.", NamedTextColor.RED));
            return true;
        }

        int souls = SoulManager.getSouls(target);
        List<Component> hoverLines = new ArrayList<>();
        hoverLines.add(Component.text(target.getName() + "'s Souls: " + souls, NamedTextColor.AQUA));
        hoverLines.add(Component.text(" ", NamedTextColor.GRAY));
        hoverLines.addAll(ShopUpgrades.costLines(target));
        Component shopLink = Component.text("Soul Shop", NamedTextColor.GOLD)
                .hoverEvent(HoverEvent.showText(Component.join(Component.newline(), hoverLines)))
                .clickEvent(ClickEvent.runCommand("/soulsshop"));
        player.sendMessage(Component.text(target.getName() + " has " + souls + " souls. ", NamedTextColor.AQUA)
                .append(shopLink));
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
