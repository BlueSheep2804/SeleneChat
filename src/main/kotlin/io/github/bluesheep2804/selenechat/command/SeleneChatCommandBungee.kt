package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.SeleneChatBungee
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerBungee
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerBungeeConsole
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class SeleneChatCommandBungee(val plugin: SeleneChatBungee) : Command(SeleneChatCommand.COMMAND_NAME, SeleneChatCommand.PERMISSION, *SeleneChatCommand.COMMAND_ALIASES), TabExecutor {
    val command = SeleneChatCommand()
    override fun execute(commandSender: CommandSender, args: Array<String>) {
        val sender = when (commandSender) {
            is ProxiedPlayer -> SeleneChatPlayerBungee(commandSender)
            else -> SeleneChatPlayerBungeeConsole(commandSender)
        }
        command.execute(sender, args)
    }

    override fun onTabComplete(commandSender: CommandSender, args: Array<String>): List<String> {
        val sender = when (commandSender) {
            is ProxiedPlayer -> SeleneChatPlayerBungee(commandSender)
            else -> SeleneChatPlayerBungeeConsole(commandSender)
        }
        return command.onTabComplete(sender, args)
    }
}