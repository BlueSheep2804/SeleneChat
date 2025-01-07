package io.github.bluesheep2804.selenechat.command.channel

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class ListCommand: SubCommand() {
    override val commandName: String = "list"
    override val permission: String = "selenechat.channel.list"

    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        val returnMessage = Component.text().append(SeleneChat.resource.command.channelSuccessList)
        SeleneChat.channelManager.allChannels.forEach { (key, channel) ->
            if (!channel.visible) return@forEach
            returnMessage.appendNewline()
                    .append(Component.text("- "))
                    .append(channel.displayName)
                    .append(Component.text("(${key})", NamedTextColor.GRAY))
        }
        sender.sendCommandResult(returnMessage.build())
        return true
    }
}
