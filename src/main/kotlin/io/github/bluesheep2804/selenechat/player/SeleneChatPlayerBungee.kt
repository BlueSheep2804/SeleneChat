package io.github.bluesheep2804.selenechat.player

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.Connection
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.*

class SeleneChatPlayerBungee(private val player: ProxiedPlayer) : SeleneChatPlayer() {
    override val displayName: String
        get() = player.displayName

    override val uniqueId: UUID
        get() = player.uniqueId

    override val currentServerName: String
        get() = player.server.info.name

    override fun sendMessage(msg: Component) {
        player.sendMessage(*BungeeComponentSerializer.get().serialize(msg))
    }

    companion object {
        fun getPlayer(source: CommandSender): SeleneChatPlayerBungee {
            return SeleneChatPlayerBungee(source as ProxiedPlayer)
        }

        fun getPlayer(connection: Connection): SeleneChatPlayerBungee {
            return SeleneChatPlayerBungee(connection as ProxiedPlayer)
        }
    }
}