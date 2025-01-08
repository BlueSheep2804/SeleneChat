package io.github.bluesheep2804.selenechat.command.channel

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.common.ConvertMode
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

class EditJpCommand: SubEditCommand() {
    override val commandName: String = "jp"
    override val permission: String = "selenechat.channel.jp"

    override fun execute(sender: SeleneChatPlayer, args: Array<String>, channel: ChannelData): Boolean {
        if (args.size < 3) {
            sender.sendCommandResult(SeleneChat.resource.command.channelSuccessEditJapanizeCurrentValue(channel.japanize))
        } else {
            channel.japanize = when (args[2]) {
                "none" -> ConvertMode.NONE
                "kana" -> ConvertMode.KANA
                "ime" -> ConvertMode.IME
                else -> {
                    sender.sendCommandResult(SeleneChat.resource.command.channelErrorEditJapanizeUnexpectedArgs)
                    return false
                }
            }
            sender.sendCommandResult(SeleneChat.resource.command.channelSuccessEditJapanize(channel.japanize))
            SeleneChat.channelManager.save(channel)
        }
        return true
    }
}
