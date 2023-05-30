package io.github.bluesheep2804.selenechat.listener

import com.google.common.io.ByteStreams
import io.github.bluesheep2804.selenechat.SeleneChatSpigot
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatListenerSpigot(val plugin: SeleneChatSpigot) : Listener {
    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val message = event.message
        val returnMessage = ChatProcess.message(message)
        event.message = LegacyComponentSerializer.legacySection().serialize(returnMessage)
        val out = ByteStreams.newDataOutput()
        out.writeUTF(event.player.displayName)
        out.writeUTF(LegacyComponentSerializer.legacySection().serialize(returnMessage))
        plugin.sendPluginMessage(out.toByteArray())
    }
}