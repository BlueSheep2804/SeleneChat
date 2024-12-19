package io.github.bluesheep2804.selenechat.command

import arrow.core.Either
import io.github.bluesheep2804.selenechat.SeleneChat.channelManager
import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.SeleneChat.plugin
import io.github.bluesheep2804.selenechat.SeleneChat.resource
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.channel.ChannelData.ChannelLeaveError
import io.github.bluesheep2804.selenechat.channel.ChannelManager.ChannelCreateError
import io.github.bluesheep2804.selenechat.channel.ChannelManager.ChannelDeleteError
import io.github.bluesheep2804.selenechat.common.ConvertMode
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import java.util.*

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
                    when (val result = channelManager.create(args[1], sender)) {
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
                    val channel = channelManager.allChannels[args[1]]
                    if (channel !is ChannelData) {
                        sender.sendCommandResult(resource.command.channelErrorDeleteNotExists)
                        return false
                    }

                    if (!channel.isModerator(sender)) {
                        sender.sendCommandResult(resource.command.channelErrorDeleteNotModerator)
                        return false
                    }
                    when (val result = channelManager.delete(args[1])) {
                        is Either.Left -> {
                            sender.sendCommandResult(when (val it = result.value) {
                                is ChannelDeleteError.ChannelNotFound -> resource.command.channelErrorDeleteNotExists
                            })
                            return false
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
                    val channel = if (args.size < 2) {
                        channelManager.getPlayerChannel(sender)
                    } else {
                        channelManager.allChannels[args[1]]
                    }
                    if (channel is ChannelData) {
                        when (val result = channel.leave(sender)) {
                            is Either.Left -> {
                                sender.sendCommandResult(when (result.value) {
                                    is ChannelLeaveError.NotInChannel -> resource.command.channelErrorLeaveNotInChannel
                                })
                            }
                            is Either.Right -> {
                                sender.sendCommandResult(resource.command.channelSuccessLeave(channel))

                                if (channel.name == channelManager.playerChannelMap[sender.uniqueId]) {
                                    channelManager.playerChannelMap.remove(sender.uniqueId)
                                    sender.sendCommandResult(resource.command.channelSuccessJoinSwitchGlobal)
                                }
                            }
                        }

                    } else {
                        sender.sendCommandResult(resource.command.channelErrorLeaveNotFound)
                        return false
                    }
                }
                else -> if (args[0].startsWith(":")) {
                    val channel = channelManager.allChannels[args[0].removePrefix(":")]
                    if (channel !is ChannelData) {
                        sender.sendCommandResult(resource.command.channelErrorEditNotFound)
                        return false
                    }
                    if (!channel.isModerator(sender)) {
                        sender.sendCommandResult(resource.command.channelErrorEditNotModerator)
                        return false
                    }
                    when (args[1]) {
                        "format" -> {
                            if (args.size < 3) {
                                sender.sendCommandResult(resource.command.channelSuccessEditFormatCurrentValue(channel.format))
                                return false
                            } else {
                                var format = args[2]
                                if (args.size > 3) {
                                    for (i in 3 until args.size) {
                                        format += " ${args[i]}"
                                    }
                                }
                                channel.format = format
                                sender.sendCommandResult(resource.command.channelSuccessEditFormat(channel.format))
                                channelManager.save(channel)
                            }
                        }
                        "jp" -> {
                            if (args.size < 3) {
                                sender.sendCommandResult(resource.command.channelSuccessEditJapanizeCurrentValue(channel.japanize))
                                return false
                            }
                            channel.japanize = when (args[2]) {
                                "none" -> ConvertMode.NONE
                                "kana" -> ConvertMode.KANA
                                "ime" -> ConvertMode.IME
                                else -> {
                                    sender.sendCommandResult(resource.command.channelErrorEditJapanizeUnexpectedArgs)
                                    return false
                                }
                            }
                            sender.sendCommandResult(resource.command.channelSuccessEditJapanize(channel.japanize))
                            channelManager.save(channel)
                        }
                        "moderator" -> {
                            if (args.size < 3) {
                                val moderators = Component.text().append(resource.command.channelSuccessEditModeratorCurrentValue)
                                channel.moderators.forEach {
                                    val player = plugin.getPlayer(UUID.fromString(it))
                                    val playerComponent = if (player.isOnline) {
                                        Component.text(player.displayName).hoverEvent(player.asHoverEvent())
                                    } else {
                                        Component.text().content("(")
                                                .append(resource.offline)
                                                .append(Component.text(")${it}"))
                                                .color(NamedTextColor.GRAY)
                                                .hoverEvent(HoverEvent.showEntity(Key.key("player"), player.uniqueId))
                                    }
                                    moderators.appendNewline()
                                            .append(Component.text("- "))
                                            .append(playerComponent)
                                }
                                sender.sendCommandResult(moderators.build())
                                return false
                            }
                            val player = plugin.getPlayer(if (args[2].startsWith("-")) args[2].removePrefix("-") else args[2])
                            if (!player.isOnline) {
                                sender.sendCommandResult(resource.command.channelErrorEditModeratorNotOnline)
                                return false
                            }
                            if (args[2].startsWith("-")) {
                                if (!channel.isModerator(player)) {
                                    sender.sendCommandResult(resource.command.channelErrorEditModeratorNotModerator)
                                    return false
                                }
                                channel.moderators -= player.uniqueId.toString()
                                sender.sendCommandResult(resource.command.channelSuccessEditModeratorExclude(player))
                            } else {
                                if (channel.isModerator(player)) {
                                    sender.sendCommandResult(resource.command.channelErrorEditModeratorAlreadyModerator)
                                    return false
                                }
                                channel.moderators += player.uniqueId.toString()
                                sender.sendCommandResult(resource.command.channelSuccessEditModerator(player))
                            }
                            channelManager.save(channel)
                        }
                        else -> {
                            sender.sendCommandResult(resource.command.channelErrorEditSubCommandNotExists)
                            return false
                        }
                    }
                } else {
                    sender.sendCommandResult(resource.command.channelErrorSubCommandNotFound)
                }
            }
        }
        return true
    }

    override fun suggest(sender: SeleneChatPlayer, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> if (args[0].startsWith(":")) {
                channelManager.allChannels.keys.map { ":${it}" }
            } else {
                listOf("list", "create", "delete", "join", "leave", ":").filter { it.startsWith(args[0]) || args[0] == "" }
            }
            2 -> when (args[0]) {
                "delete", "join", "leave" -> channelManager.allChannels.keys.filter { it.startsWith(args[1]) || args[1] == "" }
                else -> if (args[0].startsWith(":")) {
                    listOf("format", "jp", "moderator")
                } else {
                    emptyList()
                }
            }
            3 -> if (args[0].startsWith(":")) when (args[1]) {
                "jp" -> listOf("none", "kana", "ime")
                "moderator" -> plugin.getAllPlayers().map { it.displayName }.filter { it.startsWith(args[2]) || args[2] == "" }
                else -> emptyList()
            } else emptyList()
            else -> emptyList()
        }
    }

    companion object {
        const val COMMAND_NAME = "channel"
        val COMMAND_ALIASES = arrayOf("ch")
        const val PERMISSION = "selenechat.command.channel"
    }
}