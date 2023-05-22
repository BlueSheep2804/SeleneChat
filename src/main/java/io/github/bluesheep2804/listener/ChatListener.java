package io.github.bluesheep2804.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import io.github.bluesheep2804.SeleneChat;
import io.github.bluesheep2804.japanize.Japanizer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

public class ChatListener {
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
                        .hoverEvent(player.asHoverEvent())
                        .clickEvent(ClickEvent.suggestCommand(String.format("/tell %s ", userName))))
                .append(Component.text(">"))
                .append(Component.text(": ", NamedTextColor.GREEN))
                .append(Component.text(message));
        if (Japanizer.shouldConvert(message)) {
            returnMessage.append(Component.text(
                    String.format(" (%s)", Japanizer.Japanizer(message)),
                    NamedTextColor.GOLD
            ));
        }
        SeleneChat.server.sendMessage(returnMessage.build());
    }
}
