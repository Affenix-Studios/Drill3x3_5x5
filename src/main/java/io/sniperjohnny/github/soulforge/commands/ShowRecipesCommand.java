package io.sniperjohnny.github.soulforge.commands;

import io.sniperjohnny.github.soulforge.guis.AllRecipesMenu;
import io.sniperjohnny.github.soulforge.guis.RecipeSearchDialog;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShowRecipesCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(sender instanceof final Player p)) {
            sender.sendMessage("Der Befehl ist nur für spieler");
            return true;
        }
        if (strings.length > 0) {
            RecipeSearchDialog.open(p, String.join(" ", strings));
            return true;
        }
        new AllRecipesMenu().open(p);
        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], List.of("3x3", "5x5", "tnt", "scythe", "timber", "murasame"), completions);
        }
        return completions;
    }
}
