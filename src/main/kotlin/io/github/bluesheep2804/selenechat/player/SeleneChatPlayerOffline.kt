package io.github.bluesheep2804.selenechat.player

import net.kyori.adventure.text.Component
import java.util.UUID

class SeleneChatPlayerOffline(override val uniqueId: UUID = UUID.fromString("0-0-0-0-0"), override val displayName: String = "", override val currentServerName: String = "") : SeleneChatPlayer() {
    override val isOnline = false
    override fun sendMessage(msg: Component) {}
}