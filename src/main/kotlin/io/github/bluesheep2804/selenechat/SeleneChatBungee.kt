package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.channel.ChannelManager
import io.github.bluesheep2804.selenechat.command.ChannelCommandBungee
import io.github.bluesheep2804.selenechat.command.JapanizeCommandBungee
import io.github.bluesheep2804.selenechat.command.MessageCommandBungee
import io.github.bluesheep2804.selenechat.command.SeleneChatCommandBungee
import io.github.bluesheep2804.selenechat.common.Platforms
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.japanize.JapanizePlayersManager
import io.github.bluesheep2804.selenechat.listener.ChatListenerBungee
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerBungee
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerOffline
import io.github.bluesheep2804.selenechat.resource.ResourceManager
import net.kyori.adventure.platform.bungeecord.BungeeAudiences
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.plugin.Plugin
import java.util.*

class SeleneChatBungee : Plugin(), IPlugin {
    private lateinit var adventure: BungeeAudiences
    override val platform = Platforms.BUNGEECORD
    override val configManager: SeleneChatConfigManager = SeleneChatConfigManager(dataFolder)
    override val resourceManager: ResourceManager = ResourceManager(dataFolder)
    override val japanizePlayersManager: JapanizePlayersManager = JapanizePlayersManager(dataFolder)
    override val channelManager: ChannelManager = ChannelManager(dataFolder)
    init {
        SeleneChat.setPluginInstance(this)

        logger.info(configManager.checkVersion())
    }

    override fun onEnable() {
        adventure = BungeeAudiences.create(this)
        proxy.pluginManager.registerListener(this, ChatListenerBungee(this))
        proxy.registerChannel("selenechat:message")

        proxy.pluginManager.registerCommand(this, SeleneChatCommandBungee())
        proxy.pluginManager.registerCommand(this, MessageCommandBungee())
        proxy.pluginManager.registerCommand(this, JapanizeCommandBungee())
        proxy.pluginManager.registerCommand(this, ChannelCommandBungee())

        logger.info("Loaded!")
    }

    override fun onDisable() {
        adventure.close()
    }

    override fun getAllPlayers(): List<SeleneChatPlayerBungee> {
        val players = mutableListOf<SeleneChatPlayerBungee>()
        for (p in proxy.players) {
            when (val player = getPlayer(p.uniqueId)) {
                is SeleneChatPlayerBungee -> players += player
                is SeleneChatPlayerOffline -> continue
            }
        }
        return players.toList()
    }

    override fun getPlayer(name: String): SeleneChatPlayer {
        val player = proxy.getPlayer(name)
        return if (player == null) SeleneChatPlayerOffline(displayName = name) else SeleneChatPlayerBungee(player)
    }

    override fun getPlayer(uuid: UUID): SeleneChatPlayer {
        val player = proxy.getPlayer(uuid)
        return if (player == null) SeleneChatPlayerOffline(uuid) else SeleneChatPlayerBungee(player)
    }

    override fun sendMessage(component: Component) {
        proxy.broadcast(*BungeeComponentSerializer.get().serialize(component))
    }
}