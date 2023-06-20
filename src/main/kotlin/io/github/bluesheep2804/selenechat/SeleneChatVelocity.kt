package io.github.bluesheep2804.selenechat

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import io.github.bluesheep2804.selenechat.command.MessageCommand
import io.github.bluesheep2804.selenechat.command.MessageCommandVelocity
import io.github.bluesheep2804.selenechat.command.SeleneChatCommand
import io.github.bluesheep2804.selenechat.command.SeleneChatCommandVelocity
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.listener.ChatListenerVelocity
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerVelocity
import org.slf4j.Logger
import java.nio.file.Path
import java.util.*

@Plugin(id = "selenechat", name = "SeleneChat", version = "0.1.0-SNAPSHOT", description = "Chat plugin inspired by LunaChat", authors = ["BlueSheep2804"])
class SeleneChatVelocity @Inject constructor(val proxy: ProxyServer, val logger: Logger, @DataDirectory val dataDirectory: Path) : PluginInterface {
    override val configManager: SeleneChatConfigManager = SeleneChatConfigManager(dataDirectory.toFile())
    init {
        logger.info(configManager.checkVersion())

        SeleneChat.setPluginInstance(this)
    }

    @Subscribe
    fun onProxyInitialize(event: ProxyInitializeEvent) {
        proxy.eventManager.register(this, ChatListenerVelocity(this))
        proxy.channelRegistrar.register(MinecraftChannelIdentifier.create("selenechat", "message"))

        val commandManager = proxy.commandManager
        val seleneChatCommandMeta = commandManager.metaBuilder(SeleneChatCommand.COMMAND_NAME)
                .aliases(*SeleneChatCommand.COMMAND_ALIASES)
                .plugin(this)
                .build()
        val messageCommandMeta = commandManager.metaBuilder(MessageCommand.COMMAND_NAME)
                .aliases(*MessageCommand.COMMAND_ALIASES)
                .plugin(this)
                .build()
        commandManager.register(seleneChatCommandMeta, SeleneChatCommandVelocity(this))
        commandManager.register(messageCommandMeta, MessageCommandVelocity(this))

        logger.info("Loaded!")
    }

    override fun getAllPlayers(): List<SeleneChatPlayerVelocity> {
        return proxy.allPlayers.map { getPlayer(it.uniqueId)!! }
    }

    override fun getPlayer(name: String): SeleneChatPlayerVelocity? {
        val player = proxy.getPlayer(name)
        return if (player.isEmpty) null else SeleneChatPlayerVelocity(player.get())
    }

    override fun getPlayer(uuid: UUID): SeleneChatPlayerVelocity? {
        val player = proxy.getPlayer(uuid)
        return if (player.isEmpty) null else SeleneChatPlayerVelocity(player.get())
    }
}