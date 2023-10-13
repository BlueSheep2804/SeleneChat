package io.github.bluesheep2804.selenechat.command

import arrow.core.Either
import io.github.bluesheep2804.selenechat.SeleneChat.channelManager
import io.github.bluesheep2804.selenechat.SeleneChat.resource
import io.github.bluesheep2804.selenechat.channel.ChannelManager.ChannelCreateError
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class ChannelCommand : ICommand {
    override val COMMAND_NAME: String = ChannelCommand.COMMAND_NAME
    override val COMMAND_ALIASES: Array<String> = ChannelCommand.COMMAND_ALIASES
    override val PERMISSION: String = ChannelCommand.PERMISSION

    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendCommandResult(resource.command.channelErrorSubCommandEmpty)
        } else {
            when (args[0]) {
                "create" -> {
                    if (args.size < 2) {
                        sender.sendCommandResult(resource.command.channelErrorCreateEmpty)
                        return false
                    }
                    when (val result = channelManager.create(args[1])) {
                        is Either.Left -> {
                            sender.sendCommandResult(when (val it = result.value) {
                                is ChannelCreateError.AlreadyExists -> resource.command.channelErrorCreateExists
                            })
                            return false
                        }
                        is Either.Right -> sender.sendCommandResult(resource.command.channelSuccessCreate(result.value.name))
                    }
                }
                "list" -> {
                    val returnMessage = Component.text().append(resource.command.channelSuccessList)
                    channelManager.allChannels.forEach {
                        returnMessage.appendNewline()
                                .append(Component.text("- ${it.displayName}"))
                                .append(Component.text("(${it.name})", NamedTextColor.GRAY))
                    }
                    sender.sendCommandResult(returnMessage.build())
                }
                else -> {
                    sender.sendCommandResult(resource.command.channelErrorSubCommandNotFound)
                }
            }
        }
        return true
    }

    override fun suggest(sender: SeleneChatPlayer, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> listOf("list", "create").filter { it.startsWith(args[0]) || args[0] == "" }
            else -> emptyList()
        }
    }

    companion object {
        const val COMMAND_NAME = "channel"
        val COMMAND_ALIASES = arrayOf("ch")
        const val PERMISSION = "selenechat.command.channel"
    }
}