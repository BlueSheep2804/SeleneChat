package io.github.bluesheep2804.selenechat.message

import io.github.bluesheep2804.selenechat.ConvertMode
import io.github.bluesheep2804.selenechat.japanize.Japanizer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor

object ChatMessage {
    fun message(msg: String, convertMode: ConvertMode): Component {
        val japaneseConversion = Japanizer(msg)
        val returnMessage = Component.text().content(msg)
        if (convertMode != ConvertMode.NONE && japaneseConversion.shouldConvert()) {
            returnMessage.append(Component.text(
                    " (${japaneseConversion.japanize(convertMode)})",
                    NamedTextColor.GOLD
            ))
        }
        return returnMessage.build()
    }

    fun message(msg: String, username: String, serverName: String, playerHover: HoverEvent<*>, convertMode: ConvertMode): Component {
        val returnMessage = Component.text()
                .content("<")
                .append(Component.text(username)
                        .hoverEvent(playerHover)
                        .clickEvent(ClickEvent.suggestCommand("/tell $username ")))
        if (serverName != "") {
            returnMessage.append(Component.text(" ("))
                    .append(Component.text(serverName, NamedTextColor.GREEN)
                            .hoverEvent(HoverEvent.showText(Component.text("このサーバーに接続")))
                            .clickEvent(ClickEvent.runCommand("/server $serverName")))
                    .append(Component.text(")"))
        }
        returnMessage.append(Component.text(">"))
                .append(Component.text(": ", NamedTextColor.GREEN))
        return returnMessage.append(message(msg, convertMode)).build()
    }
}