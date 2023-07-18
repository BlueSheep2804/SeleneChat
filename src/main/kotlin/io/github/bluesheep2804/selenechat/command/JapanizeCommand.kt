package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.SeleneChat.resource
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

class JapanizeCommand : ICommand {
    override val COMMAND_NAME: String = JapanizeCommand.COMMAND_NAME
    override val COMMAND_ALIASES: Array<String> = JapanizeCommand.COMMAND_ALIASES
    override val PERMISSION: String = JapanizeCommand.PERMISSION

    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(resource.command.japanizeSuccessCurrentValueComponent(sender.isEnabledJapanize))
        } else {
            if (args[0] == "on") {
                sender.enableJapanize()
            } else if (args[0] == "off") {
                sender.disableJapanize()
            } else {
                sender.sendMessage(resource.command.japanizeErrorUnexpectedArgs)
                return false
            }
            sender.sendMessage(resource.command.japanizeSuccessChangedComponent(sender.isEnabledJapanize))
        }
        return true
    }

    override fun suggest(sender: SeleneChatPlayer, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> listOf("on", "off")
            else -> emptyList()
        }
    }

    companion object {
        const val COMMAND_NAME = "japanize"
        val COMMAND_ALIASES = arrayOf("jp")
        const val PERMISSION = "selenechat.command.japanize"
    }
}