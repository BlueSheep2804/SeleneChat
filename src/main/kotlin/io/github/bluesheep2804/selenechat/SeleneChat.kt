package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.config.SeleneChatConfigData
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager

object SeleneChat {
    lateinit var plugin: PluginInterface
    lateinit var configManager: SeleneChatConfigManager
    val config: SeleneChatConfigData
        get() = configManager.config

    fun setPluginInstance(plugin: PluginInterface) {
        this.plugin = plugin
        this.configManager = plugin.configManager
    }
}