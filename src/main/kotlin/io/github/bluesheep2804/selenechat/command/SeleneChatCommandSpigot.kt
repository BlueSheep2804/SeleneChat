package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.SeleneChatSpigot
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerSpigot
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerSpigotConsole
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class SeleneChatCommandSpigot(val plugin: SeleneChatSpigot) : CommandExecutor, TabExecutor {
    val command = SeleneChatCommand()
    override fun onCommand(commandSender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val sender = when (commandSender) {
            is Player -> SeleneChatPlayerSpigot(commandSender)
            is ConsoleCommandSender -> SeleneChatPlayerSpigotConsole(commandSender)
            else -> return false
        }
        return this.command.execute(sender, args)
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        val sender = when (commandSender) {
            is Player -> SeleneChatPlayerSpigot(commandSender)
            is ConsoleCommandSender -> SeleneChatPlayerSpigotConsole(commandSender)
            else -> return emptyList()
        }
        return this.command.onTabComplete(sender, args)
    }
}