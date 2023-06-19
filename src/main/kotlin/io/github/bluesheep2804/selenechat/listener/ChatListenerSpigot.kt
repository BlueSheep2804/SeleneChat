package io.github.bluesheep2804.selenechat.listener

import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.config.ConvertMode
import io.github.bluesheep2804.selenechat.SeleneChatSpigot
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.message.PluginMessage
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerSpigot
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatListenerSpigot(private val plugin: SeleneChatSpigot) : Listener {
    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val message = event.message
        val sender = SeleneChatPlayerSpigot(event.player)

        if (config.convertMode != ConvertMode.NONE) {
            if (config.useSeleneChatFormat) {
                val returnMessage = ChatMessage.message(message, sender)
                plugin.server.spigot().broadcast(*BungeeComponentSerializer.get().serialize(returnMessage))
                event.isCancelled = true
            } else {
                val returnMessage = ChatMessage.message(message)
                event.message = LegacyComponentSerializer.legacySection().serialize(returnMessage)
            }
        }
        if (config.shouldSendPluginMessage) {
            plugin.sendPluginMessage(PluginMessage(event.message, sender.uniqueId, sender.displayName).build())
        }
    }
}