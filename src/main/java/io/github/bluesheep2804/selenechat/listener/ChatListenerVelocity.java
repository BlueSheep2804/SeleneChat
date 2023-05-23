package io.github.bluesheep2804.selenechat.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import io.github.bluesheep2804.selenechat.SeleneChatVelocity;

public class ChatListenerVelocity {
    @Subscribe
    public void onPlayerChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String username = player.getUsername();
        String message = event.getMessage();

        // デフォルトのイベントを無効化する
        // クライアントのバージョンが1.19.1以降だとキックされるがUnSignedVelocityで回避できる
        event.setResult(PlayerChatEvent.ChatResult.denied());

        SeleneChatVelocity.server.sendMessage(ChatProcess.message(message, username, player.asHoverEvent()));
    }
}
