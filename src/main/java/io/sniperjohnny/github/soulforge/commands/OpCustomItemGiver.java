package io.sniperjohnny.github.soulforge.commands;

import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import io.sniperjohnny.github.soulforge.listener.CraftListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static io.sniperjohnny.github.soulforge.customItem.DrillItem3x3.getminerv2;
import static io.sniperjohnny.github.soulforge.customItem.DrillItem5x5.getminerv3;
import static io.sniperjohnny.github.soulforge.customItem.Murasame.getMurasame;
import static io.sniperjohnny.github.soulforge.customItem.Scythe.getScythe;
import static io.sniperjohnny.github.soulforge.customItem.TimberAxe.getTimberAxe;
import static io.sniperjohnny.github.soulforge.customItem.TntPickaxe.getTntPickaxe;

@SuppressWarnings("UnstableApiUsage")
public class OpCustomItemGiver implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage(Component.text("This command is only for players.", NamedTextColor.DARK_RED));
            return true;
        }

        if (args.length < 3) {
            p.sendMessage(Component.text("Usage: " + command.getUsage(), NamedTextColor.RED));
            return true;
        }

        String sub = args[0];
        String playerName = args[1];
        String itemName = args[2];

        Player target = Bukkit.getPlayerExact(playerName);
        if (target == null) {
            p.sendMessage(Component.text("Player not found.", NamedTextColor.RED));
            return true;
        }

        if (sub.equalsIgnoreCase("remove")) {
            if (itemName.equalsIgnoreCase("drill_3x3")) {
                removeByKey(target, ToolKeys.DRILL_3X3);
                p.sendMessage(Component.text(target.getName() + " has lost the Drill 3x3.", NamedTextColor.BLACK));
                return true;
            }
            if (itemName.equalsIgnoreCase("drill_5x5")) {
                removeByKey(target, ToolKeys.DRILL_5X5);
                p.sendMessage(Component.text(target.getName() + " has lost the Drill 5x5.", NamedTextColor.BLACK));
                return true;
            }
            if (itemName.equalsIgnoreCase("tnt_pickaxe")) {
                removeByKey(target, ToolKeys.TNT_PICKAXE);
                p.sendMessage(Component.text(target.getName() + " has lost the TNT Pickaxe.", NamedTextColor.BLACK));
                return true;
            }
            if (itemName.equalsIgnoreCase("scythe")) {
                removeByKey(target, ToolKeys.SCYTHE);
                p.sendMessage(Component.text(target.getName() + " has lost the Scythe of the Grim Reaper.", NamedTextColor.BLACK));
                return true;
            }
            if (itemName.equalsIgnoreCase("timber_axe")) {
                removeByKey(target, ToolKeys.TIMBER_AXE);
                p.sendMessage(Component.text(target.getName() + " has lost the Timber Axe.", NamedTextColor.BLACK));
                return true;
            }
            if (itemName.equalsIgnoreCase("murasame")) {
                removeByKey(target, ToolKeys.MURASAME);
                p.sendMessage(Component.text(target.getName() + " has lost Murasame.", NamedTextColor.BLACK));
                return true;
            }

            p.sendMessage(Component.text("Unknown item.", NamedTextColor.RED));
            return true;
        }

        if (!sub.equalsIgnoreCase("give")) {
            p.sendMessage(Component.text("Unknown Command"));
            return true;
        }

        if (itemName.equalsIgnoreCase("drill_3x3")) {
            target.getInventory().addItem(getminerv2());
            p.sendMessage(Component.text(target.getName() + " has become a stronger miner.", NamedTextColor.BLACK));
            return true;
        }
        if (itemName.equalsIgnoreCase("drill_5x5")) {
            target.getInventory().addItem(getminerv3());
            p.sendMessage(Component.text(target.getName() + " has become an even stronger miner.", NamedTextColor.BLACK));
            return true;
        }
        if (itemName.equalsIgnoreCase("tnt_pickaxe")) {
            target.getInventory().addItem(CraftListener.applyUnlocks(target, getTntPickaxe()));
            p.sendMessage(Component.text(target.getName() + " has received the TNT Pickaxe.", NamedTextColor.BLACK));
            return true;
        }
        if (itemName.equalsIgnoreCase("scythe")) {
            target.getInventory().addItem(CraftListener.applyUnlocks(target, getScythe()));
            p.sendMessage(Component.text(target.getName() + " has received the Scythe of the Grim Reaper.", NamedTextColor.BLACK));
            return true;
        }
        if (itemName.equalsIgnoreCase("timber_axe")) {
            target.getInventory().addItem(CraftListener.applyUnlocks(target, getTimberAxe()));
            p.sendMessage(Component.text(target.getName() + " has received the Timber Axe.", NamedTextColor.BLACK));
            return true;
        }
        if (itemName.equalsIgnoreCase("murasame")) {
            ItemStack murasame = CraftListener.applyUnlocks(target, getMurasame());
            target.getInventory().addItem(murasame != null ? murasame : getMurasame());
            p.sendMessage(Component.text(target.getName() + " has received Murasame.", NamedTextColor.BLACK));
            return true;
        }

        p.sendMessage(Component.text("Unknown item.", NamedTextColor.RED));
        return true;
    }

    private void removeByKey(Player target, NamespacedKey key) {
        ItemStack[] contents = target.getInventory().getContents();
        boolean changed = false;
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item != null && item.hasItemMeta()
                    && item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {
                contents[i] = null;
                changed = true;
            }
        }
        if (changed) {
            target.getInventory().setContents(contents);
        }
        ItemStack offHand = target.getInventory().getItemInOffHand();
        if (offHand != null && offHand.hasItemMeta()
                && offHand.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {
            target.getInventory().setItemInOffHand(null);
        }
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                               @NotNull String label, @NotNull String[] args) {

        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], List.of("give", "remove"), completions);
            return completions;
        }

        if (args.length == 2) {
            List<String> players = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                players.add(p.getName());
            }
            StringUtil.copyPartialMatches(args[1], players, completions);
            return completions;
        }

        if (args.length == 3) {
            StringUtil.copyPartialMatches(args[2], List.of("drill_3x3", "drill_5x5", "tnt_pickaxe",
                    "scythe", "timber_axe", "murasame"), completions);
            return completions;
        }

        return completions;
    }
}
