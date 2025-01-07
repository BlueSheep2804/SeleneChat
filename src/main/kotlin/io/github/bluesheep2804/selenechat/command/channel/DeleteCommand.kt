package io.github.bluesheep2804.selenechat.command.channel

import arrow.core.Either
import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.channel.ChannelManager
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayerConsole

class DeleteCommand: SubCommand() {
    override val commandName: String = "delete"
    override val permission: String = "selenechat.channel.delete"

    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        if (args.size < 2) {
            sender.sendCommandResult(SeleneChat.resource.command.channelErrorDeleteEmpty)
            return false
        }
        val channel = SeleneChat.channelManager.allChannels[args[1]]
        if (channel !is ChannelData) {
            sender.sendCommandResult(SeleneChat.resource.command.channelErrorDeleteNotExists)
            return false
        }

        if (!channel.isModerator(sender) && sender !is SeleneChatPlayerConsole) {
            sender.sendCommandResult(SeleneChat.resource.command.channelErrorDeleteNotModerator)
            return false
        }
        when (val result = SeleneChat.channelManager.delete(args[1])) {
            is Either.Left -> {
                sender.sendCommandResult(when (result.value) {
                    is ChannelManager.ChannelDeleteError.ChannelNotFound -> SeleneChat.resource.command.channelErrorDeleteNotExists
                })
                return false
            }
            is Either.Right -> sender.sendCommandResult(SeleneChat.resource.command.channelSuccessDelete(result.value))
        }
        return true
    }
}
