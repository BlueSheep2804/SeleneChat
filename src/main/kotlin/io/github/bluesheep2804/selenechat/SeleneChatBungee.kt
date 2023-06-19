package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.command.MessageCommandBungee
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.listener.ChatListenerBungee
import net.kyori.adventure.platform.bungeecord.BungeeAudiences
import net.md_5.bungee.api.plugin.Plugin

class SeleneChatBungee : Plugin(), PluginInterface {
    private lateinit var adventure: BungeeAudiences
    override val configManager: SeleneChatConfigManager = SeleneChatConfigManager(dataFolder)
    init {
        logger.info(configManager.checkVersion())
    }

    override fun onEnable() {
        adventure = BungeeAudiences.create(this)
        proxy.pluginManager.registerListener(this, ChatListenerBungee(this))
        proxy.registerChannel("selenechat:message")

        proxy.pluginManager.registerCommand(this, MessageCommandBungee(this))

        logger.info("Loaded!")
    }

    override fun onDisable() {
        adventure.close()
    }
}