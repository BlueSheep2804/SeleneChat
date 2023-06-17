package io.github.bluesheep2804.selenechat.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import com.velocitypowered.api.event.player.PlayerChatEvent
import com.velocitypowered.api.proxy.ServerConnection
import io.github.bluesheep2804.selenechat.SeleneChatVelocity
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.message.PluginMessage
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerVelocity

class ChatListenerVelocity(plugin: SeleneChatVelocity) {
    private val proxy = plugin.proxy
    private val logger = plugin.logger
    private val config = plugin.config
    @Subscribe
    fun onPlayerChatEvent(event: PlayerChatEvent) {
        val sender = SeleneChatPlayerVelocity(event.player)
        val message = event.message

        // デフォルトのイベントを無効化する
        // クライアントのバージョンが1.19.1以降だとキックされるがUnSignedVelocityで回避できる
        event.result = PlayerChatEvent.ChatResult.denied()
        proxy.sendMessage(ChatMessage.message(config.chatFormat, config.chatFormatMessage, message, sender, config.convertMode))
    }

    @Subscribe
    fun onPluginMessageEvent(event: PluginMessageEvent) {
        if (event.identifier.id != "selenechat:message") {
            return
        }
        if (!config.shouldReceivePluginMessage) {
            return
        }
        val input = event.dataAsDataStream()
        val pm = PluginMessage.fromByteArrayDataInput(input)
        val sender = SeleneChatPlayerVelocity(proxy.getPlayer(pm.playerUUID).get())
        val serverName = (event.source as ServerConnection).serverInfo.name
        val returnMessage = ChatMessage.message(config.chatFormat, config.chatFormatMessage, pm.message, sender, config.convertMode)

        for (server in proxy.allServers) {
            if (server.serverInfo.name != serverName) {
                server.sendMessage(returnMessage)
            }
        }
    }
}