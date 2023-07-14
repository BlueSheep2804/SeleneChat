package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

interface ICommand {
    val COMMAND_NAME: String
    val COMMAND_ALIASES: Array<String>
    val PERMISSION: String
    fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean
    fun suggest(sender: SeleneChatPlayer, args: Array<String>): List<String>
}