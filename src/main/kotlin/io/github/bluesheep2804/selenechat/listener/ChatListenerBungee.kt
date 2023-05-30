package io.github.bluesheep2804.selenechat.listener

import com.google.common.io.ByteStreams
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.connection.Server
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.event.EventHandler
import java.util.logging.Logger

class ChatListenerBungee(private val plugin: Plugin) : Listener {
    private val proxy: ProxyServer = plugin.proxy
    private val logger: Logger = proxy.logger
    @EventHandler
    fun onChat(event: ChatEvent) {
        if (event.isCommand || event.isProxyCommand) {
            return
        }
        if (event.sender !is ProxiedPlayer) {
            return
        }
        proxy.scheduler.runAsync(plugin) {
            val message = event.message
            val sender = event.sender as ProxiedPlayer
            val returnMessage = ChatProcess.message(message, sender.displayName, HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text(sender.displayName)))
            proxy.broadcast(*BungeeComponentSerializer.get().serialize(returnMessage))
        }
        event.isCancelled = true
    }

    @EventHandler
    fun onPluginMessage(event: PluginMessageEvent) {
        if (event.tag != "selenechat:message") {
            return
        }
        val `in` = ByteStreams.newDataInput(event.data)
        val playerName = `in`.readUTF()
        val message = `in`.readUTF()
        val serverName = (event.sender as Server).info.name
        logger.info("Received message: $playerName> $message")
        val returnMessage = ChatProcess.message(message, playerName, HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text(playerName)))
        for (player in proxy.players) {
            if (player.server.info.name != serverName) {
                player.sendMessage(*BungeeComponentSerializer.get().serialize(returnMessage))
            }
        }
    }
}