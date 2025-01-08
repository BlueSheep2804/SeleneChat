package io.github.bluesheep2804.selenechat.command.channel

import io.github.bluesheep2804.selenechat.SeleneChat.channelManager
import io.github.bluesheep2804.selenechat.SeleneChat.resource
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class ListCommand: SubCommand() {
    override val commandName: String = "list"
    override val permission: String = "selenechat.channel.list"

    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        val returnMessage = Component.text().append(resource.command.channelSuccessList)
        channelManager.allChannels.forEach { (_, channel) ->
            if (!channel.visible) return@forEach
            returnMessage.appendNewline()
                    .append(channelIndicator(sender, channel)).appendSpace()
                    .append(channel.displayName)
                    .append(Component.text("(${channel.name})", NamedTextColor.GRAY))
        }
        sender.sendCommandResult(returnMessage.build())
        return true
    }

    private fun channelIndicator(sender: SeleneChatPlayer, channel: ChannelData): Component {
        if (channelManager.playerChannelMap[sender.uniqueId] == channel.name) {
            return resource.command.channelSuccessListIndicatorSpeak.hoverEvent(resource.command.channelSuccessListIndicatorSpeakHover)
        }

        if (channel.playerList.contains(sender.uniqueId.toString())) {
            return resource.command.channelSuccessListIndicatorJoins.hoverEvent(resource.command.channelSuccessListIndicatorJoinsHover)
        }

        return resource.command.channelSuccessListIndicator.hoverEvent(resource.command.channelSuccessListIndicatorHover)
    }
}
