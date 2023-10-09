package io.github.bluesheep2804.selenechat.player

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import io.github.bluesheep2804.selenechat.SeleneChat.resource
import net.kyori.adventure.text.Component
import java.util.*

class SeleneChatPlayerVelocity(private val player: Player) : SeleneChatPlayer() {
    override val displayName: String
        get() = player.username

    override val uniqueId: UUID
        get() = player.uniqueId

    override val currentServerName: String
        get() = player.currentServer.get().serverInfo.name

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
        fun getPlayer(source: CommandSource): SeleneChatPlayerVelocity {
            return SeleneChatPlayerVelocity(source as Player)
        }
    }
}