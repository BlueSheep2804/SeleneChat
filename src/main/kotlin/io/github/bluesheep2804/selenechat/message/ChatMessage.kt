package io.github.bluesheep2804.selenechat.message

import io.github.bluesheep2804.selenechat.ConvertMode
import io.github.bluesheep2804.selenechat.japanize.Japanizer
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import java.util.UUID

object ChatMessage {
    fun message(chatFormat: String, msg: String, convertMode: ConvertMode): Component {
        val mm = MiniMessage.miniMessage()
        val japaneseConversion = Japanizer(msg)
        val messageTagResolver = Placeholder.component(
                "message",
                Component.text(msg)
        )
        val jpTagResolver = TagResolver.resolver("jp")
        { args: ArgumentQueue, _: Context ->
            if (convertMode == ConvertMode.NONE || !japaneseConversion.shouldConvert()) {
                return@resolver Tag.selfClosingInserting(Component.text(""))
            }
            val prefix = args.popOr("prefix").value()
            val suffix = args.popOr("suffix").value()
            return@resolver Tag.selfClosingInserting(
                    Component.text(prefix)
                            .append(Component.text(japaneseConversion.japanize(convertMode)))
                            .append(Component.text(suffix))
            )
        }
        return mm.deserialize(chatFormat, messageTagResolver, jpTagResolver)
    }

    fun message(chatFormat: String, chatFormatMessage: String, msg: String, username: String, userUUID: UUID, serverName: String, convertMode: ConvertMode): Component {
        val mm = MiniMessage.miniMessage()
        val usernameTagResolver = Placeholder.component(
                "username",
                Component.text(username)
                        .hoverEvent(HoverEvent.showEntity(HoverEvent.ShowEntity.of(Key.key("player"), userUUID, Component.text(username))))
                        .clickEvent(ClickEvent.suggestCommand("/tell $username "))
        )
        val serverNameTagResolver = TagResolver.resolver("servername")
        { args: ArgumentQueue, _: Context ->
            if (serverName == "") {
                return@resolver Tag.selfClosingInserting(Component.text(""))
            }
            val prefix = args.popOr("prefix").value()
            val suffix = args.popOr("suffix").value()

            return@resolver Tag.selfClosingInserting(
                    Component.text(prefix)
                            .append(
                                    Component.text(serverName)
                                        .hoverEvent(HoverEvent.showText(Component.text("このサーバーに接続")))
                                        .clickEvent(ClickEvent.runCommand("/server $serverName")))
                            .append(Component.text(suffix))
            )
        }

        val messageTagResolver = Placeholder.component("message", message(chatFormatMessage, msg, convertMode))
        return mm.deserialize(chatFormat, usernameTagResolver, serverNameTagResolver, messageTagResolver)
    }
}