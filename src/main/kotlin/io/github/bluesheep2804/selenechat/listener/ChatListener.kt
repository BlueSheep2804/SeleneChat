package io.github.bluesheep2804.selenechat.listener

import io.github.bluesheep2804.selenechat.SeleneChat.channelManager
import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.SeleneChat.plugin
import io.github.bluesheep2804.selenechat.SeleneChatSpigot
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import io.github.bluesheep2804.selenechat.util.Platforms
import net.kyori.adventure.text.Component

object ChatListener {
    fun chat(message: String, sender: SeleneChatPlayer): Component? {
        val channel = channelManager.getPlayerChannel(sender)
        return if (channel is ChannelData) {
            channel.sendMessage(ChatMessage.chat(message, sender))
            null
        } else {
            if (plugin.platform == Platforms.BUKKIT && !config.useSeleneChatFormat) {
                ChatMessage.message(message, sender)
            } else {
                plugin.sendMessage(ChatMessage.chat(message, sender))
                null
            }
        }
    }
}