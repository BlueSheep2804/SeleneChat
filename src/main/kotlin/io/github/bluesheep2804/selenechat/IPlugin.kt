package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.config.SeleneChatConfigData
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.japanize.JapanizePlayersManager
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import io.github.bluesheep2804.selenechat.resource.ResourceData
import io.github.bluesheep2804.selenechat.resource.ResourceManager
import java.util.*

interface IPlugin {
    val configManager: SeleneChatConfigManager
    val config: SeleneChatConfigData
        get() = configManager.config
    val resourceManager: ResourceManager
    val resource: ResourceData
        get() = resourceManager.resource
    val japanizePlayersManager: JapanizePlayersManager
    val japanizePlayers: MutableMap<String, Boolean>
        get() = japanizePlayersManager.japanizePlayers
    fun getAllPlayers(): List<SeleneChatPlayer>
    fun getPlayer(name: String): SeleneChatPlayer
    fun getPlayer(uuid: UUID): SeleneChatPlayer
}