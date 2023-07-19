package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

class SeleneChatCommand : ICommand {
    override val COMMAND_NAME: String = SeleneChatCommand.COMMAND_NAME
    override val COMMAND_ALIASES: Array<String> = SeleneChatCommand.COMMAND_ALIASES
    override val PERMISSION: String = SeleneChatCommand.PERMISSION

    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(SeleneChat.resource.command.selenechatErrorSubCommandEmpty)
            return false
        }
        if (args[0] == "reload") {
            SeleneChat.configManager.reload()
            SeleneChat.resourceManager.reload()
            SeleneChat.japanizePlayersManager.reload()
            sender.sendMessage(SeleneChat.resource.command.selenechatSuccessReload)
        }
        return true
    }

    override fun suggest(sender: SeleneChatPlayer, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> listOf("reload").filter { it.startsWith(args[0]) || args[0] == "" }
            else -> emptyList()
        }
    }

    companion object {
        const val COMMAND_NAME = "selenechat"
        val COMMAND_ALIASES = arrayOf("sc")
        const val PERMISSION = "selenechat.command.selenechat"
    }
}