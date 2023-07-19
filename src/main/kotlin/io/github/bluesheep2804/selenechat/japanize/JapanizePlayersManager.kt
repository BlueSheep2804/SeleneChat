package io.github.bluesheep2804.selenechat.japanize

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class JapanizePlayersManager(private val file: File) {
    lateinit var japanizePlayers: MutableMap<String, Boolean>
    private val japanizePlayersSerializer = MapSerializer(String.serializer(), Boolean.serializer())
    init {
        reload()
    }
    fun reload() {
        val japanizeFile = File(file, "jp.yml")
        if (!file.exists()) {
            file.mkdir()
        }
        if (!japanizeFile.exists()) {
            japanizeFile.createNewFile()
            val japanizePlayers = emptyMap<String, Boolean>()
            val output = FileOutputStream(japanizeFile)
            Yaml.default.encodeToStream(japanizePlayersSerializer, japanizePlayers, output)
        }
        val japanizeFileInputStream = FileInputStream(japanizeFile)
        japanizePlayers = Yaml.default.decodeFromStream(japanizePlayersSerializer, japanizeFileInputStream).toMutableMap()
    }
    fun save() {
        val japanizeFile = File(file, "jp.yml")
        if (!file.exists()) {
            file.mkdir()
        }
        val output = FileOutputStream(japanizeFile)
        Yaml.default.encodeToStream(japanizePlayersSerializer, japanizePlayers, output)
    }
}