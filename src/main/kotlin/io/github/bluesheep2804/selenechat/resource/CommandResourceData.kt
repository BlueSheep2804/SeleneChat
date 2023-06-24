package io.github.bluesheep2804.selenechat.resource

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
        val selenechatErrorSubCommandEmpty: Component = Component.text("empty", NamedTextColor.RED),  // TODO: 後で書き直す
        @Serializable(with = ComponentSerializer::class)
        val selenechatSuccessReload: Component = Component.text("reloaded!")  // TODO: 書き直す
) {
    fun messageErrorPlayerNotFoundComponent(playerName: String): Component {
        val mm = MiniMessage.miniMessage()
        val playerTagResolver = Placeholder.component("player", Component.text(playerName))
        return mm.deserialize(messageErrorPlayerNotFound, playerTagResolver)
    }
}
