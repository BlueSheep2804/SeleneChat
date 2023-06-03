package io.github.bluesheep2804.selenechat.listener

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
        val returnMessage = ChatMessage.message(message)

        event.message = LegacyComponentSerializer.legacySection().serialize(returnMessage)
        plugin.sendPluginMessage(PluginMessage(event.message, event.player.uniqueId, event.player.displayName).build())
    }
}