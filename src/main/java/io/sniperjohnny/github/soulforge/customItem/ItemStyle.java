package io.sniperjohnny.github.soulforge.customItem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public final class ItemStyle {

    private ItemStyle() {
    }

    public static Component name(String text, NamedTextColor color) {
        return Component.text(text, color).decoration(TextDecoration.BOLD, true);
    }

    public static Component blade(String text, NamedTextColor color) {
        return Component.text(text, color)
                .decoration(TextDecoration.BOLD, true)
                .decoration(TextDecoration.ITALIC, true);
    }

    public static String roman(int level) {
        return switch (level) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            default -> String.valueOf(level);
        };
    }
}
