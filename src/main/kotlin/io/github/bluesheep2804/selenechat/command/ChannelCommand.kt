package io.github.bluesheep2804.selenechat.command

import arrow.core.Either
import io.github.bluesheep2804.selenechat.SeleneChat.channelManager
import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.SeleneChat.resource
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.channel.ChannelData.ChannelJoinError
import io.github.bluesheep2804.selenechat.channel.ChannelData.ChannelLeaveError
import io.github.bluesheep2804.selenechat.channel.ChannelManager.ChannelDeleteError
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
                        is Either.Right -> sender.sendCommandResult(resource.command.channelSuccessCreate(result.value))
                    }
                }
                "delete" -> {
                    if (args.size < 2) {
                        sender.sendCommandResult(resource.command.channelErrorDeleteEmpty)
                        return false
                    }
                    when (val result = channelManager.delete(args[1])) {
                        is Either.Left -> {
                            sender.sendCommandResult(when (val it = result.value) {
                                is ChannelDeleteError.ChannelNotFound -> resource.command.channelErrorDeleteNotExists
                            })
                        }
                        is Either.Right -> sender.sendCommandResult(resource.command.channelSuccessDelete(result.value))
                    }
                }
                "list" -> {
                    val returnMessage = Component.text().append(resource.command.channelSuccessList)
                    channelManager.allChannels.forEach { (key, channel) ->
                        returnMessage.appendNewline()
                                .append(Component.text("- "))
                                .append(channel.displayName)
                                .append(Component.text("(${key})", NamedTextColor.GRAY))
                    }
                    sender.sendCommandResult(returnMessage.build())
                }
                "join" -> {
                    if (args.size < 2) {
                        sender.sendCommandResult(resource.command.channelErrorJoinEmpty)
                        return false
                    }
                    val channel = channelManager.allChannels[args[1]]
                    if (channel is ChannelData) {
                        when (val result = channel.join(sender)) {
                            is Either.Left -> {}
                            is Either.Right -> sender.sendCommandResult(resource.command.channelSuccessJoin(channel))
                        }
                        channelManager.playerChannelMap[sender.uniqueId] = channel.name
                        sender.sendCommandResult(resource.command.channelSuccessJoinSwitch(channel))
                    } else {
                        if (args[1] == config.globalMarker) {
                            channelManager.playerChannelMap.remove(sender.uniqueId)
                            sender.sendCommandResult(resource.command.channelSuccessJoinSwitchGlobal)
                        } else {
                            sender.sendCommandResult(resource.command.channelErrorJoinNotFound)
                            return false
                        }
                    }
                }
                "leave" -> {
                    if (args.size < 2) {  // TODO: 会話中のチャンネルから抜けるようにする
                        sender.sendCommandResult(resource.command.channelErrorLeaveEmpty)
                        return false
                    }
                    val channel = channelManager.allChannels[args[1]]
                    if (channel is ChannelData) {
                        when (val result = channel.leave(sender)) {
                            is Either.Left -> {
                                sender.sendCommandResult(when (result.value) {
                                    is ChannelLeaveError.NotInChannel -> resource.command.channelErrorLeaveNotInChannel
                                })
                            }
                            is Either.Right -> sender.sendCommandResult(resource.command.channelSuccessLeave(channel))
                        }

                    } else {
                        sender.sendCommandResult(resource.command.channelErrorLeaveNotFound)
                        return false
                    }
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
            1 -> listOf("list", "create", "delete", "join", "leave").filter { it.startsWith(args[0]) || args[0] == "" }
            2 -> when (args[0]) {
                "delete", "join", "leave" -> channelManager.allChannels.keys.filter { it.startsWith(args[1]) || args[1] == "" }
                else -> emptyList()
            }
            else -> emptyList()
        }
    }

    companion object {
        const val COMMAND_NAME = "channel"
        val COMMAND_ALIASES = arrayOf("ch")
        const val PERMISSION = "selenechat.command.channel"
    }
}