package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.SeleneChat.channelManager
import io.github.bluesheep2804.selenechat.SeleneChat.plugin
import io.github.bluesheep2804.selenechat.SeleneChat.resource
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.command.channel.*
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerConsole

class ChannelCommand : ICommand {
    override val COMMAND_NAME: String = ChannelCommand.COMMAND_NAME
    override val COMMAND_ALIASES: Array<String> = ChannelCommand.COMMAND_ALIASES
    override val PERMISSION: String = ChannelCommand.PERMISSION
    private val subCommands: List<SubCommand> = listOf(
            CreateCommand(),
            DeleteCommand(),
            ListCommand(),
            JoinCommand(),
            LeaveCommand(),
    )
    private val subEditCommands: List<SubEditCommand> = listOf(
            EditFormatCommand(),
            EditJpCommand(),
            EditModeratorCommand(),
            EditVisibleCommand(),
    )

    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendCommandResult(resource.command.channelErrorSubCommandEmpty)
            return false
        }

        if (args[0].startsWith(":")) {
            val channel = channelManager.allChannels[args[0].removePrefix(":")]
            if (channel !is ChannelData) {
                sender.sendCommandResult(resource.command.channelErrorEditNotFound)
                return false
            }

            if (!channel.isModerator(sender) && sender !is SeleneChatPlayerConsole) {
                sender.sendCommandResult(resource.command.channelErrorEditNotModerator)
                return false
            }

            if (args.size < 2) {
                sender.sendCommandResult(resource.command.channelErrorSubCommandEmpty)
                return false
            }

            subEditCommands.forEach {
                if (args[1].lowercase() == it.commandName) {
                    if (!it.checkPermission(sender)) return false
                    it.execute(sender, args, channel)
                    return true
                }
            }

            sender.sendCommandResult(resource.command.channelErrorEditSubCommandNotExists)
            return false
        }

        subCommands.forEach {
            if (args[0].lowercase() == it.commandName) {
                if (!it.checkPermission(sender)) return false
                it.execute(sender, args)
                return true
            }
        }

        sender.sendCommandResult(resource.command.channelErrorSubCommandNotFound)
        return false
    }

    override fun suggest(sender: SeleneChatPlayer, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> if (args[0].startsWith(":")) {
                channelManager.allChannels.filterValues { it.visible }.keys.map { ":${it}" }.filter { it.startsWith(args[0]) || channelManager.allChannels[it]!!.visible }
            } else {
                listOf("list", "create", "delete", "join", "leave", ":").filter { it.startsWith(args[0]) || args[0] == "" }
            }
            2 -> when (args[0]) {
                "delete", "join", "leave" -> channelManager.allChannels.filterValues { it.visible }.keys.filter { it.startsWith(args[1]) || args[1] == "" }
                else -> if (args[0].startsWith(":")) {
                    listOf("format", "jp", "moderator", "visible").filter { it.startsWith(args[1]) || args[1] == "" }
                } else {
                    emptyList()
                }
            }
            3 -> if (args[0].startsWith(":")) when (args[1]) {
                "jp" -> listOf("none", "kana", "ime").filter { it.startsWith(args[2]) || args[2] == "" }
                "moderator" -> plugin.getAllPlayers().map { it.displayName }.filter { it.startsWith(args[2]) || args[2] == "" }
                "visible" -> listOf("true", "false").filter { it.startsWith(args[2]) || args[2] == "" }
                else -> emptyList()
            } else emptyList()
            else -> emptyList()
        }
    }

    companion object {
        const val COMMAND_NAME = "channel"
        val COMMAND_ALIASES = arrayOf("ch")
        const val PERMISSION = "selenechat.channel"
    }
}