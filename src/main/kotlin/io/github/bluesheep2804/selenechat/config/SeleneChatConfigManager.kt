package io.github.bluesheep2804.selenechat.config

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import io.github.bluesheep2804.selenechat.SeleneChat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SeleneChatConfigManager(private val file: File) {
    lateinit var config: SeleneChatConfigData
    private val yamlConfiguration = YamlConfiguration(strictMode = false, breakScalarsAt = 120)
    private val yaml = Yaml(configuration = yamlConfiguration)
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
            yaml.encodeToStream(SeleneChatConfigData.serializer(), config, output)
        }
        val configFileInputStream = FileInputStream(configFile)
        config = yaml.decodeFromStream(SeleneChatConfigData.serializer(), configFileInputStream)
    }

    fun checkVersion(): String {
        return if (config.configVersion < SeleneChatConfigData().configVersion) SeleneChat.resource.configVersionOutdated
        else if (config.configVersion > SeleneChatConfigData().configVersion) SeleneChat.resource.configVersionNewer
        else SeleneChat.resource.configVersionLatest
    }
}
