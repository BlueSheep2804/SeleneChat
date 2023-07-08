package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.command.MessageCommand
import io.github.bluesheep2804.selenechat.command.MessageCommandSpigot
import io.github.bluesheep2804.selenechat.command.SeleneChatCommand
import io.github.bluesheep2804.selenechat.command.SeleneChatCommandSpigot
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.listener.ChatListenerSpigot
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerSpigot
import io.github.bluesheep2804.selenechat.resource.ResourceManager
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class SeleneChatSpigot : JavaPlugin(), IPlugin {
    private lateinit var adventure: BukkitAudiences
    override val configManager: SeleneChatConfigManager = SeleneChatConfigManager(dataFolder)
    override val resourceManager: ResourceManager = ResourceManager(dataFolder)
    init {
        SeleneChat.setPluginInstance(this)

        logger.info(configManager.checkVersion())
    }

    override fun onEnable() {
        adventure = BukkitAudiences.create(this)
        server.pluginManager.registerEvents(ChatListenerSpigot(this), this)
        server.messenger.registerOutgoingPluginChannel(this, "selenechat:message")

        this.getCommand(SeleneChatCommand.COMMAND_NAME)?.setExecutor(SeleneChatCommandSpigot(this))
        this.getCommand(MessageCommand.COMMAND_NAME)?.setExecutor(MessageCommandSpigot(this))

        logger.info("Loaded!")
    }

    override fun onDisable() {
        adventure.close()
    }

    fun sendPluginMessage(msg: ByteArray) {
        server.sendPluginMessage(this, "selenechat:message", msg)
    }

    override fun getAllPlayers(): List<SeleneChatPlayerSpigot> {
        return server.onlinePlayers.map { getPlayer(it.uniqueId)!! }
    }

    override fun getPlayer(name: String): SeleneChatPlayerSpigot? {
        val player = server.getPlayerExact(name)
        return if (player == null) null else SeleneChatPlayerSpigot(player)
    }

    override fun getPlayer(uuid: UUID): SeleneChatPlayerSpigot? {
        val player = server.getPlayer(uuid)
        return if (player == null) null else SeleneChatPlayerSpigot(player)
    }
}