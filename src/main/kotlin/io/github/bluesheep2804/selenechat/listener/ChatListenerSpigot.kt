package io.github.bluesheep2804.selenechat.listener

import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.SeleneChatSpigot
import io.github.bluesheep2804.selenechat.message.PluginMessage
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerSpigot
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatListenerSpigot(private val plugin: SeleneChatSpigot) : Listener {
    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val message = event.message
        val sender = SeleneChatPlayerSpigot(event.player)

        val result = ChatListener.chat(message, sender)
        if (result is Component)
            event.message = LegacyComponentSerializer.legacySection().serialize(result)
        else
            event.isCancelled = true

        if (config.shouldSendPluginMessage) {
            plugin.sendPluginMessage(PluginMessage(event.message, sender).build())
        }
    }
}