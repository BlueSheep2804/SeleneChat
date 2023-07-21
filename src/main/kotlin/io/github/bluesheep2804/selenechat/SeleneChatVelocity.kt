package io.github.bluesheep2804.selenechat

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import io.github.bluesheep2804.selenechat.command.*
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.japanize.JapanizePlayersManager
import io.github.bluesheep2804.selenechat.listener.ChatListenerVelocity
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerOffline
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerVelocity
import io.github.bluesheep2804.selenechat.resource.ResourceManager
import org.slf4j.Logger
import java.nio.file.Path
import java.util.*

@Plugin(id = "selenechat", name = "SeleneChat", version = "0.2.0", description = "Chat plugin inspired by LunaChat", authors = ["BlueSheep2804"])
class SeleneChatVelocity @Inject constructor(val proxy: ProxyServer, val logger: Logger, @DataDirectory val dataDirectory: Path) : IPlugin {
    override val configManager: SeleneChatConfigManager = SeleneChatConfigManager(dataDirectory.toFile())
    override val resourceManager: ResourceManager = ResourceManager(dataDirectory.toFile())
    override val japanizePlayersManager: JapanizePlayersManager = JapanizePlayersManager(dataDirectory.toFile())
    init {
        SeleneChat.setPluginInstance(this)

        logger.info(configManager.checkVersion())
    }

    @Subscribe
    fun onProxyShutdown(event: ProxyShutdownEvent) {
        japanizePlayersManager.save()
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
        val japanizeCommandMeta = commandManager.metaBuilder(JapanizeCommand.COMMAND_NAME)
                .aliases(*JapanizeCommand.COMMAND_ALIASES)
                .plugin(this)
                .build()
        commandManager.register(seleneChatCommandMeta, SeleneChatCommandVelocity())
        commandManager.register(messageCommandMeta, MessageCommandVelocity())
        commandManager.register(japanizeCommandMeta, JapanizeCommandVelocity())

        logger.info("Loaded!")
    }

    override fun getAllPlayers(): List<SeleneChatPlayerVelocity> {
        val players = mutableListOf<SeleneChatPlayerVelocity>()
        for (p in proxy.allPlayers) {
            when (val player = getPlayer(p.uniqueId)) {
                is SeleneChatPlayerVelocity -> players += player
                is SeleneChatPlayerOffline -> continue
            }
        }
        return players.toList()
    }

    override fun getPlayer(name: String): SeleneChatPlayer {
        val player = proxy.getPlayer(name)
        return if (player.isEmpty) SeleneChatPlayerOffline(displayName = name) else SeleneChatPlayerVelocity(player.get())
    }

    override fun getPlayer(uuid: UUID): SeleneChatPlayer {
        val player = proxy.getPlayer(uuid)
        return if (player.isEmpty) SeleneChatPlayerOffline(uuid) else SeleneChatPlayerVelocity(player.get())
    }
}