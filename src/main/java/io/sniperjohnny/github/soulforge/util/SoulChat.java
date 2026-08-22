package io.sniperjohnny.github.soulforge.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;

public final class SoulChat {

    private SoulChat() {
    }

    public static Component shopLink(String hoverText) {
        return Component.text("Soul Shop", NamedTextColor.GOLD)
                .hoverEvent(HoverEvent.showText(Component.text(hoverText, NamedTextColor.AQUA)))
                .clickEvent(ClickEvent.runCommand("/soulsshop"));
    }
}
