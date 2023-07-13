package io.github.bluesheep2804.selenechat.japanize

import com.charleskorn.kaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class JapanizePlayersManager(private val file: File) {
    lateinit var japanizePlayers: JapanizePlayers
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
            val japanizePlayers = JapanizePlayers()
            val output = FileOutputStream(japanizeFile)
            Yaml.default.encodeToStream(JapanizePlayers.serializer(), japanizePlayers, output)
        }
        val japanizeFileInputStream = FileInputStream(japanizeFile)
        japanizePlayers = Yaml.default.decodeFromStream(JapanizePlayers.serializer(), japanizeFileInputStream)
    }
    fun save() {
        val japanizeFile = File(file, "jp.yml")
        if (!file.exists()) {
            file.mkdir()
        }
        val output = FileOutputStream(japanizeFile)
        Yaml.default.encodeToStream(JapanizePlayers.serializer(), japanizePlayers, output)
    }
}