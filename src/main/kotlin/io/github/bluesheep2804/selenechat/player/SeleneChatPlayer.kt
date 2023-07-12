package io.github.bluesheep2804.selenechat.player

import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.examination.Examinable
import java.util.*

abstract class SeleneChatPlayer {
    abstract val displayName: String
    abstract val uniqueId: UUID
    abstract val currentServerName: String
    open val isOnline = true
    abstract fun sendMessage(msg: Component)
    open fun asHoverEvent(): HoverEvent<out Examinable> {
        return HoverEvent.showEntity(HoverEvent.ShowEntity.of(Key.key("player"), uniqueId, Component.text(displayName)))
    }
}