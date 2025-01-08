package io.github.bluesheep2804.selenechat.command.channel

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.SeleneChat.resource
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import java.util.*

class EditInfoCommand: SubEditCommand() {
    override val commandName: String = "info"
    override val permission: String = "selenechat.channel.info"

    override fun execute(sender: SeleneChatPlayer, args: Array<String>, channel: ChannelData): Boolean {
        val component = Component.text().append(resource.command.channelSuccessEditInfoHeader(channel)).appendNewline()

        if (channel.playerList.isEmpty()) {
            component.append(resource.command.channelSuccessEditInfoNoPlayer)
        } else {
            component.append(resource.command.channelSuccessEditInfoPlayer(channel.playerList.size)).appendNewline()
            channel.playerList.forEach {
                val player = SeleneChat.plugin.getPlayer(UUID.fromString(it))
                component.append(
                        (
                                if (player.isOnline) Component.text(player.displayName) else Component.translatable("gui.socialInteractions.status_offline").color(NamedTextColor.GRAY)
                        ).hoverEvent(player.asHoverEvent())
                ).append(Component.text(", "))
            }
        }

        sender.sendMessage(component.build())
        return true
    }
}
