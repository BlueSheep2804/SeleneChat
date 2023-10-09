package io.github.bluesheep2804.selenechat.player

import io.github.bluesheep2804.selenechat.SeleneChat.resource
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
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
        try {
            player.spigot().sendMessage(*BungeeComponentSerializer.get().serialize(msg))
        } catch (_: NoSuchMethodError) {
            player.sendMessage(LegacyComponentSerializer.legacySection().serialize(msg))
        }
    }

    override fun sendCommandResult(msg: Component) {
        sendMessage(resource.prefix.append(msg))
    }

    override fun hasPermission(permission: String): Boolean {
        return player.hasPermission(permission)
    }

    companion object {
        fun getPlayer(source: CommandSender): SeleneChatPlayerSpigot {
            return SeleneChatPlayerSpigot(source as Player)
        }
    }
}