package io.github.bluesheep2804.selenechat.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class ChatListenerBungee implements Listener {
    private final Plugin plugin;

    public ChatListenerBungee(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onChat(ChatEvent event) {
        if (event.isCommand() || event.isProxyCommand()) {
            return;
        }
        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }
        this.plugin.getProxy().getScheduler().runAsync(this.plugin, () -> {
            String message = event.getMessage();
            ProxiedPlayer sender = (ProxiedPlayer)event.getSender();
            Component returnMessage = ChatProcess.message(message, sender.getDisplayName(), HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text(sender.getDisplayName())));

            this.plugin.getProxy().broadcast(BungeeComponentSerializer.get().serialize(returnMessage));
        });
        event.setCancelled(true);
    }
}
