package io.github.bluesheep2804.selenechat.command.channel

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

class EditFormatCommand: SubEditCommand() {
    override val commandName: String = "format"
    override val permission: String = "selenechat.channel.format"

    override fun execute(sender: SeleneChatPlayer, args: Array<String>, channel: ChannelData): Boolean {
        if (args.size < 3) {
            sender.sendCommandResult(SeleneChat.resource.command.channelSuccessEditFormatCurrentValue(channel.format))
        } else {
            var format = args[2]
            if (args.size > 3) {
                for (i in 3 until args.size) {
                    format += " ${args[i]}"
                }
            }
            channel.format = format
            sender.sendCommandResult(SeleneChat.resource.command.channelSuccessEditFormat(channel.format))
            SeleneChat.channelManager.save(channel)
        }
        return true
    }
}
