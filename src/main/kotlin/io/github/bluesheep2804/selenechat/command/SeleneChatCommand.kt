package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class SeleneChatCommand {
    fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(Component.text("empty", NamedTextColor.RED))
            return false
        }
        if (args[0] == "reload") {
            SeleneChat.configManager.reload()
            sender.sendMessage(Component.text("reloaded!"))
        }
        return true
    }

    fun onTabComplete(sender: SeleneChatPlayer, args: Array<String>): List<String> {
        if (args.size == 1) {
            return listOf("reload")
        } else {
            return emptyList()
        }
    }

    companion object {
        const val COMMAND_NAME = "selenechat"
        val COMMAND_ALIASES = arrayOf("sc")
        const val PERMISSION = "selenechat.command.selenechat"
    }
}