package io.github.bluesheep2804.selenechat

import io.github.bluesheep2804.selenechat.listener.ChatListenerSpigot
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.plugin.java.JavaPlugin

class SeleneChatSpigot : JavaPlugin() {
    private var adventure: BukkitAudiences? = null
    private val server = getServer()
    fun adventure(): BukkitAudiences {
        checkNotNull(adventure) { "Tried to access Adventure when the plugin was disabled!" }
        return adventure!!
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(ChatListenerSpigot(this), this)
        adventure = BukkitAudiences.create(this)
        server.messenger.registerOutgoingPluginChannel(this, "selenechat:message")
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