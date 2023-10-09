package io.github.bluesheep2804.selenechat.resource

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage

@Serializable
data class ResourceData(
        var resourceVersion: Int = 1,
        val configVersionOutdated: String = "The config file appears to be out of date. The config file will be loaded, but unexpected glitches may occur.",
        val configVersionNewer: String = "The config file has been created with a newer version than the current one. The config file will be loaded, but unexpected glitches may occur.",
        val configVersionLatest: String = "The config file is up-to-date!",
        @Serializable(with = ComponentSerializer::class)
        val prefix: Component = MiniMessage.miniMessage().deserialize("<reset>[<#d9e4ff>SC<reset>]"),
        @Serializable(with = ComponentSerializer::class)
        val enabled: Component = Component.text("Enabled", NamedTextColor.GREEN),
        @Serializable(with = ComponentSerializer::class)
        val disabled: Component = Component.text("Disabled", NamedTextColor.RED),
        @Serializable(with = ComponentSerializer::class)
        val hoverTextServer: Component = Component.translatable("selectServer.select"),
        val command: CommandResourceData = CommandResourceData()
)
