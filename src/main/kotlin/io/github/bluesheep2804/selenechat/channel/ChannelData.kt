package io.github.bluesheep2804.selenechat.channel

import kotlinx.serialization.Serializable

@Serializable
data class ChannelData(
        val name: String,
        val displayName: String = name
)
