package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.text.Component

class JapanizeCommand : ICommand {
    override val COMMAND_NAME: String = JapanizeCommand.COMMAND_NAME
    override val COMMAND_ALIASES: Array<String> = JapanizeCommand.COMMAND_ALIASES
    override val PERMISSION: String = JapanizeCommand.PERMISSION

    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        sender.toggleJapanize()
        sender.sendMessage(Component.text(sender.isEnabledJapanize))
        return true
    }

    override fun suggest(sender: SeleneChatPlayer, args: Array<String>): List<String> {
        return emptyList()
    }

    companion object {
        const val COMMAND_NAME = "japanize"
        val COMMAND_ALIASES = arrayOf("jp")
        const val PERMISSION = "selenechat.command.japanize"
    }
}