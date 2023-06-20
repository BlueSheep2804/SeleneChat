package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.config.SeleneChatConfigData
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import java.util.UUID

interface PluginInterface {
    val configManager: SeleneChatConfigManager
    val config: SeleneChatConfigData
        get() = configManager.config
    fun getAllPlayers(): List<SeleneChatPlayer>
    fun getPlayer(name: String): SeleneChatPlayer?
    fun getPlayer(uuid: UUID): SeleneChatPlayer?
}