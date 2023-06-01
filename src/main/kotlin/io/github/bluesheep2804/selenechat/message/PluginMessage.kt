package io.github.bluesheep2804.selenechat.message

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteStreams
import java.util.*

class PluginMessage(val message: String, val playerUUID: UUID, val playerDisplayName: String) {
    fun build(): ByteArray {
        val output = ByteStreams.newDataOutput()
        output.writeUTF(message)
        output.writeUTF(playerUUID.toString())
        output.writeUTF(playerDisplayName)
        return output.toByteArray()
    }

    companion object {
        fun fromByteArrayDataInput(input: ByteArrayDataInput): PluginMessage {
            val message = input.readUTF()
            val playerUUID = input.readUTF()
            val playerDisplayName = input.readUTF()
            return PluginMessage(message, UUID.fromString(playerUUID), playerDisplayName)
        }
    }
}