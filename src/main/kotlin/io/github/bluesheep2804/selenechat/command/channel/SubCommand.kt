package io.github.bluesheep2804.selenechat.command.channel

import io.github.bluesheep2804.selenechat.SeleneChat.resource
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer

abstract class SubCommand {
    abstract val commandName: String
    abstract val permission: String

    abstract fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean

    fun checkPermission(sender: SeleneChatPlayer): Boolean {
        return if (!sender.hasPermission(permission)) {
            sender.sendCommandResult(resource.command.generalErrorNoPermission)
            false
        } else {
            true
        }
    }
}
