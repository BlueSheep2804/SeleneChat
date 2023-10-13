package io.github.bluesheep2804.selenechat.channel

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ChannelManager(private val file: File) {
    private val channelDirectory = File(file, "channel")
    private val yamlConfiguration = YamlConfiguration(strictMode = false)
    private val yaml = Yaml(configuration = yamlConfiguration)
    val allChannels = mutableListOf<ChannelData>()

    init {
        reload()
    }

    fun reload() {
        if (allChannels.isNotEmpty()) {
            allChannels.clear()
        }
        channelDirectory.listFiles()?.forEach {
            val input = FileInputStream(it)
            allChannels += yaml.decodeFromStream(ChannelData.serializer(), input)
        }
    }

    fun create(name: String): Either<ChannelCreateError, ChannelData> {
        if (!allChannels.none { it.name == name }) {
            return ChannelCreateError.AlreadyExists.left()
        }
        val channelFile = File(channelDirectory, "${name}.yml")
        val channel = ChannelData(name)
        if (!channelDirectory.exists()) {
            channelDirectory.mkdirs()
        }
        channelFile.createNewFile()
        val output = FileOutputStream(channelFile)
        yaml.encodeToStream(ChannelData.serializer(), channel, output)
        allChannels += channel
        return channel.right()
    }

    sealed interface ChannelCreateError {
        object AlreadyExists : ChannelCreateError
    }
}