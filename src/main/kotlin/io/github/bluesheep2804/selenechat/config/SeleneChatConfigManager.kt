package io.github.bluesheep2804.selenechat.config

import com.charleskorn.kaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SeleneChatConfigManager(private val file: File) {
    lateinit var config: SeleneChatConfigData
    init {
        reload()
    }
    fun reload() {
        val configFile = File(file, "config.yml")
        if (!file.exists()) {
            file.mkdir()
        }
        if (!configFile.exists()) {
            configFile.createNewFile()
            val config = SeleneChatConfigData()
            val output = FileOutputStream(configFile)
            Yaml.default.encodeToStream(SeleneChatConfigData.serializer(), config, output)
        }
        val configFileInputStream = FileInputStream(configFile)
        config = Yaml.default.decodeFromStream(SeleneChatConfigData.serializer(), configFileInputStream)
    }

    fun checkVersion(): String {
        return if (config.configVersion < SeleneChatConfigData().configVersion) TEXT_VERSION_OUTDATED
        else if (config.configVersion > SeleneChatConfigData().configVersion) TEXT_VERSION_NEWER
        else TEXT_VERSION_LATEST
    }

    companion object {
        const val TEXT_VERSION_OUTDATED = "The config file appears to be out of date. Please back up the current file and check the differences. The config file will be loaded, but unexpected glitches may occur."
        const val TEXT_VERSION_NEWER = "The config file has been created with a newer version than the current one. The config file will be loaded, but unexpected glitches may occur."
        const val TEXT_VERSION_LATEST = "The config file is up-to-date!"
    }
}
