package io.github.bluesheep2804.selenechat.listener

import io.github.bluesheep2804.selenechat.ConvertMode
import io.github.bluesheep2804.selenechat.SeleneChatSpigot
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.message.PluginMessage
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatListenerSpigot(private val plugin: SeleneChatSpigot) : Listener {
    private val config = plugin.config!!
    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val message = event.message
        val player = event.player

        if (config.convertMode != ConvertMode.NONE) {
            if (config.useSeleneChatFormat) {
                val returnMessage = ChatMessage.message(config.chatFormat, config.chatFormatMessage, message, player.displayName, player.uniqueId, "", config.convertMode)
                plugin.server.spigot().broadcast(*BungeeComponentSerializer.get().serialize(returnMessage))
                event.isCancelled = true
            } else {
                val returnMessage = ChatMessage.message(config.chatFormatMessage, message, config.convertMode)
                event.message = LegacyComponentSerializer.legacySection().serialize(returnMessage)
            }
        }
        if (config.shouldSendPluginMessage) {
            plugin.sendPluginMessage(PluginMessage(event.message, player.uniqueId, player.displayName).build())
        }
    }
}