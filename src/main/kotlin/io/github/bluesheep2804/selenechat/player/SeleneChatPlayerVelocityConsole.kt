package io.github.bluesheep2804.selenechat.player

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.ConsoleCommandSource
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import java.util.*

class SeleneChatPlayerVelocityConsole(private val player: ConsoleCommandSource) : SeleneChatPlayer() {
    override val displayName: String
        get() = ""

    override val uniqueId: UUID
        get() = UUID.fromString("0-0-0-0-0")

    override val currentServerName: String
        get() = ""

    override fun sendMessage(msg: Component) {
        player.sendMessage(msg)
    }

    override fun asHoverEvent(): HoverEvent<Component> {
        return HoverEvent.showText(Component.text("CONSOLE"))
    }

    companion object {
        fun getPlayer(source: CommandSource): SeleneChatPlayerVelocityConsole {
            return SeleneChatPlayerVelocityConsole(source as ConsoleCommandSource)
        }
    }
}