package io.github.bluesheep2804.selenechat.listener

import com.google.common.io.ByteStreams
import io.github.bluesheep2804.selenechat.ConvertMode
import io.github.bluesheep2804.selenechat.SeleneChatBungee
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.message.PluginMessage
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.connection.Server
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class ChatListenerBungee(private val plugin: SeleneChatBungee) : Listener {
    private val proxy = plugin.proxy
    private val logger = proxy.logger
    private val config = plugin.config!!
    @EventHandler
    fun onChat(event: ChatEvent) {
        if (event.isCommand || event.isProxyCommand) {
            return
        }
        if (event.sender !is ProxiedPlayer) {
            return
        }
        if (config.convertMode == ConvertMode.NONE) {
            return
        }
        proxy.scheduler.runAsync(plugin) {
            val message = event.message
            val sender = event.sender as ProxiedPlayer
            val serverName = if (config.shouldShowServerName) sender.server.info.name else ""
            val returnMessage = ChatMessage.message(config.chatFormat, config.chatFormatMessage, message, sender.displayName, sender.uniqueId, serverName, config.convertMode)
            proxy.broadcast(*BungeeComponentSerializer.get().serialize(returnMessage))
        }
        event.isCancelled = true
    }

    @EventHandler
    fun onPluginMessage(event: PluginMessageEvent) {
        if (event.tag != "selenechat:message") {
            return
        }
        if (!config.shouldReceivePluginMessage) {
            return
        }
        val input = ByteStreams.newDataInput(event.data)
        val pm = PluginMessage.fromByteArrayDataInput(input)
        val serverName = (event.sender as Server).info.name
        val returnMessage = ChatMessage.message(config.chatFormat, config.chatFormatMessage, pm.message, pm.playerDisplayName, pm.playerUUID, serverName, config.convertMode)

        for (player in proxy.players) {
            if (player.server.info.name != serverName) {
                player.sendMessage(*BungeeComponentSerializer.get().serialize(returnMessage))
            }
        }
    }
}