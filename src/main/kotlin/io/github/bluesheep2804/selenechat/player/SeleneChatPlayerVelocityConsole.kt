package io.github.bluesheep2804.selenechat.player

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.ConsoleCommandSource
import io.github.bluesheep2804.selenechat.SeleneChat.resource
import net.kyori.adventure.text.Component

class SeleneChatPlayerVelocityConsole(private val player: ConsoleCommandSource) : SeleneChatPlayerConsole() {
    override fun sendMessage(msg: Component) {
        player.sendMessage(msg)
    }

    override fun sendCommandResult(msg: Component) {
        sendMessage(resource.prefix.append(msg))
    }

    override fun hasPermission(permission: String): Boolean {
        return player.hasPermission(permission)
    }

    companion object {
        fun getPlayer(source: CommandSource): SeleneChatPlayerVelocityConsole {
            return SeleneChatPlayerVelocityConsole(source as ConsoleCommandSource)
        }
    }
}