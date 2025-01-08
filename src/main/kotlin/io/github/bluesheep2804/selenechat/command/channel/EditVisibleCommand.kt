package io.github.bluesheep2804.selenechat.command.channel

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

class EditVisibleCommand: SubEditCommand() {
    override val commandName: String = "visible"
    override val permission: String = "selenechat.channel.visible"

    override fun execute(sender: SeleneChatPlayer, args: Array<String>, channel: ChannelData): Boolean {
        if (args.size < 3) {
            sender.sendCommandResult(SeleneChat.resource.command.channelSuccessEditVisibleCurrentValue(channel.visible))
        } else {
            channel.visible = when (args[2].lowercase()) {
                "true" -> true
                "false" -> false
                else -> {
                    sender.sendCommandResult(SeleneChat.resource.command.channelErrorEditVisibleUnexpectedArgs)
                    return false
                }
            }
            sender.sendCommandResult(SeleneChat.resource.command.channelSuccessEditVisible(channel.visible))
            SeleneChat.channelManager.save(channel)
        }
        return true
    }
}
