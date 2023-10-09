package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.channel.ChannelManager
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigData
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.japanize.JapanizePlayersManager
import io.github.bluesheep2804.selenechat.resource.ResourceData
import io.github.bluesheep2804.selenechat.resource.ResourceManager

object SeleneChat {
    lateinit var plugin: IPlugin
    val configManager: SeleneChatConfigManager
        get() = plugin.configManager
    val config: SeleneChatConfigData
        get() = plugin.config
    val resourceManager: ResourceManager
        get() = plugin.resourceManager
    val resource: ResourceData
        get() = plugin.resource
    val japanizePlayersManager: JapanizePlayersManager
        get() = plugin.japanizePlayersManager
    val japanizePlayers: MutableMap<String, Boolean>
        get() = plugin.japanizePlayers
    val channelManager: ChannelManager
        get() = plugin.channelManager

    fun setPluginInstance(plugin: IPlugin) {
        this.plugin = plugin
    }
}