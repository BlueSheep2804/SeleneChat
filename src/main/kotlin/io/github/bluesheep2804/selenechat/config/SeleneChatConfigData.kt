package io.github.bluesheep2804.selenechat.config

import com.charleskorn.kaml.YamlComment
import kotlinx.serialization.Serializable

@Serializable
data class SeleneChatConfigData(
        @YamlComment("Version of the config file. Do not change.")
        val configVersion: Int = 1,

        @YamlComment("Specifying the chat conversion mode.", "Possible values: none, hiragana, ime")
        var convertMode: ConvertMode = ConvertMode.IME,
        @YamlComment("Whether to send message content to proxy servers using plugin messages.", "Applies to Spigot only.")
        var shouldSendPluginMessage: Boolean = false,
        @YamlComment("Choose whether to send the message in the standard Minecraft message format or in SeleneChat's own format.", "If true, SeleneChat's format will be used.", "Applies to Spigot only.")
        var useSeleneChatFormat: Boolean = false,
        @YamlComment("Whether to receive plugin messages from the server to the proxy server.", "Applies to Bungeecord and Velocity only.")
        var shouldReceivePluginMessage: Boolean = false,

        @YamlComment(
                "Specifies the format of the chat.",
                "MiniMessage tags can be used.",
                "<sender> -> Display name of sender",
                "<server:[prefix]:[suffix]> -> Name of the server where the sender is located",
                "<message> -> Message body"
        )
        var chatFormat: String = "<sender><server:@:><green>:</green> <message>",
        @YamlComment(
                "Specifies the format of the message portion of the chat.",
                "MiniMessage tags can be used.",
                "<message> -> Message body",
                "<jp:[prefix]:[suffix]> -> Message after Japanese conversion"
        )
        var chatFormatMessage: String = "<message><gold><jp: (:)></gold>",
        @YamlComment(
                "Specifies the format of the private chat.",
                "MiniMessage tags can be used.",
                "<sender> -> Display name of sender",
                "<senderserver:[prefix]:[suffix]> -> Name of the server where the sender is located",
                "<receiver> -> Display name of receiver",
                "<receiverserver:[prefix]:[suffix]> -> Name of the server where the receiver is located",
                "<message> -> Message body"
        )
        var chatFormatPrivateMessage: String = "<gray>[<sender><senderserver:@:> -> <receiver><receiverserver:@:>]</gray> <message>"
)
