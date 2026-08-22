package io.sniperjohnny.github.soulforge.souls;

import io.sniperjohnny.github.soulforge.SoulForge;
import io.sniperjohnny.github.soulforge.customItem.ItemStyle;
import io.sniperjohnny.github.soulforge.customItem.Murasame;
import io.sniperjohnny.github.soulforge.customItem.Scythe;
import io.sniperjohnny.github.soulforge.customItem.TimberAxe;
import io.sniperjohnny.github.soulforge.customItem.TntPickaxe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class ShopUpgrades {

    public static final List<ShopUpgrade> ALL = List.of(
            new ShopUpgrade(SoulManager.SCYTHE_EFFICIENCY, "Scythe: Efficiency", "soulsshop.scythe.efficiency", 15, 5, Scythe.getScythe()),
            new ShopUpgrade(SoulManager.SCYTHE_LOOTING, "Scythe: Looting", "soulsshop.scythe.looting", 20, 3, Scythe.getScythe()),
            new ShopUpgrade(SoulManager.SCYTHE_SOUL_HARVEST, "Scythe: Soul Harvest", "soulsshop.scythe.soul-harvest", 50, 5, Scythe.getScythe()),
            new ShopUpgrade(SoulManager.SCYTHE_SHARPNESS, "Scythe: Sharpness", "soulsshop.scythe.sharpness", 20, 5, Scythe.getScythe()),
            new ShopUpgrade(SoulManager.TNT_FORTUNE, "TNT Pickaxe: Fortune", "soulsshop.tntpickaxe.fortune", 25, 3, TntPickaxe.getTntPickaxe()),
            new ShopUpgrade(SoulManager.TNT_SILKTOUCH, "TNT Pickaxe: Silk Touch", "soulsshop.tntpickaxe.silktouch", 25, 1, TntPickaxe.getTntPickaxe()),
            new ShopUpgrade(SoulManager.TNT_UNBREAKABLE, "TNT Pickaxe: Unbreakable", "soulsshop.tntpickaxe.unbreakable", 40, 1, new ItemStack(Material.ANVIL)),
            new ShopUpgrade(SoulManager.TIMBER_COOLDOWN, "Timber Axe: Remove Cooldown", "soulsshop.timberaxe.remove-cooldown", 30, 1, new ItemStack(Material.CLOCK)),
            new ShopUpgrade(SoulManager.TIMBER_EFFICIENCY, "Timber Axe: Efficiency", "soulsshop.timberaxe.efficiency", 15, 5, TimberAxe.getTimberAxe()),
            new ShopUpgrade(SoulManager.TIMBER_FORTUNE, "Timber Axe: Fortune", "soulsshop.timberaxe.fortune", 20, 3, TimberAxe.getTimberAxe()),
            new ShopUpgrade(SoulManager.TIMBER_SILKTOUCH, "Timber Axe: Silk Touch", "soulsshop.timberaxe.silktouch", 20, 1, TimberAxe.getTimberAxe()),
            new ShopUpgrade(SoulManager.MURASAME_CRAFT, "Unlock Murasame Crafting", "soulsshop.murasame.crafting-unlock", 100, 1, Murasame.getMurasame())
    );

    private ShopUpgrades() {
    }

    public static int cost(ShopUpgrade upgrade) {
        return SoulForge.getShopCost(upgrade.costPath(), upgrade.defaultCost());
    }

    public static List<Component> costLines(OfflinePlayer owner) {
        List<Component> lines = new ArrayList<>();
        for (ShopUpgrade upgrade : ALL) {
            int level = SoulManager.getUnlockLevel(owner, upgrade.key());
            String text;
            if (level >= upgrade.maxLevel()) {
                text = upgrade.name() + " — MAX";
            } else if (upgrade.maxLevel() == 1) {
                text = upgrade.name() + " — " + cost(upgrade) + " souls";
            } else {
                int nextCost = cost(upgrade) * (level + 1);
                text = upgrade.name() + " — " + nextCost + " souls (level "
                        + ItemStyle.roman(level + 1) + ")";
            }
            lines.add(Component.text(text, NamedTextColor.GRAY));
        }
        return lines;
    }
}
