package io.github.bluesheep2804.selenechat.player

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

open class SeleneChatPlayerSpigot(private val player: Player) : SeleneChatPlayer() {
    override val displayName: String
        get() = player.displayName

    override val uniqueId: UUID
        get() = player.uniqueId

    override val currentServerName: String
        get() = ""

    override fun sendMessage(msg: Component) {
        player.spigot().sendMessage(*BungeeComponentSerializer.get().serialize(msg))
    }

    companion object {
        fun getPlayer(source: CommandSender): SeleneChatPlayerSpigot {
            return SeleneChatPlayerSpigot(source as Player)
        }
    }
}