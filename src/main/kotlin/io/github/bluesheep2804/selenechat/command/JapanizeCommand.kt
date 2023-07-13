package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.text.Component

class JapanizeCommand : ICommand {
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