package io.github.bluesheep2804.selenechat.player

import io.github.bluesheep2804.selenechat.SeleneChat.resource
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.CommandSender

class SeleneChatPlayerBungeeConsole(private val player: CommandSender) : SeleneChatPlayerConsole() {
    override fun sendMessage(msg: Component) {
        player.sendMessage(*BungeeComponentSerializer.get().serialize(msg))
    }

    override fun sendCommandResult(msg: Component) {
        sendMessage(resource.prefix.append(msg))
    }

    override fun hasPermission(permission: String): Boolean {
        return player.hasPermission(permission)
    }
}