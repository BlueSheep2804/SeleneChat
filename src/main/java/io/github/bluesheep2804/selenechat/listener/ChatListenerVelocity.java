package io.github.bluesheep2804.selenechat.listener;

import com.google.common.io.ByteArrayDataInput;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import io.github.bluesheep2804.selenechat.SeleneChatVelocity;
import net.kyori.adventure.text.event.HoverEvent;

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

    @Subscribe
    public void onPluginMessageEvent(PluginMessageEvent event) {
        SeleneChatVelocity.logger.info(event.getIdentifier().getId());
        if (!event.getIdentifier().getId().equals("selenechat:message")) {
            return;
        }
        ByteArrayDataInput in = event.dataAsDataStream();
        String playerName = in.readUTF();
        String message = in.readUTF();
        String serverName = ((ServerConnection)event.getSource()).getServerInfo().getName();
        HoverEvent<HoverEvent.ShowEntity> playerHoverEvent = SeleneChatVelocity.server.getPlayer(playerName).get().asHoverEvent();

        for (RegisteredServer server : SeleneChatVelocity.server.getAllServers()) {
            if (!server.getServerInfo().getName().equals(serverName)) {
                server.sendMessage(ChatProcess.message(message, playerName, playerHoverEvent));
            }
        }
    }
}
