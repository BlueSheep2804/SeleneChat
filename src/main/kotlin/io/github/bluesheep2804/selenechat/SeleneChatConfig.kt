package io.github.bluesheep2804.selenechat

import com.charleskorn.kaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SeleneChatConfig {
    companion object {
        fun load(file: File): SeleneChatConfigData {
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
            val config = Yaml.default.decodeFromStream(SeleneChatConfigData.serializer(), configFileInputStream)
            return config
        }

        const val TEXT_VERSION_OUTDATED = "The config file appears to be out of date. Please back up the current file and check the differences."
        const val TEXT_VERSION_NEWER = "The config file has been created with a newer version than the current one."
    }
}
