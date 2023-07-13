package io.github.bluesheep2804.selenechat.player

import io.github.bluesheep2804.selenechat.SeleneChat
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
    val isEnabledJapanize: Boolean
        get() = SeleneChat.japanizePlayers.enable.contains(uniqueId.toString())
    abstract fun sendMessage(msg: Component)
    open fun asHoverEvent(): HoverEvent<out Examinable> {
        return HoverEvent.showEntity(HoverEvent.ShowEntity.of(Key.key("player"), uniqueId, Component.text(displayName)))
    }
    fun enableJapanize() {
        SeleneChat.japanizePlayers.enable.add(uniqueId.toString())
        SeleneChat.japanizePlayers.disable.remove(uniqueId.toString())
    }
    fun disableJapanize() {
        SeleneChat.japanizePlayers.disable.add(uniqueId.toString())
        SeleneChat.japanizePlayers.enable.remove(uniqueId.toString())
    }
    fun toggleJapanize() {
        if (isEnabledJapanize) disableJapanize() else enableJapanize()
    }
}