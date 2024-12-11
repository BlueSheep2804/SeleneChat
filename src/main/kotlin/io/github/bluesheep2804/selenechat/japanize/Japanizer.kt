package io.github.bluesheep2804.selenechat.japanize

import io.github.bluesheep2804.selenechat.SeleneChat.config
import io.github.bluesheep2804.selenechat.common.ConvertMode
import io.github.bluesheep2804.selenechat.common.ConvertMode.*

/**
 * ローマ字表記を漢字に変換するクラス
 * @author BlueSheep2804
 * @see [LunaChat Github](https://github.com/ucchyocean/LunaChat/tree/master/src/main/java/com/github/ucchyocean/lc3/japanize/Japanizer.java)
 */
class Japanizer(private val original: String) {
    fun japanize(convertMode: ConvertMode): String {
        return when (convertMode) {
            NONE -> ""
            KANA -> RomaToKana.convert(original)
            IME -> IMEConverter.googleIME(RomaToKana.convert(original))
        }
    }

    fun shouldConvert(): Boolean {
        return (original.toByteArray().size == original.length // 2バイト文字が無い
                && !original.matches("[ \\uFF61-\\uFF9F]+".toRegex())) // 半角カタカナが無い
    }
}
