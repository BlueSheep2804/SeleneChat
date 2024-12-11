package io.github.bluesheep2804.selenechat.listener

import io.github.bluesheep2804.selenechat.SeleneChat.channelManager
import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.SeleneChat.plugin
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.common.Platforms
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.text.Component

object ChatListener {
    fun chat(message: String, sender: SeleneChatPlayer): Component? {
        val channel = if (message.startsWith(config.globalMarker)) null else channelManager.getPlayerChannel(sender)
        return if (channel is ChannelData) {
            channel.sendMessage(ChatMessage.channelChat(message, sender, channel))
            null
        } else {
            val message = if (message.startsWith(config.globalMarker)) message.removePrefix(config.globalMarker) else message
            if (plugin.platform == Platforms.BUKKIT && !config.useSeleneChatFormat) {
                ChatMessage.message(message, sender)
            } else {
                plugin.sendMessage(ChatMessage.chat(message, sender))
                null
            }
        }
    }
}