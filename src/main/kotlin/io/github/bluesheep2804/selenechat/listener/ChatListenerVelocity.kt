package io.github.bluesheep2804.selenechat.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import com.velocitypowered.api.event.player.PlayerChatEvent
import com.velocitypowered.api.proxy.ServerConnection
import io.github.bluesheep2804.selenechat.SeleneChatVelocity

class ChatListenerVelocity(plugin: SeleneChatVelocity) {
    private val server = plugin.server
    private val logger = plugin.logger
    @Subscribe
    fun onPlayerChatEvent(event: PlayerChatEvent) {
        val player = event.player
        val username = player.username
        val message = event.message

        // デフォルトのイベントを無効化する
        // クライアントのバージョンが1.19.1以降だとキックされるがUnSignedVelocityで回避できる
        event.result = PlayerChatEvent.ChatResult.denied()
        server.sendMessage(ChatProcess.message(message, username, player.asHoverEvent()))
    }

    @Subscribe
    fun onPluginMessageEvent(event: PluginMessageEvent) {
        logger.info(event.identifier.id)
        if (event.identifier.id != "selenechat:message") {
            return
        }
        val `in` = event.dataAsDataStream()
        val playerName = `in`.readUTF()
        val message = `in`.readUTF()
        val serverName = (event.source as ServerConnection).serverInfo.name
        val playerHoverEvent = server.getPlayer(playerName).get().asHoverEvent()
        for (server in server.allServers) {
            if (server.serverInfo.name != serverName) {
                server.sendMessage(ChatProcess.message(message, playerName, playerHoverEvent))
            }
        }
    }
}