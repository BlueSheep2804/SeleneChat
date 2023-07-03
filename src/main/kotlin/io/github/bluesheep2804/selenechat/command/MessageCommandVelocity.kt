package io.github.bluesheep2804.selenechat.command

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.command.SimpleCommand.Invocation
import com.velocitypowered.api.proxy.ConsoleCommandSource
import com.velocitypowered.api.proxy.Player
import io.github.bluesheep2804.selenechat.SeleneChatVelocity
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerVelocity
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerVelocityConsole

class MessageCommandVelocity(val plugin: SeleneChatVelocity) : SimpleCommand {
    val command = MessageCommand()
    override fun execute(invocation: Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()
        val sender = when (source) {
            is Player -> SeleneChatPlayerVelocity(source)
            is ConsoleCommandSource -> SeleneChatPlayerVelocityConsole(source)
            else -> return
        }
        command.execute(sender, args)
    }

    override fun hasPermission(invocation: Invocation): Boolean {
        return invocation.source().hasPermission(MessageCommand.PERMISSION)
    }

    override fun suggest(invocation: Invocation): List<String> {
        val sender = when (val source = invocation.source()) {
            is Player -> SeleneChatPlayerVelocity(source)
            is ConsoleCommandSource -> SeleneChatPlayerVelocityConsole(source)
            else -> return emptyList()
        }
        val args = if (invocation.arguments().isNotEmpty()) invocation.arguments() else invocation.arguments() + ""
        return command.suggest(sender, args)
    }
}