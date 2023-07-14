package io.github.bluesheep2804.selenechat.command

import com.velocitypowered.api.command.SimpleCommand
import com.velocitypowered.api.proxy.ConsoleCommandSource
import com.velocitypowered.api.proxy.Player
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerVelocity
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerVelocityConsole

abstract class CommandVelocity : SimpleCommand {
    abstract val command: ICommand
    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()
        val sender = when (source) {
            is Player -> SeleneChatPlayerVelocity(source)
            is ConsoleCommandSource -> SeleneChatPlayerVelocityConsole(source)
            else -> return
        }
        command.execute(sender, args)
    }

    override fun hasPermission(invocation: SimpleCommand.Invocation): Boolean {
        return invocation.source().hasPermission(command.PERMISSION)
    }

    override fun suggest(invocation: SimpleCommand.Invocation): List<String> {
        val sender = when (val source = invocation.source()) {
            is Player -> SeleneChatPlayerVelocity(source)
            is ConsoleCommandSource -> SeleneChatPlayerVelocityConsole(source)
            else -> return emptyList()
        }
        val args = if (invocation.arguments().isNotEmpty()) invocation.arguments() else invocation.arguments() + ""
        return command.suggest(sender, args)
    }
}