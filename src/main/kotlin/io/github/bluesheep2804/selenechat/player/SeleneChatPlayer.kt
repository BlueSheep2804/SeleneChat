package io.github.bluesheep2804.selenechat.player

import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.SeleneChat.config
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
        get() {
            return if (SeleneChat.japanizePlayers.containsKey(uniqueId.toString())) {
                SeleneChat.japanizePlayers[uniqueId.toString()]!!
            } else {
                config.japanizeDefault
            }
        }
    abstract fun sendMessage(msg: Component)
    open fun asHoverEvent(): HoverEvent<out Examinable> {
        return HoverEvent.showEntity(HoverEvent.ShowEntity.of(Key.key("player"), uniqueId, Component.text(displayName)))
    }
    fun enableJapanize() {
        SeleneChat.japanizePlayers[uniqueId.toString()] = true
    }
    fun disableJapanize() {
        SeleneChat.japanizePlayers[uniqueId.toString()] = false
    }
    fun toggleJapanize() {
        if (isEnabledJapanize) disableJapanize() else enableJapanize()
    }
}