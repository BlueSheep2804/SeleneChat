package io.github.bluesheep2804.selenechat.listener

import com.google.common.io.ByteStreams
import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.SeleneChat.channelManager
import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.SeleneChatBungee
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.message.PluginMessage
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerBungee
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class ChatListenerBungee(private val plugin: SeleneChatBungee) : Listener {
    private val proxy = plugin.proxy
    private val logger = proxy.logger
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
            val sender = SeleneChatPlayerBungee.getPlayer(event.sender)

            val channel = channelManager.getPlayerChannel(sender)
            if (channel is ChannelData) {
                val returnMessage = ChatMessage.chat(message, sender)
                channel.sendMessage(returnMessage)
            } else {
                val returnMessage = ChatMessage.chat(message, sender)
                proxy.broadcast(*BungeeComponentSerializer.get().serialize(returnMessage))
            }
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
        val sender = SeleneChat.plugin.getPlayer(pm.player.uniqueId)
        val returnMessage = ChatMessage.chat(pm.message, sender)

        for (player in SeleneChat.plugin.getAllPlayers()) {
            if (player.currentServerName != sender.currentServerName) {
                player.sendMessage(returnMessage)
            }
        }
    }
}