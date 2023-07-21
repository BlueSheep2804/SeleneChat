package io.github.bluesheep2804.selenechat.resource

import io.github.bluesheep2804.selenechat.SeleneChat
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
        val japanizeSuccessChanged: String = "Your Japanize conversion is set to <value>."
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
}
