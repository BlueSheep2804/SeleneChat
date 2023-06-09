package io.github.bluesheep2804.selenechat

import com.charleskorn.kaml.YamlComment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeleneChatConfigData(
        @YamlComment("Version of the config file. Do not change.")
        val configVersion: Int = 1,

        @YamlComment("Specifying the chat conversion mode.", "Possible values: none, hiragana, ime")
        var convertMode: ConvertMode = ConvertMode.IME,
        @YamlComment("Whether to send message content to proxy servers using plugin messages.", "Applies to Spigot only.")
        var shouldSendPluginMessage: Boolean = false,
        @YamlComment("Whether to receive plugin messages from the server to the proxy server.", "Applies to Bungeecord and Velocity only.")
        var shouldReceivePluginMessage: Boolean = false,
        @YamlComment("Whether to include the name of the server where the sender is located in the chat.", "Applies to Bungeecord and Velocity only.")
        var shouldShowServerName: Boolean = true,

        @YamlComment(
                "Specifies the format of the chat.",
                "MiniMessage tags can be used.",
                "<username> -> Display name of speaker",
                "<servername:[prefix]:[suffix]> -> Name of the server where the speaker is located",
                "<message> -> Message body"
        )
        var chatFormat: String = "<username><servername:@:><green>:</green> <message>",
        @YamlComment(
                "Specifies the format of the message portion of the chat.",
                "MiniMessage tags can be used.",
                "<message> -> Message body",
                "<jp:[prefix]:[suffix]> -> Message after Japanese conversion"
        )
        var chatFormatMessage: String = "<message><gold><jp: (:)></gold>"
)

@Serializable
enum class ConvertMode {
        @SerialName("none") NONE,
        @SerialName("hiragana") KANA,
        @SerialName("ime") IME
}