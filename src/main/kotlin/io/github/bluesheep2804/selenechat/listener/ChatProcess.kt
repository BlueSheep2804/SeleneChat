package io.github.bluesheep2804.selenechat.listener

import io.github.bluesheep2804.selenechat.japanize.Japanizer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor

object ChatProcess {
    fun message(msg: String): Component {
        val japaneseConversion = Japanizer(msg)
        val returnMessage = Component.text().content(msg)
        if (japaneseConversion.shouldConvert()) {
            returnMessage.append(Component.text(
                    java.lang.String.format(" (%s)", japaneseConversion.japanize()),
                    NamedTextColor.GOLD
            ))
        }
        return returnMessage.build()
    }

    fun message(msg: String, username: String, playerHover: HoverEvent<*>): Component {
        val returnMessage = Component.text()
                .content("<")
                .append(Component.text(username)
                        .hoverEvent(playerHover)
                        .clickEvent(ClickEvent.suggestCommand(String.format("/tell %s ", username))))
                .append(Component.text(">"))
                .append(Component.text(": ", NamedTextColor.GREEN))
        return returnMessage.append(message(msg)).build()
    }
}