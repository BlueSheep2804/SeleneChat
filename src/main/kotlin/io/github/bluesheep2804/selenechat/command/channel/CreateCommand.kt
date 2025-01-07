package io.github.bluesheep2804.selenechat.command.channel

import arrow.core.Either
import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.channel.ChannelManager
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

class CreateCommand: SubCommand() {
    override val commandName: String = "create"
    override val permission: String = "selenechat.channel.create"

    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        if (args.size < 2) {
            sender.sendCommandResult(SeleneChat.resource.command.channelErrorCreateEmpty)
            return false
        }

        when (val result = SeleneChat.channelManager.create(args[1].lowercase(), sender)) {
            is Either.Left -> {
                sender.sendCommandResult(when (result.value) {
                    is ChannelManager.ChannelCreateError.AlreadyExists -> SeleneChat.resource.command.channelErrorCreateExists
                })
                return false
            }
            is Either.Right -> sender.sendCommandResult(SeleneChat.resource.command.channelSuccessCreate(result.value))
        }
        return true
    }
}
