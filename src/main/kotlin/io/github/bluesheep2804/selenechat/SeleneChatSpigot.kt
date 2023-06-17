package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.command.MessageCommand
import io.github.bluesheep2804.selenechat.command.MessageCommandSpigot
import io.github.bluesheep2804.selenechat.listener.ChatListenerSpigot
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin

class SeleneChatSpigot : JavaPlugin() {
    private var adventure: BukkitAudiences? = null
    private val server = getServer()
    var config: SeleneChatConfigData? = null
    fun adventure(): BukkitAudiences {
        checkNotNull(adventure) { "Tried to access Adventure when the plugin was disabled!" }
        return adventure!!
    }

    override fun onEnable() {
        config = SeleneChatConfig.load(dataFolder)
        if (config!!.configVersion < SeleneChatConfigData().configVersion) {
            logger.warning(SeleneChatConfig.TEXT_VERSION_OUTDATED)
        } else if (config!!.configVersion > SeleneChatConfigData().configVersion) {
            logger.warning(SeleneChatConfig.TEXT_VERSION_NEWER)
        }

        server.pluginManager.registerEvents(ChatListenerSpigot(this), this)
        adventure = BukkitAudiences.create(this)
        server.messenger.registerOutgoingPluginChannel(this, "selenechat:message")

        this.getCommand(MessageCommand.COMMAND_NAME)?.setExecutor(MessageCommandSpigot(this))

        logger.info("Loaded!")
    }

    override fun onDisable() {
        if (adventure != null) {
            adventure!!.close()
            adventure = null
        }
    }

    fun sendPluginMessage(msg: ByteArray?) {
        server.sendPluginMessage(this, "selenechat:message", msg)
    }

}