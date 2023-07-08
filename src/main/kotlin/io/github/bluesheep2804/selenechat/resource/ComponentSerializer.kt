package io.github.bluesheep2804.selenechat.resource

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

object ComponentSerializer : KSerializer<Component> {
    private val mm = MiniMessage.miniMessage()
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Component", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Component) {
        encoder.encodeString(mm.serialize(value))
    }

    override fun deserialize(decoder: Decoder): Component {
        return mm.deserialize(decoder.decodeString())
    }
}
