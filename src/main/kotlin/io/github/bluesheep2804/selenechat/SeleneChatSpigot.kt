package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.command.MessageCommand
import io.github.bluesheep2804.selenechat.command.MessageCommandSpigot
import io.github.bluesheep2804.selenechat.config.SeleneChatConfigManager
import io.github.bluesheep2804.selenechat.listener.ChatListenerSpigot
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin

class SeleneChatSpigot : JavaPlugin(), PluginInterface {
    private lateinit var adventure: BukkitAudiences
    override val configManager: SeleneChatConfigManager = SeleneChatConfigManager(dataFolder)
    init {
        logger.info(configManager.checkVersion())
    }

    override fun onEnable() {
        adventure = BukkitAudiences.create(this)
        server.pluginManager.registerEvents(ChatListenerSpigot(this), this)
        server.messenger.registerOutgoingPluginChannel(this, "selenechat:message")

        this.getCommand(MessageCommand.COMMAND_NAME)?.setExecutor(MessageCommandSpigot(this))

        logger.info("Loaded!")
    }

    override fun onDisable() {
        adventure.close()
    }

    fun sendPluginMessage(msg: ByteArray) {
        server.sendPluginMessage(this, "selenechat:message", msg)
    }
}