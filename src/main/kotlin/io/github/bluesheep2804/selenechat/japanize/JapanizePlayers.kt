package io.github.bluesheep2804.selenechat.japanize

import kotlinx.serialization.Serializable

@Serializable
data class JapanizePlayers(
        val enable: MutableList<String> = mutableListOf(),
        val disable: MutableList<String> = mutableListOf()
)
