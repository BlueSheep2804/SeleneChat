package io.github.bluesheep2804.selenechat

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import io.github.bluesheep2804.selenechat.listener.ChatListenerVelocity
import org.slf4j.Logger

@Plugin(id = "selenechat", name = "SeleneChat", version = "0.1.0-SNAPSHOT", description = "Chat plugin for Velocity inspired by Lunachat", authors = ["BlueSheep2804"])
class SeleneChatVelocity @Inject constructor(val server: ProxyServer, val logger: Logger) {
    init {
        logger.info("Loaded!")
    }

    @Subscribe
    fun onProxyInitialize(event: ProxyInitializeEvent) {
        server.eventManager.register(this, ChatListenerVelocity(this))
        server.channelRegistrar.register(MinecraftChannelIdentifier.create("selenechat", "message"))
    }
}