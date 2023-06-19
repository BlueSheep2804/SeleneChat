package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.config.SeleneChatConfigData
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager

interface PluginInterface {
    val configManager: SeleneChatConfigManager
    val config: SeleneChatConfigData
        get() = configManager.config
}