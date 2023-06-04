package io.github.bluesheep2804.selenechat.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import com.velocitypowered.api.event.player.PlayerChatEvent
import com.velocitypowered.api.proxy.ServerConnection
import io.github.bluesheep2804.selenechat.ConvertMode
import io.github.bluesheep2804.selenechat.SeleneChatVelocity
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.message.PluginMessage

class ChatListenerVelocity(plugin: SeleneChatVelocity) {
    private val server = plugin.server
    private val logger = plugin.logger
    private val config = plugin.config!!
    @Subscribe
    fun onPlayerChatEvent(event: PlayerChatEvent) {
        if (config.convertMode == ConvertMode.NONE) {
            return
        }
        val player = event.player
        val username = player.username
        val message = event.message
        val serverName = if (config.shouldShowServerName) event.player.currentServer.get().serverInfo.name else ""

        // デフォルトのイベントを無効化する
        // クライアントのバージョンが1.19.1以降だとキックされるがUnSignedVelocityで回避できる
        event.result = PlayerChatEvent.ChatResult.denied()
        server.sendMessage(ChatMessage.message(message, username, serverName, player.asHoverEvent(), config.convertMode))
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
        val serverName = (event.source as ServerConnection).serverInfo.name
        val playerHoverEvent = server.getPlayer(pm.playerUUID).get().asHoverEvent()
        val returnMessage = ChatMessage.message(pm.message, pm.playerDisplayName, serverName, playerHoverEvent, config.convertMode)

        for (server in server.allServers) {
            if (server.serverInfo.name != serverName) {
                server.sendMessage(returnMessage)
            }
        }
    }
}