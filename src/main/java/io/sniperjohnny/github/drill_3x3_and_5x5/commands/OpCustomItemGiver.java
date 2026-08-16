package io.sniperjohnny.github.drill_3x3_and_5x5.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

import static io.sniperjohnny.github.drill_3x3_and_5x5.customItem.DrillItem3x3.getminerv2;
import static io.sniperjohnny.github.drill_3x3_and_5x5.customItem.DrillItem5x5.getminerv3;

@SuppressWarnings("UnstableApiUsage")
public class OpCustomItemGiver implements TabExecutor {





    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage(ChatColor.DARK_RED + "This command is only for players.");
            return true;
        }

        if (args.length < 3) {
            p.sendMessage(ChatColor.RED + "Usage: /opci give/remove <player> <item>");
            return true;
        }

        String sub = args[0];
        String playerName = args[1];
        String itemName = args[2];

        if (!sub.equals("give")) {
            if (sub.equals("remove")){
                Player target = Bukkit.getPlayerExact(playerName);
                if (target == null) {
                    p.sendMessage(ChatColor.RED + "Player not found.");
                    return true;
                }


                if (itemName.equalsIgnoreCase("drill_3x3")) {
                    target.getInventory().removeItem(getminerv2());
                    p.sendMessage(ChatColor.BLACK + target.getName() + " has lost the Drill 3x3.");
                    return true;
                }
                if (itemName.equalsIgnoreCase("drill_5x5")) {
                    target.getInventory().removeItem(getminerv3());
                    p.sendMessage(ChatColor.BLACK + target.getName() + " has lost the Drill 5x5.");
                    return true;
                }

                p.sendMessage(ChatColor.RED + "Unknown item.");
                return true;

            }else {
                p.sendMessage("Unknown Command");
                return true;
            }

        }





        Player target = Bukkit.getPlayerExact(playerName);
        if (target == null) {
            p.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }


        if (itemName.equalsIgnoreCase("drill_3x3")) {
            target.getInventory().addItem(getminerv2());
            p.sendMessage(ChatColor.BLACK + target.getName() + " has become a stronger miner.");
            return true;
        }
        if (itemName.equalsIgnoreCase("drill_5x5")) {
            target.getInventory().addItem(getminerv3());
            p.sendMessage(ChatColor.BLACK + target.getName() + " has become an even stronger miner.");
            return true;
        }


        p.sendMessage(ChatColor.RED + "Unknown item.");
        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender,
                                               @NotNull Command command,
                                               @NotNull String label,
                                               @NotNull String[] args) {

        List<String> completions = new ArrayList<>();

        // /ci <subcommand>
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], List.of("give", "remove"), completions);
            return completions;
        }

        // /ci give <player>
        if (args.length == 2) {
            List<String> players = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                players.add(p.getName());
            }
            StringUtil.copyPartialMatches(args[1], players, completions);
            return completions;
        }

        // /ci give <player> <item>
        if (args.length == 3) {
            StringUtil.copyPartialMatches(args[2], List.of("drill_3x3", "drill_5x5"), completions);
            return completions;
        }

        return completions;
    }
}
