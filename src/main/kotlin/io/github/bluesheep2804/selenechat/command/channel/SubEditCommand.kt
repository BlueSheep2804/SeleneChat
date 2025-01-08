package io.github.bluesheep2804.selenechat.command.channel

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

abstract class SubEditCommand {
    abstract val commandName: String
    abstract val permission: String

    abstract fun execute(sender: SeleneChatPlayer, args: Array<String>, channel: ChannelData): Boolean

    fun checkPermission(sender: SeleneChatPlayer): Boolean {
        return if (!sender.hasPermission(permission)) {
            sender.sendCommandResult(SeleneChat.resource.command.generalErrorNoPermission)
            false
        } else {
            true
        }
    }
}
