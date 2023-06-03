package io.github.bluesheep2804.selenechat

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import io.github.bluesheep2804.selenechat.listener.ChatListenerVelocity
import org.slf4j.Logger
import java.nio.file.Path

@Plugin(id = "selenechat", name = "SeleneChat", version = "0.1.0-SNAPSHOT", description = "Chat plugin for Velocity inspired by Lunachat", authors = ["BlueSheep2804"])
class SeleneChatVelocity @Inject constructor(val server: ProxyServer, val logger: Logger, @DataDirectory val dataDirectory: Path) {
    var config: SeleneChatConfigData? = null
    init {
        config = SeleneChatConfig.load(dataDirectory.toFile())
        if (config!!.configVersion < SeleneChatConfigData().configVersion) {
            logger.warn(SeleneChatConfig.TEXT_VERSION_OUTDATED)
        } else if (config!!.configVersion > SeleneChatConfigData().configVersion) {
            logger.warn(SeleneChatConfig.TEXT_VERSION_NEWER)
        }

        logger.info("Loaded!")
    }

    @Subscribe
    fun onProxyInitialize(event: ProxyInitializeEvent) {
        server.eventManager.register(this, ChatListenerVelocity(this))
        server.channelRegistrar.register(MinecraftChannelIdentifier.create("selenechat", "message"))
    }
}