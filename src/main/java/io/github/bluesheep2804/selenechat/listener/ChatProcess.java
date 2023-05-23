package io.github.bluesheep2804.selenechat.listener;

import io.github.bluesheep2804.selenechat.japanize.Japanizer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;

public class ChatProcess {
    public static Component message(String msg) {
        TextComponent.Builder returnMessage = Component.text().content(msg);
        if (Japanizer.shouldConvert(msg)) {
            returnMessage.append(Component.text(
                    String.format(" (%s)", Japanizer.Japanizer(msg)),
                    NamedTextColor.GOLD
            ));
        }
        return returnMessage.build();
    }

    public static Component message(String msg, String username, HoverEvent playerHover) {
        TextComponent.Builder returnMessage = Component.text()
                .content("<")
                .append(Component.text(username)
                        .hoverEvent(playerHover)
                        .clickEvent(ClickEvent.suggestCommand(String.format("/tell %s ", username))))
                .append(Component.text(">"))
                .append(Component.text(": ", NamedTextColor.GREEN));

        return returnMessage.append(message(msg)).build();
    }
}
