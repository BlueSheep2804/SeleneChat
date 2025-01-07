package io.github.bluesheep2804.selenechat.command.channel

import arrow.core.Either
import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

class JoinCommand: SubCommand() {
    override val commandName: String = "join"
    override val permission: String = "selenechat.channel.join"

    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        if (args.size < 2) {
            sender.sendCommandResult(SeleneChat.resource.command.channelErrorJoinEmpty)
            return false
        }

        val channel = SeleneChat.channelManager.allChannels[args[1]]
        if (channel is ChannelData) {
            when (val result = channel.join(sender)) {
                is Either.Left -> when (result.value) {
                    ChannelData.ChannelJoinError.ConsolePlayer -> {
                        sender.sendCommandResult(SeleneChat.resource.command.channelErrorJoinConsole)
                        return false
                    }
                    ChannelData.ChannelJoinError.AlreadyJoins -> {}
                }
                is Either.Right -> sender.sendCommandResult(SeleneChat.resource.command.channelSuccessJoin(channel))
            }
            SeleneChat.channelManager.playerChannelMap[sender.uniqueId] = channel.name
            sender.sendCommandResult(SeleneChat.resource.command.channelSuccessJoinSwitch(channel))
        } else {
            if (args[1] == SeleneChat.config.globalMarker) {
                SeleneChat.channelManager.playerChannelMap.remove(sender.uniqueId)
                sender.sendCommandResult(SeleneChat.resource.command.channelSuccessJoinSwitchGlobal)
            } else {
                sender.sendCommandResult(SeleneChat.resource.command.channelErrorJoinNotFound)
                return false
            }
        }
        return true
    }
}
