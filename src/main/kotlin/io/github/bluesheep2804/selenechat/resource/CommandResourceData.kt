package io.github.bluesheep2804.selenechat.resource

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.util.ComponentSerializer
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder

@Serializable
data class CommandResourceData(
        @Serializable(with = ComponentSerializer::class)
        val messageErrorPlayer: Component = Component.text("A player is required.", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val messageErrorMessage: Component = Component.text("A message is required.", NamedTextColor.RED),
        val messageErrorPlayerNotFound: String = "<red>The specified player <player> does not exist.",
        @Serializable(with = ComponentSerializer::class)
        val selenechatErrorSubCommandEmpty: Component = Component.text("Argument is missing.", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val selenechatErrorSubCommandPermission: Component = Component.text("You do not have permission to execute that command.", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val selenechatSuccessReload: Component = Component.text("Reloaded!"),
        @Serializable(with = ComponentSerializer::class)
        val japanizeErrorUnexpectedArgs: Component = Component.text("The argument must be one of [on, off].", NamedTextColor.RED),
        val japanizeSuccessCurrentValue: String = "Your Japanize conversion is currently set to <value>.",
        val japanizeSuccessChanged: String = "Your Japanize conversion is set to <value>.",
        @Serializable(with = ComponentSerializer::class)
        val channelErrorSubCommandEmpty: Component = Component.text("Argument is missing.", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val channelErrorSubCommandNotFound: Component = Component.text("That subcommand does not exist.", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val channelErrorCreateEmpty: Component = Component.text("The channel name is not specified.", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val channelErrorCreateExists: Component = Component.text("The channel with the specified name already exists.", NamedTextColor.RED),
        val channelSuccessCreate: String = "Channel created: <channel>",
        @Serializable(with = ComponentSerializer::class)
        val channelErrorDeleteEmpty: Component = Component.text("The channel name is not specified.", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val channelErrorDeleteNotExists: Component = Component.text("The specified channel does not exist.", NamedTextColor.RED),
        val channelSuccessDelete: String = "Channel deleted: <channel>",
        @Serializable(with = ComponentSerializer::class)
        val channelSuccessList: Component = Component.text("Channel list"),
        @Serializable(with = ComponentSerializer::class)
        val channelErrorJoinEmpty: Component = Component.text("The channel name is not specified.", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val channelErrorJoinNotFound: Component = Component.text("The specified channel cannot be found.", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val channelErrorJoinAlreadyJoins: Component = Component.text("You are already in this channel.", NamedTextColor.RED),
        val channelSuccessJoin: String = "You joined channel <channel>.",
        val channelSuccessJoinSwitch: String = "Your chat channel has been changed to <channel>.",
        @Serializable(with = ComponentSerializer::class)
        val channelSuccessJoinSwitchGlobal: Component = Component.text("Your chat channel has been changed to global channel."),
        @Serializable(with = ComponentSerializer::class)
        val channelErrorLeaveEmpty: Component = Component.text("The channel name is not specified.", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val channelErrorLeaveNotFound: Component = Component.text("The specified channel cannot be found.", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val channelErrorLeaveNotInChannel: Component = Component.text("You are not in the specified channel.", NamedTextColor.RED),
        val channelSuccessLeave: String = "You left channel <channel>.",
) {
    fun messageErrorPlayerNotFoundComponent(playerName: String): Component {
        val mm = MiniMessage.miniMessage()
        val playerTagResolver = Placeholder.component("player", Component.text(playerName))
        return mm.deserialize(messageErrorPlayerNotFound, playerTagResolver)
    }
    fun japanizeSuccessCurrentValueComponent(value: Boolean): Component {
        val mm = MiniMessage.miniMessage()
        val valueComponent = if (value) {
            SeleneChat.resource.enabled
        } else {
            SeleneChat.resource.disabled
        }
        val valueTagResolver = Placeholder.component("value", valueComponent)
        return mm.deserialize(japanizeSuccessCurrentValue, valueTagResolver)
    }
    fun japanizeSuccessChangedComponent(value: Boolean): Component {
        val mm = MiniMessage.miniMessage()
        val valueComponent = if (value) {
            SeleneChat.resource.enabled
        } else {
            SeleneChat.resource.disabled
        }
        val valueTagResolver = Placeholder.component("value", valueComponent)
        return mm.deserialize(japanizeSuccessChanged, valueTagResolver)
    }

    fun channelSuccessCreate(channel: ChannelData): Component {
        val channelNameTagResolver = Placeholder.component("channel", channel.displayName)
        return MiniMessage.miniMessage().deserialize(channelSuccessCreate, channelNameTagResolver)
    }

    fun channelSuccessDelete(channel: ChannelData): Component {
        val channelNameTagResolver = Placeholder.component("channel", channel.displayName)
        return MiniMessage.miniMessage().deserialize(channelSuccessDelete, channelNameTagResolver)
    }

    fun channelSuccessJoin(channel: ChannelData): Component {
        val channelNameTagResolver = Placeholder.component("channel", channel.displayName)
        return MiniMessage.miniMessage().deserialize(channelSuccessJoin, channelNameTagResolver)
    }

    fun channelSuccessJoinSwitch(channel: ChannelData): Component {
        val channelNameTagResolver = Placeholder.component("channel", channel.displayName)
        return MiniMessage.miniMessage().deserialize(channelSuccessJoinSwitch, channelNameTagResolver)
    }

    fun channelSuccessLeave(channel: ChannelData): Component {
        val channelNameTagResolver = Placeholder.component("channel", channel.displayName)
        return MiniMessage.miniMessage().deserialize(channelSuccessLeave, channelNameTagResolver)
    }
}
