package io.github.bluesheep2804.selenechat.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.bluesheep2804.selenechat.SeleneChatSpigot;
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
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(event.getPlayer().getDisplayName());
        out.writeUTF(LegacyComponentSerializer.legacySection().serialize(returnMessage));
        SeleneChatSpigot.getInstance().sendPluginMessage(out.toByteArray());
    }
}
