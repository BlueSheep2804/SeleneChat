package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerSpigot
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerSpigotConsole
import org.bukkit.command.*
import org.bukkit.entity.Player

abstract class CommandSpigot : CommandExecutor, TabExecutor {
    abstract val command: ICommand
    override fun onCommand(commandSender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val sender = when (commandSender) {
            is Player -> SeleneChatPlayerSpigot(commandSender)
            is ConsoleCommandSender -> SeleneChatPlayerSpigotConsole(commandSender)
            else -> return false
        }
        return this.command.execute(sender, args)
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<String>): List<String> {
        val sender = when (commandSender) {
            is Player -> SeleneChatPlayerSpigot(commandSender)
            is ConsoleCommandSender -> SeleneChatPlayerSpigotConsole(commandSender)
            else -> return emptyList()
        }
        return this.command.suggest(sender, args)
    }
}