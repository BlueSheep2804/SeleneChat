package io.github.bluesheep2804.selenechat.message

import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.config.ConvertMode
import io.github.bluesheep2804.selenechat.japanize.Japanizer
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

object ChatMessage {
    fun message(msg: String): Component {
        val mm = MiniMessage.miniMessage()
        val japaneseConversion = Japanizer(msg)
        val messageTagResolver = Placeholder.component(
                "message",
                Component.text(msg)
        )
        val jpTagResolver = TagResolver.resolver("jp")
        { args: ArgumentQueue, _: Context ->
            if (config.convertMode == ConvertMode.NONE || !japaneseConversion.shouldConvert()) {
                return@resolver Tag.selfClosingInserting(Component.text(""))
            }
            val prefix = args.popOr("prefix").value()
            val suffix = args.popOr("suffix").value()
            return@resolver Tag.selfClosingInserting(
                    Component.text(prefix)
                            .append(Component.text(japaneseConversion.japanize()))
                            .append(Component.text(suffix))
            )
        }
        return mm.deserialize(config.chatFormatMessage, messageTagResolver, jpTagResolver)
    }

    fun message(msg: String, sender: SeleneChatPlayer): Component {
        val mm = MiniMessage.miniMessage()
        val usernameTagResolver = Placeholder.component(
                "username",
                Component.text(sender.displayName)
                        .hoverEvent(sender.asHoverEvent())
                        .clickEvent(ClickEvent.suggestCommand("/tell ${sender.displayName} "))
        )
        val serverNameTagResolver = TagResolver.resolver("servername")
        { args: ArgumentQueue, _: Context ->
            if (sender.currentServerName == "") {
                return@resolver Tag.selfClosingInserting(Component.text(""))
            }
            val prefix = args.popOr("prefix").value()
            val suffix = args.popOr("suffix").value()

            return@resolver Tag.selfClosingInserting(
                    Component.text(prefix)
                            .append(
                                    Component.text(sender.currentServerName)
                                        .hoverEvent(HoverEvent.showText(Component.text("このサーバーに接続")))
                                        .clickEvent(ClickEvent.runCommand("/server ${sender.currentServerName}")))
                            .append(Component.text(suffix))
            )
        }

        val messageTagResolver = Placeholder.component("message", message(msg))
        return mm.deserialize(config.chatFormat, usernameTagResolver, serverNameTagResolver, messageTagResolver)
    }
}