package io.github.bluesheep2804.selenechat.resource

import kotlinx.serialization.Serializable

@Serializable
data class ResourceData(
        val configVersionOutdated: String = "The config file appears to be out of date. Please back up the current file and check the differences. The config file will be loaded, but unexpected glitches may occur.",
        val configVersionNewer: String = "The config file has been created with a newer version than the current one. The config file will be loaded, but unexpected glitches may occur.",
        val configVersionLatest: String = "The config file is up-to-date!",
        val command: CommandResourceData = CommandResourceData()
)
