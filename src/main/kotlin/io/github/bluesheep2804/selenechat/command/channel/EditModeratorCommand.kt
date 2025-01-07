package io.github.bluesheep2804.selenechat.command.channel

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.channel.ChannelData
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import java.util.*

class EditModeratorCommand: SubEditCommand() {
    override val commandName: String = "moderator"
    override val permission: String = "selenechat.channel.moderator"

    override fun execute(sender: SeleneChatPlayer, args: Array<String>, channel: ChannelData): Boolean {
        if (args.size < 3) {
            val moderators = Component.text().append(SeleneChat.resource.command.channelSuccessEditModeratorCurrentValue)
            channel.moderators.forEach {
                val player = SeleneChat.plugin.getPlayer(UUID.fromString(it))
                val playerComponent = if (player.isOnline) {
                    Component.text(player.displayName).hoverEvent(player.asHoverEvent())
                } else {
                    Component.text().content("(")
                            .append(SeleneChat.resource.offline)
                            .append(Component.text(")${it}"))
                            .color(NamedTextColor.GRAY)
                            .hoverEvent(HoverEvent.showEntity(Key.key("player"), player.uniqueId))
                }
                moderators.appendNewline()
                        .append(Component.text("- "))
                        .append(playerComponent)
            }
            sender.sendCommandResult(moderators.build())
        } else {
            val player = SeleneChat.plugin.getPlayer(if (args[2].startsWith("-")) args[2].removePrefix("-") else args[2])
            if (!player.isOnline) {
                sender.sendCommandResult(SeleneChat.resource.command.channelErrorEditModeratorNotOnline)
                return false
            }
            if (args[2].startsWith("-")) {
                if (!channel.isModerator(player)) {
                    sender.sendCommandResult(SeleneChat.resource.command.channelErrorEditModeratorNotModerator)
                    return false
                }
                channel.moderators -= player.uniqueId.toString()
                sender.sendCommandResult(SeleneChat.resource.command.channelSuccessEditModeratorExclude(player))
            } else {
                if (channel.isModerator(player)) {
                    sender.sendCommandResult(SeleneChat.resource.command.channelErrorEditModeratorAlreadyModerator)
                    return false
                }
                channel.moderators += player.uniqueId.toString()
                sender.sendCommandResult(SeleneChat.resource.command.channelSuccessEditModerator(player))
            }
            SeleneChat.channelManager.save(channel)
        }
        return true
    }
}
