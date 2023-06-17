package io.github.bluesheep2804.selenechat.command

import io.github.bluesheep2804.selenechat.SeleneChatConfigData
import io.github.bluesheep2804.selenechat.message.ChatMessage
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder

class MessageCommand(private val config: SeleneChatConfigData) {
    fun execute(sender: SeleneChatPlayer, receiver: SeleneChatPlayer, message: String): Boolean {
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
                ChatMessage.message(config.chatFormatMessage, message, config.convertMode)
        )
        val returnMessage = mm.deserialize(config.chatFormatPrivateMessage, senderTagResolver, receiverTagResolver, messageTagResolver)
        sender.sendMessage(returnMessage)
        receiver.sendMessage(returnMessage)
        return true
    }

    fun onTabComplete(args: Array<String>, onlinePlayers: List<String>): List<String> {
        return when (args.size) {
            1 -> onlinePlayers
            else -> listOf()
        }
    }

    companion object {
        const val COMMAND_NAME = "message"
        val COMMAND_ALIASES = arrayOf("msg", "tell", "w")
        const val PERMISSION = "selenechat.command.message"
    }
}