package io.github.bluesheep2804.selenechat.listener

import io.github.bluesheep2804.selenechat.ConvertMode
import io.github.bluesheep2804.selenechat.SeleneChatSpigot
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.message.PluginMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatListenerSpigot(private val plugin: SeleneChatSpigot) : Listener {
    private val config = plugin.config!!
    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val message = event.message

        if (config.convertMode != ConvertMode.NONE) {
            val returnMessage = ChatMessage.message(message, config.convertMode)
            event.message = LegacyComponentSerializer.legacySection().serialize(returnMessage)
        }
        if (config.shouldSendPluginMessage) {
            plugin.sendPluginMessage(PluginMessage(event.message, event.player.uniqueId, event.player.displayName).build())
        }
    }
}