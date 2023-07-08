package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

class SeleneChatCommand : ICommand {
    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(SeleneChat.resource.command.selenechatErrorSubCommandEmpty)
            return false
        }
        if (args[0] == "reload") {
            SeleneChat.configManager.reload()
            SeleneChat.resourceManager.reload()
            sender.sendMessage(SeleneChat.resource.command.selenechatSuccessReload)
        }
        return true
    }

    override fun suggest(sender: SeleneChatPlayer, args: Array<String>): List<String> {
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