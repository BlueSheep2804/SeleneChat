package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.command.MessageCommandBungee
import io.github.bluesheep2804.selenechat.command.SeleneChatCommandBungee
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.listener.ChatListenerBungee
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerBungee
import net.kyori.adventure.platform.bungeecord.BungeeAudiences
import net.md_5.bungee.api.plugin.Plugin
import java.util.*

class SeleneChatBungee : Plugin(), PluginInterface {
    private lateinit var adventure: BungeeAudiences
    override val configManager: SeleneChatConfigManager = SeleneChatConfigManager(dataFolder)
    init {
        logger.info(configManager.checkVersion())

        SeleneChat.setPluginInstance(this)
    }

    override fun onEnable() {
        adventure = BungeeAudiences.create(this)
        proxy.pluginManager.registerListener(this, ChatListenerBungee(this))
        proxy.registerChannel("selenechat:message")

        proxy.pluginManager.registerCommand(this, SeleneChatCommandBungee(this))
        proxy.pluginManager.registerCommand(this, MessageCommandBungee(this))

        logger.info("Loaded!")
    }

    override fun onDisable() {
        adventure.close()
    }

    override fun getAllPlayers(): List<SeleneChatPlayerBungee> {
        return proxy.players.map { getPlayer(it.uniqueId)!! }
    }

    override fun getPlayer(name: String): SeleneChatPlayerBungee? {
        val player = proxy.getPlayer(name)
        return if (player == null) null else SeleneChatPlayerBungee(player)
    }

    override fun getPlayer(uuid: UUID): SeleneChatPlayerBungee? {
        val player = proxy.getPlayer(uuid)
        return if (player == null) null else SeleneChatPlayerBungee(player)
    }
}