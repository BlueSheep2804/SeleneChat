package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.listener.ChatListenerBungee
import net.kyori.adventure.platform.bungeecord.BungeeAudiences
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin

class SeleneChatBungee : Plugin() {
    private val proxy: ProxyServer = getProxy()
    private var adventure: BungeeAudiences? = null
    var config: SeleneChatConfigData? = null
    fun adventure(): BungeeAudiences {
        checkNotNull(adventure) { "Cannot retrieve audience provider while plugin is not enabled" }
        return adventure!!
    }

    override fun onEnable() {
        proxy.pluginManager.registerListener(this, ChatListenerBungee(this))
        adventure = BungeeAudiences.create(this)
        config = SeleneChatConfig.load(dataFolder)
        if (config!!.configVersion < SeleneChatConfigData().configVersion) {
            logger.warning(SeleneChatConfig.TEXT_VERSION_OUTDATED)
        } else if (config!!.configVersion > SeleneChatConfigData().configVersion) {
            logger.warning(SeleneChatConfig.TEXT_VERSION_NEWER)
        }

        proxy.registerChannel("selenechat:message")
        logger.info("Loaded")
    }

    override fun onDisable() {
        if (adventure != null) {
            adventure!!.close()
            adventure = null
        }
    }
}