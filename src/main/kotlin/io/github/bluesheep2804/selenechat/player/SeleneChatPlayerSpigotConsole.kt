package io.github.bluesheep2804.selenechat.player

import io.github.bluesheep2804.selenechat.SeleneChat.resource
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import java.util.*

class SeleneChatPlayerSpigotConsole(private val player: ConsoleCommandSender) : SeleneChatPlayer() {
    override val displayName: String
        get() = player.name

    override val uniqueId: UUID
        get() = UUID.fromString("0-0-0-0-0")

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

    override fun asHoverEvent(): HoverEvent<Component> {
        return HoverEvent.showText(Component.text("CONSOLE"))
    }

    override fun hasPermission(permission: String): Boolean {
        return player.hasPermission(permission)
    }

    companion object {
        fun getPlayer(source: CommandSender): SeleneChatPlayerSpigotConsole {
            return SeleneChatPlayerSpigotConsole(source as ConsoleCommandSender)
        }
    }
}