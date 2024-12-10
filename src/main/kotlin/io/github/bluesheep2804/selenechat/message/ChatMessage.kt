package io.github.bluesheep2804.selenechat.message

import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.SeleneChat.resource
import io.github.bluesheep2804.selenechat.channel.ChannelData
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
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import java.text.SimpleDateFormat
import java.util.*

object ChatMessage {
    private val dateFormat: SimpleDateFormat
        get() = SimpleDateFormat(config.dateFormat)
    private val timeFormat: SimpleDateFormat
        get() = SimpleDateFormat(config.timeFormat)
    fun message(msg: String, sender: SeleneChatPlayer): Component {
        val mm = MiniMessage.miniMessage()
        val serializedMessage = if (config.useColorCode || sender.hasPermission("selenechat.colorcode")) {
            LegacyComponentSerializer.legacyAmpersand().deserialize(msg.removePrefix("$"))
        } else {
            Component.text(msg.removePrefix("$"))
        }
        val japaneseConversion = Japanizer(PlainTextComponentSerializer.plainText().serialize(serializedMessage))
        val messageTagResolver = Placeholder.component(
                "message",
                serializedMessage
        )
        val jpTagResolver = TagResolver.resolver("jp")
        { args: ArgumentQueue, _: Context ->
            if (!sender.isEnabledJapanize || config.convertMode == ConvertMode.NONE || !japaneseConversion.shouldConvert() || msg[0] == '$') {
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
        return mm.deserialize(config.messageFormat, messageTagResolver, jpTagResolver)
    }

    fun chat(msg: String, sender: SeleneChatPlayer): Component {
        val mm = MiniMessage.miniMessage()
        val senderTagResolver = Placeholder.component(
                "sender",
                playerClickableComponent(sender)
        )
        val serverTagResolver = TagResolver.resolver("server")
        { args: ArgumentQueue, _: Context ->
            return@resolver serverTag(sender, args)
        }
        val messageTagResolver = Placeholder.component("message", message(msg, sender))
        val dateTagResolver = Placeholder.component("date", Component.text(dateFormat.format(Date())))
        val timeTagResolver = Placeholder.component("time", Component.text(timeFormat.format(Date())))

        return mm.deserialize(
                config.chatFormat,
                senderTagResolver,
                serverTagResolver,
                messageTagResolver,
                dateTagResolver,
                timeTagResolver
        )
    }

    fun channelChat(msg: String, sender: SeleneChatPlayer, channel: ChannelData): Component {
        val mm = MiniMessage.miniMessage()
        val senderTagResolver = Placeholder.component(
                "sender",
                playerClickableComponent(sender)
        )
        val channelTagResolver = Placeholder.component(
                "channel",
                channelClickableComponent(channel)
        )
        val serverTagResolver = TagResolver.resolver("server")
        { args: ArgumentQueue, _: Context ->
            return@resolver serverTag(sender, args)
        }
        val messageTagResolver = Placeholder.component("message", message(msg, sender))
        val dateTagResolver = Placeholder.component("date", Component.text(dateFormat.format(Date())))
        val timeTagResolver = Placeholder.component("time", Component.text(timeFormat.format(Date())))

        return mm.deserialize(
                channel.format.ifEmpty { config.channelChatFormat },
                senderTagResolver,
                channelTagResolver,
                serverTagResolver,
                messageTagResolver,
                dateTagResolver,
                timeTagResolver
        )
    }

    fun privateMessage(msg: String, sender: SeleneChatPlayer, receiver: SeleneChatPlayer): Component {
        val mm = MiniMessage.miniMessage()
        val senderTagResolver = Placeholder.component(
                "sender",
                playerClickableComponent(sender)
        )
        val senderServerTagResolver = TagResolver.resolver("senderserver")
        { args: ArgumentQueue, _: Context ->
            return@resolver serverTag(sender, args)
        }
        val receiverTagResolver = Placeholder.component(
                "receiver",
                playerClickableComponent(receiver)
        )
        val receiverServerTagResolver = TagResolver.resolver("receiverserver")
        { args: ArgumentQueue, _: Context ->
            return@resolver serverTag(receiver, args)
        }
        val messageTagResolver = Placeholder.component(
                "message",
                message(msg, sender)
        )
        val dateTagResolver = Placeholder.component("date", Component.text(dateFormat.format(Date())))
        val timeTagResolver = Placeholder.component("time", Component.text(timeFormat.format(Date())))

        return mm.deserialize(
                config.privateMessageFormat,
                senderTagResolver,
                senderServerTagResolver,
                receiverTagResolver,
                receiverServerTagResolver,
                messageTagResolver,
                dateTagResolver,
                timeTagResolver
        )
    }

    private fun playerClickableComponent(player: SeleneChatPlayer): Component {
        return Component.text(player.displayName)
                .hoverEvent(player.asHoverEvent())
                .clickEvent(ClickEvent.suggestCommand("/tell ${player.displayName} "))
    }

    private fun channelClickableComponent(channel: ChannelData): Component {
        return channel.displayName
                .hoverEvent(resource.hoverTextChannel)
                .clickEvent(ClickEvent.runCommand("/channel join ${channel.name}"))
    }

    private fun serverClickableComponent(serverName: String): Component {
        return Component.text(serverName)
                .hoverEvent(HoverEvent.showText(resource.hoverTextServer))
                .clickEvent(ClickEvent.runCommand("/server $serverName"))
    }

    private fun serverTag(player: SeleneChatPlayer, args: ArgumentQueue): Tag {
        if (player.currentServerName == "") {
            return Tag.selfClosingInserting(Component.text(""))
        }
        val prefix = args.popOr("prefix").value()
        val suffix = args.popOr("suffix").value()

        return Tag.selfClosingInserting(
                Component.text(prefix)
                        .append(serverClickableComponent(player.currentServerName))
                        .append(Component.text(suffix))
        )
    }
}