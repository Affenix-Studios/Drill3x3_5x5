package io.sniperjohnny.github.soulforge.guis;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import io.sniperjohnny.github.soulforge.SoulForge;
import io.sniperjohnny.github.soulforge.souls.SoulManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public final class RecipeSearchDialog {

    private RecipeSearchDialog() {
    }

    public static void open(Player player) {
        open(player, "");
    }

    public static void open(Player player, String initial) {
        player.closeInventory();
        Dialog dialog = Dialog.create(builder -> builder.empty()
                .base(DialogBase.builder(Component.text("Search recipes"))
                        .body(List.of(DialogBody.plainMessage(Component.text(
                                "Type a recipe name and press Search (3x3, 5x5, tnt, scythe, timber, murasame)"))))
                        .inputs(List.of(DialogInput.text("search", Component.text("Recipe"))
                                .initial(initial)
                                .maxLength(32)
                                .build()))
                        .build())
                .type(DialogType.confirmation(
                        ActionButton.builder(Component.text("Search", TextColor.color(0xAEFFC1)))
                                .action(DialogAction.customClick((view, audience) -> {
                                    if (view == null || !(audience instanceof Player p)) {
                                        return;
                                    }
                                    String search = view.getText("search");
                                    Bukkit.getScheduler().runTask(SoulForge.getInstance(), () -> openRecipe(p, search));
                                }, ClickCallback.Options.builder().uses(1).build()))
                                .build(),
                        ActionButton.builder(Component.text("Cancel", TextColor.color(0xFFA0B1)))
                                .action(null)
                                .build()
                )));
        player.showDialog(dialog);
    }

    public static void openRecipe(Player player, String search) {
        if (search == null) {
            search = "";
        }
        String query = search.toLowerCase();

        if (query.contains("3x3")) {
            new Recipe3x3Drill().open(player);
            return;
        }
        if (query.contains("5x5")) {
            new Recipe5x5Drill().open(player);
            return;
        }
        if (query.contains("tnt")) {
            new RecipeTntPickaxeDrill().open(player);
            return;
        }
        if (query.contains("scythe") || query.contains("grim") || query.contains("reaper")) {
            new RecipeScytheDrill().open(player);
            return;
        }
        if (query.contains("timber") || query.contains("axe") || query.contains("tree")) {
            new RecipeTimberAxeDrill().open(player);
            return;
        }
        if (query.contains("murasame") || query.contains("sword") || query.contains("curse") || query.contains("katana")) {
            if (SoulManager.hasUnlock(player, SoulManager.MURASAME_CRAFT)) {
                new RecipeMurasameDrill().open(player);
            } else {
                player.sendMessage(Component.text(
                        "Murasame's recipe is locked. Unlock it in /soulsshop!", NamedTextColor.RED));
            }
            return;
        }

        int score3x3 = score(query, "drill", "hammer", "3", "three", "drei");
        int score5x5 = score(query, "drill", "hammer", "5", "five", "funf");
        int scoreTnt = score(query, "pickaxe", "explosive", "boom", "bomb", "dynamite");
        int scoreScythe = score(query, "scythe", "grim", "reaper", "soul");
        int scoreTimber = score(query, "timber", "axe", "tree", "holz", "wood");
        int scoreMurasame = score(query, "murasame", "sword", "curse", "katana");

        int best = Math.max(score3x3, Math.max(score5x5, Math.max(scoreTnt, Math.max(scoreScythe, Math.max(scoreTimber, scoreMurasame)))));
        if (best == 0) {
            player.sendMessage(Component.text("No recipe found for \"" + search
                    + "\". Try: 3x3, 5x5, tnt, scythe, timber, murasame", NamedTextColor.RED));
            return;
        }

        int matches = 0;
        if (score3x3 == best) matches++;
        if (score5x5 == best) matches++;
        if (scoreTnt == best) matches++;
        if (scoreScythe == best) matches++;
        if (scoreTimber == best) matches++;
        if (scoreMurasame == best) matches++;

        if (matches > 1) {
            player.sendMessage(Component.text("Multiple recipes match \"" + search + "\". Showing all recipes.",
                    NamedTextColor.YELLOW));
            new AllRecipesMenu().open(player);
        } else if (score3x3 == best) {
            new Recipe3x3Drill().open(player);
        } else if (score5x5 == best) {
            new Recipe5x5Drill().open(player);
        } else if (scoreTnt == best) {
            new RecipeTntPickaxeDrill().open(player);
        } else if (scoreScythe == best) {
            new RecipeScytheDrill().open(player);
        } else if (scoreTimber == best) {
            new RecipeTimberAxeDrill().open(player);
        } else if (SoulManager.hasUnlock(player, SoulManager.MURASAME_CRAFT)) {
            new RecipeMurasameDrill().open(player);
        } else {
            player.sendMessage(Component.text(
                    "Murasame's recipe is locked. Unlock it in /soulsshop!", NamedTextColor.RED));
        }
    }

    private static int score(String query, String... keywords) {
        int score = 0;
        for (String keyword : keywords) {
            if (query.contains(keyword)) {
                score++;
            }
        }
        return score;
    }
}
