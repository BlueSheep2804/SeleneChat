package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.command.*
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.japanize.JapanizePlayersManager
import io.github.bluesheep2804.selenechat.listener.ChatListenerSpigot
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerOffline
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerSpigot
import io.github.bluesheep2804.selenechat.resource.ResourceManager
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class SeleneChatSpigot : JavaPlugin(), IPlugin {
    private lateinit var adventure: BukkitAudiences
    override val configManager: SeleneChatConfigManager = SeleneChatConfigManager(dataFolder)
    override val resourceManager: ResourceManager = ResourceManager(dataFolder)
    override val japanizePlayersManager: JapanizePlayersManager = JapanizePlayersManager(dataFolder)
    init {
        SeleneChat.setPluginInstance(this)

        logger.info(configManager.checkVersion())
    }

    override fun onEnable() {
        adventure = BukkitAudiences.create(this)
        server.pluginManager.registerEvents(ChatListenerSpigot(this), this)
        server.messenger.registerOutgoingPluginChannel(this, "selenechat:message")

        this.getCommand(SeleneChatCommand.COMMAND_NAME)?.setExecutor(SeleneChatCommandSpigot())
        this.getCommand(MessageCommand.COMMAND_NAME)?.setExecutor(MessageCommandSpigot())
        this.getCommand(JapanizeCommand.COMMAND_NAME)?.setExecutor(JapanizeCommandSpigot())

        logger.info("Loaded!")
    }

    override fun onDisable() {
        adventure.close()
        japanizePlayersManager.save()
    }

    fun sendPluginMessage(msg: ByteArray) {
        server.sendPluginMessage(this, "selenechat:message", msg)
    }

    override fun getAllPlayers(): List<SeleneChatPlayerSpigot> {
        val players = mutableListOf<SeleneChatPlayerSpigot>()
        for (p in server.onlinePlayers) {
            when (val player = getPlayer(p.uniqueId)) {
                is SeleneChatPlayerSpigot -> players += player
                is SeleneChatPlayerOffline -> continue
            }
        }
        return players.toList()
    }

    override fun getPlayer(name: String): SeleneChatPlayer {
        val player = server.getPlayerExact(name)
        return if (player == null) SeleneChatPlayerOffline(displayName = name) else SeleneChatPlayerSpigot(player)
    }

    override fun getPlayer(uuid: UUID): SeleneChatPlayer {
        val player = server.getPlayer(uuid)
        return if (player == null) SeleneChatPlayerOffline(uuid) else SeleneChatPlayerSpigot(player)
    }
}