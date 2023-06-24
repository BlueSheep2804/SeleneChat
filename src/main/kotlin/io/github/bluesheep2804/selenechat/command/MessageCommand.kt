package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder

class MessageCommand : ICommand {
    override fun execute(sender: SeleneChatPlayer, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(SeleneChat.resource.command.messageErrorPlayer)
            return false
        }
        if (args.size == 1) {
            sender.sendMessage(SeleneChat.resource.command.messageErrorMessage)
            return false
        }
        val receiver = SeleneChat.plugin.getPlayer(args[0])
        receiver ?: run {
            sender.sendMessage(SeleneChat.resource.command.messageErrorPlayerNotFoundComponent(args[0]))
            return false
        }

        var message = args[1]
        if (args.size > 2) {
            for (i in 2 until args.size) {
                message += " ${args[i]}"
            }
        }

        val mm = MiniMessage.miniMessage()
        val senderTagResolver = Placeholder.component(
                "sender",
                Component.text(sender.displayName)
                        .hoverEvent(sender.asHoverEvent())
                        .clickEvent(ClickEvent.suggestCommand("/tell ${sender.displayName} "))
        )
        val receiverTagResolver = Placeholder.component(
                "receiver",
                Component.text(receiver.displayName)
                        .hoverEvent(receiver.asHoverEvent())
                        .clickEvent(ClickEvent.suggestCommand("/tell ${receiver.displayName} "))
        )
        val messageTagResolver = Placeholder.component(
                "message",
                ChatMessage.message(message)
        )
        val returnMessage = mm.deserialize(config.chatFormatPrivateMessage, senderTagResolver, receiverTagResolver, messageTagResolver)
        sender.sendMessage(returnMessage)
        receiver.sendMessage(returnMessage)
        return true
    }

    override fun suggest(sender: SeleneChatPlayer, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> SeleneChat.plugin.getAllPlayers().map { it.displayName }
            else -> listOf()
        }
    }

    companion object {
        const val COMMAND_NAME = "message"
        val COMMAND_ALIASES = arrayOf("msg", "tell", "w")
        const val PERMISSION = "selenechat.command.message"
    }
}