package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.SeleneChatBungee
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerBungee
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerBungeeConsole
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class MessageCommandBungee(val plugin: SeleneChatBungee) : Command(MessageCommand.COMMAND_NAME, MessageCommand.PERMISSION, *MessageCommand.COMMAND_ALIASES), TabExecutor {
    val command = MessageCommand(plugin.config)
    override fun execute(commandSender: CommandSender, args: Array<String>) {
        val sender = when (commandSender) {
            is ProxiedPlayer -> SeleneChatPlayerBungee(commandSender)
            else -> SeleneChatPlayerBungeeConsole(commandSender)
        }
        if (args.isEmpty()) {
            sender.sendMessage(Component.text("A player is required", NamedTextColor.RED))
            return
        }
        if (args.size == 1) {
            sender.sendMessage(Component.text("A message is required.", NamedTextColor.RED))
            return
        }
        val receiverRaw = plugin.proxy.getPlayer(args[0])
        if (receiverRaw == null) {
            sender.sendMessage(Component.text("The specified player ${args[0]} does not exist.", NamedTextColor.RED))
            return
        }
        val receiver = SeleneChatPlayerBungee(receiverRaw)
        command.execute(sender, receiver, args[1])
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): List<String> {
        val players = mutableListOf<String>()
        plugin.proxy.players.forEach {
            players += it.displayName
        }
        return command.onTabComplete(args, players)
    }
}