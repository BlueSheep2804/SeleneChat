package io.github.bluesheep2804.selenechat

import com.charleskorn.kaml.YamlComment
import kotlinx.serialization.Serializable

@Serializable
data class SeleneChatConfigData(
        @YamlComment("Version of the config file. Do not change.") val configVersion: Int = 1,
        @YamlComment("test") var testdata: String = "hehe",
        @YamlComment("hoge") var hoge: String = "huga"
)
