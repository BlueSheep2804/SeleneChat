package io.github.bluesheep2804.selenechat.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListenerSpigot implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Component returnMessage = ChatProcess.message(message);

        event.setMessage(LegacyComponentSerializer.legacySection().serialize(returnMessage));
    }
}
