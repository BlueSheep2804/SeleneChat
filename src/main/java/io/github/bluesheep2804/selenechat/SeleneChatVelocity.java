package io.github.bluesheep2804.selenechat;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import io.github.bluesheep2804.selenechat.listener.ChatListenerVelocity;
import org.slf4j.Logger;

@Plugin(
        id = "selenechat",
        name = "SeleneChat",
        version = "0.1.0-SNAPSHOT",
        description = "Chat plugin for Velocity inspired by Lunachat",
        authors = {"BlueSheep2804"}
)
public class SeleneChatVelocity {

    public static ProxyServer server;
    public static Logger logger;

    @Inject
    public SeleneChatVelocity(ProxyServer server, Logger logger) {
        SeleneChatVelocity.server = server;
        SeleneChatVelocity.logger = logger;

        logger.info("Loaded!");
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        server.getEventManager().register(this, new ChatListenerVelocity());
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("selenechat", "message"));
    }
}