package io.github.bluesheep2804;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.bluesheep2804.japanize.Japanizer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.slf4j.Logger;

@Plugin(id = "selenechat", name = "SeleneChat", version = "0.1.0-SNAPSHOT",
        description = "Chat plugin for Velocity inspired by Lunachat", authors = {"BlueSheep2804"})
public class SeleneChat {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public SeleneChat(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("Hello there! I made my first plugin with Velocity.");
    }

    @Subscribe
    public void onPlayerChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String userName = player.getUsername();
        String message = event.getMessage();

        // デフォルトのイベントを無効化する
        // クライアントのバージョンが1.19.1以降だとキックされるがUnSignedVelocityで回避できる
        event.setResult(PlayerChatEvent.ChatResult.denied());

        TextComponent.Builder returnMessage = Component.text()
                .content("<")
                .append(Component.text(userName)
                        .hoverEvent(Component.text(userName + "にメッセージを送る"))
                        .clickEvent(ClickEvent.suggestCommand("/tell " + userName + " ")))
                .append(Component.text(">"))
                .append(Component.text(": ", NamedTextColor.GREEN))
                .append(Component.text(message));
        if (Japanizer.shouldConvert(message)) {
            returnMessage.append(Component.text(" (" + Japanizer.Japanizer(message) + ")", NamedTextColor.GOLD));
        }
        server.sendMessage(returnMessage.build());
    }
}