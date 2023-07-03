package io.github.bluesheep2804.selenechat.resource

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ResourceManager(private val file: File) {
    lateinit var resource: ResourceData
    private val yamlConfiguration = YamlConfiguration(strictMode = false, breakScalarsAt = 160)
    private val yaml = Yaml(configuration = yamlConfiguration)
    init {
        reload()
    }

    fun reload() {
        val resourceFile = File(file, "message.yml")
        if (!file.exists()) {
            file.mkdir()
        }
        if (!resourceFile.exists()) {
            resourceFile.createNewFile()
            val messages = ResourceData()
            val output = FileOutputStream(resourceFile)
            yaml.encodeToStream(ResourceData.serializer(), messages, output)
        }
        val resourceFileInputStream = FileInputStream(resourceFile)
        this.resource = yaml.decodeFromStream(ResourceData.serializer(), resourceFileInputStream)
    }
}