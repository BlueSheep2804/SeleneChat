package io.github.bluesheep2804.selenechat.player

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.CommandSender
import java.util.*

class SeleneChatPlayerBungeeConsole(private val player: CommandSender) : SeleneChatPlayer() {
    override val displayName: String
        get() = player.name

    override val uniqueId: UUID
        get() = UUID.fromString("0-0-0-0-0")

    override val currentServerName: String
        get() = ""

    override fun asHoverEvent(): HoverEvent<Component> {
        return HoverEvent.showText(Component.text("CONSOLE"))
    }

    override fun sendMessage(msg: Component) {
        player.sendMessage(*BungeeComponentSerializer.get().serialize(msg))
    }

    override fun hasPermission(permission: String): Boolean {
        return player.hasPermission(permission)
    }
}