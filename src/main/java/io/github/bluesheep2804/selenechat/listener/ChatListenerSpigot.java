package io.github.bluesheep2804.selenechat.listener;

import io.github.bluesheep2804.selenechat.japanize.Japanizer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListenerSpigot implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        TextComponent.Builder returnMessage = Component.text().content(message);
        if (Japanizer.shouldConvert(message)) {
            returnMessage.append(Component.text(
                    String.format(" (%s)", Japanizer.Japanizer(message)),
                    NamedTextColor.GOLD
            ));
        }

        event.setMessage(LegacyComponentSerializer.legacySection().serialize(returnMessage.build()));
    }
}
