package io.github.bluesheep2804.selenechat.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import com.velocitypowered.api.event.player.PlayerChatEvent
import com.velocitypowered.api.proxy.ServerConnection
import io.github.bluesheep2804.selenechat.SeleneChatVelocity
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.message.PluginMessage

class ChatListenerVelocity(plugin: SeleneChatVelocity) {
    private val server = plugin.server
    private val logger = plugin.logger
    private val config = plugin.config!!
    @Subscribe
    fun onPlayerChatEvent(event: PlayerChatEvent) {
        val player = event.player
        val username = player.username
        val message = event.message

        // デフォルトのイベントを無効化する
        // クライアントのバージョンが1.19.1以降だとキックされるがUnSignedVelocityで回避できる
        event.result = PlayerChatEvent.ChatResult.denied()
        server.sendMessage(ChatMessage.message(message, username, player.asHoverEvent()))
    }

    @Subscribe
    fun onPluginMessageEvent(event: PluginMessageEvent) {
        if (event.identifier.id != "selenechat:message") {
            return
        }
        val input = event.dataAsDataStream()
        val pm = PluginMessage.fromByteArrayDataInput(input)
        val serverName = (event.source as ServerConnection).serverInfo.name
        val playerHoverEvent = server.getPlayer(pm.playerUUID).get().asHoverEvent()
        val returnMessage = ChatMessage.message(pm.message, pm.playerDisplayName, playerHoverEvent)

        for (server in server.allServers) {
            if (server.serverInfo.name != serverName) {
                server.sendMessage(returnMessage)
            }
        }
    }
}