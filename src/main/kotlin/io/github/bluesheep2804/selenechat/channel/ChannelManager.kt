package io.github.bluesheep2804.selenechat.channel

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import io.github.bluesheep2804.selenechat.SeleneChat
import io.github.bluesheep2804.selenechat.player.SeleneChatPlayer
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class ChannelManager(private val file: File) {
    private val channelDirectory = File(file, "channel")
    private val yamlConfiguration = YamlConfiguration(strictMode = false)
    private val yaml = Yaml(configuration = yamlConfiguration)
    val allChannels = mutableMapOf<String, ChannelData>()
    val playerChannelMap = mutableMapOf<UUID, String>()

    init {
        reload()
    }

    fun reload() {
        if (allChannels.isNotEmpty()) {
            allChannels.clear()
        }
        channelDirectory.listFiles()?.forEach {
            val input = FileInputStream(it)
            val channel = yaml.decodeFromStream(ChannelData.serializer(), input)
            allChannels[channel.name] = channel
        }
    }

    fun save(channel: ChannelData) {
        val channelFile = File(channelDirectory, "${channel.name}.yml")
        val output = FileOutputStream(channelFile)
        yaml.encodeToStream(ChannelData.serializer(), channel, output)
    }

    fun create(name: String, moderator: SeleneChatPlayer): Either<ChannelCreateError, ChannelData> {
        if (!allChannels.none { it.key == name }) {
            return ChannelCreateError.AlreadyExists.left()
        }
        val channelFile = File(channelDirectory, "${name}.yml")
        val channel = ChannelData(name)
        channel.japanize = SeleneChat.plugin.config.convertMode
        channel.moderators += moderator.uniqueId.toString()
        if (!channelDirectory.exists()) {
            channelDirectory.mkdirs()
        }
        channelFile.createNewFile()
        val output = FileOutputStream(channelFile)
        yaml.encodeToStream(ChannelData.serializer(), channel, output)
        allChannels[channel.name] = channel
        return channel.right()
    }

    sealed interface ChannelCreateError {
        object AlreadyExists : ChannelCreateError
    }

    fun delete(name: String): Either<ChannelDeleteError, ChannelData> {
        val channel = allChannels.remove(name) ?: return ChannelDeleteError.ChannelNotFound.left()
        val channelFile = File(channelDirectory, "${name}.yml")
        if (channelFile.exists()) {
            channelFile.delete()
        }
        return channel.right()
    }

    sealed interface ChannelDeleteError {
        object ChannelNotFound : ChannelDeleteError
    }

    fun getPlayerChannel(player: SeleneChatPlayer): ChannelData? {
        val channelName = playerChannelMap[player.uniqueId]
        return allChannels[channelName]
    }
}