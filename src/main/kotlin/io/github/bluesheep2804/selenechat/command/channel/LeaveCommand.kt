package io.github.bluesheep2804.selenechat.command.channel

import arrow.core.Either
import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

class LeaveCommand: SubCommand() {
    override val commandName: String = "leave"
    override val permission: String = "selenechat.channel.leave"

    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        val channel = if (args.size < 2) {
            SeleneChat.channelManager.getPlayerChannel(sender)
        } else {
            SeleneChat.channelManager.allChannels[args[1]]
        }
        if (channel is ChannelData) {
            when (val result = channel.leave(sender)) {
                is Either.Left -> {
                    sender.sendCommandResult(when (result.value) {
                        is ChannelData.ChannelLeaveError.NotInChannel -> SeleneChat.resource.command.channelErrorLeaveNotInChannel
                    })
                }
                is Either.Right -> {
                    sender.sendCommandResult(SeleneChat.resource.command.channelSuccessLeave(channel))

                    if (channel.name == SeleneChat.channelManager.playerChannelMap[sender.uniqueId]) {
                        SeleneChat.channelManager.playerChannelMap.remove(sender.uniqueId)
                        sender.sendCommandResult(SeleneChat.resource.command.channelSuccessJoinSwitchGlobal)
                    }
                }
            }
        } else {
            sender.sendCommandResult(SeleneChat.resource.command.channelErrorLeaveNotFound)
            return false
        }
        return true
    }
}
