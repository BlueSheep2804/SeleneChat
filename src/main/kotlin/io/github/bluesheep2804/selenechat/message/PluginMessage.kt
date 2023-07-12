package io.github.bluesheep2804.selenechat.message

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteStreams
import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import java.util.*

class PluginMessage(val message: String, val player: SeleneChatPlayer) {
    fun build(): ByteArray {
        val output = ByteStreams.newDataOutput()
        output.writeUTF(message)
        output.writeUTF(player.uniqueId.toString())
        output.writeUTF(player.displayName)
        return output.toByteArray()
    }

    companion object {
        fun fromByteArrayDataInput(input: ByteArrayDataInput): PluginMessage {
            val message = input.readUTF()
            val playerUUID = input.readUTF()
            val playerDisplayName = input.readUTF()
            val player = SeleneChat.plugin.getPlayer(UUID.fromString(playerUUID))
            return PluginMessage(message, player)
        }
    }
}