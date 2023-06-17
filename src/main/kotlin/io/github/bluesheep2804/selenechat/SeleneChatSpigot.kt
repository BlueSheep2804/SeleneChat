package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.command.MessageCommand
import io.github.bluesheep2804.selenechat.command.MessageCommandSpigot
import io.github.bluesheep2804.selenechat.listener.ChatListenerSpigot
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin

class SeleneChatSpigot : JavaPlugin(), SeleneChat {
    private lateinit var adventure: BukkitAudiences
    override val config: SeleneChatConfigData = SeleneChatConfig.load(dataFolder)
    init {
        if (config.configVersion < SeleneChatConfigData().configVersion) {
            logger.warning(SeleneChatConfig.TEXT_VERSION_OUTDATED)
        } else if (config.configVersion > SeleneChatConfigData().configVersion) {
            logger.warning(SeleneChatConfig.TEXT_VERSION_NEWER)
        }
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