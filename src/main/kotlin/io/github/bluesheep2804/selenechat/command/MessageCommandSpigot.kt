package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.SeleneChatSpigot
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerSpigot
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerSpigotConsole
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class MessageCommandSpigot(val plugin: SeleneChatSpigot) : CommandExecutor, TabExecutor {
    val command = MessageCommand()
    override fun onCommand(commandSender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val sender = when (commandSender) {
            is Player -> SeleneChatPlayerSpigot(commandSender)
            is ConsoleCommandSender -> SeleneChatPlayerSpigotConsole(commandSender)
            else -> return false
        }
        if (args.isEmpty()) {
            sender.sendMessage(Component.text("A player is required", NamedTextColor.RED))
            return false
        }
        if (args.size == 1) {
            sender.sendMessage(Component.text("A message is required.", NamedTextColor.RED))
            return false
        }
        val receiverRaw = plugin.server.getPlayerExact(args[0])
        receiverRaw ?: run {
            sender.sendMessage(Component.text("The specified player ${args[0]} does not exist.", NamedTextColor.RED))
            return false
        }
        val receiver = SeleneChatPlayerSpigot(receiverRaw)
        this.command.execute(sender, receiver, args[1])
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        val players = mutableListOf<String>()
        plugin.server.onlinePlayers.forEach {
            players += it.displayName
        }
        return this.command.onTabComplete(args, players)
    }
}