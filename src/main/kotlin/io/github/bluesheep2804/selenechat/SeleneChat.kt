package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.config.SeleneChatConfigData
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.japanize.JapanizePlayersManager
import io.github.bluesheep2804.selenechat.resource.ResourceData
import io.github.bluesheep2804.selenechat.resource.ResourceManager

object SeleneChat {
    lateinit var plugin: IPlugin
    lateinit var configManager: SeleneChatConfigManager
    val config: SeleneChatConfigData
        get() = configManager.config
    lateinit var resourceManager: ResourceManager
    val resource: ResourceData
        get() = resourceManager.resource
    val japanizePlayersManager: JapanizePlayersManager
        get() = plugin.japanizePlayersManager
    val japanizePlayers: MutableMap<String, Boolean>
        get() = plugin.japanizePlayers

    fun setPluginInstance(plugin: IPlugin) {
        this.plugin = plugin
        this.configManager = plugin.configManager
        this.resourceManager = plugin.resourceManager
    }
}