package io.github.bluesheep2804.selenechat.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ConvertMode {
    @SerialName("none") NONE,
    @SerialName("kana") KANA,
    @SerialName("ime") IME
}
