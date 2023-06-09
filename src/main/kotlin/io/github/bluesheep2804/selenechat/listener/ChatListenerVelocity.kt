package io.github.bluesheep2804.selenechat.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import com.velocitypowered.api.event.player.PlayerChatEvent
import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.SeleneChat.plugin
import io.github.bluesheep2804.selenechat.SeleneChatVelocity
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.message.PluginMessage
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerVelocity

class ChatListenerVelocity(plugin: SeleneChatVelocity) {
    private val proxy = plugin.proxy
    private val logger = plugin.logger
    @Subscribe
    fun onPlayerChatEvent(event: PlayerChatEvent) {
        val sender = SeleneChatPlayerVelocity(event.player)
        val message = event.message

        // デフォルトのイベントを無効化する
        // クライアントのバージョンが1.19.1以降だとキックされるがUnSignedVelocityで回避できる
        event.result = PlayerChatEvent.ChatResult.denied()
        proxy.sendMessage(ChatMessage.chat(message, sender))
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
        val sender = plugin.getPlayer(pm.player.uniqueId)
        val returnMessage = ChatMessage.chat(pm.message, sender)

        for (server in proxy.allServers) {
            if (server.serverInfo.name != sender.currentServerName) {
                server.sendMessage(returnMessage)
            }
        }
    }
}