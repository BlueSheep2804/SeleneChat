package io.github.bluesheep2804.selenechat.player

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.examination.Examinable
import java.util.*

abstract class SeleneChatPlayerConsole: SeleneChatPlayer() {
    override val displayName: String
        get() = "CONSOLE"

    override val uniqueId: UUID
        get() = UUID.fromString("0-0-0-0-0")

    override val currentServerName: String
        get() = ""

    override val isConsole: Boolean
        get() = true

    override fun asHoverEvent(): HoverEvent<out Examinable> {
        return HoverEvent.showText(Component.text("CONSOLE"))
    }
}