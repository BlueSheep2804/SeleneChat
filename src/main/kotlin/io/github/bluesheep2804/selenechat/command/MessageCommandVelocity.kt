package io.github.bluesheep2804.selenechat.command

import com.velocitypowered.api.command.SimpleCommand.Invocation
import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.ConsoleCommandSource
import com.velocitypowered.api.proxy.Player
import io.github.bluesheep2804.selenechat.SeleneChatVelocity
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerVelocity
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerVelocityConsole
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class MessageCommandVelocity(val plugin: SeleneChatVelocity) : SimpleCommand {
    val command = MessageCommand(plugin.config)
    override fun execute(invocation: Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()
        val sender = when (source) {
            is Player -> SeleneChatPlayerVelocity(source)
            is ConsoleCommandSource -> SeleneChatPlayerVelocityConsole(source)
            else -> return
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
        if (receiverRaw.isEmpty) {
            sender.sendMessage(Component.text("The specified player ${args[0]} does not exist.", NamedTextColor.RED))
            return
        }
        val receiver = SeleneChatPlayerVelocity(receiverRaw.get())
        command.execute(sender, receiver, args[1])
    }

    override fun hasPermission(invocation: Invocation): Boolean {
        return invocation.source().hasPermission(MessageCommand.PERMISSION)
    }

    override fun suggest(invocation: Invocation): List<String> {
        val args = arrayOf<String>(invocation.alias(), *invocation.arguments())
        val players = mutableListOf<String>()
        plugin.proxy.allPlayers.forEach {
            players += it.username
        }
        return command.onTabComplete(args, players)
    }
}