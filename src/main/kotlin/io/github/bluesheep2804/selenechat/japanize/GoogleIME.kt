package io.github.bluesheep2804.selenechat.japanize

import com.google.gson.Gson
import com.google.gson.JsonArray

/**
 * GoogleIMEの最初の変換候補を抽出して結合するクラス
 *
 * @author ucchy, BlueSheep2804
 * @see [LunaChat Github](https://github.com/ucchyocean/LunaChat/tree/master/src/main/java/com/github/ucchyocean/lc3/japanize/GoogleIME.java)
 */
object GoogleIME {
    @JvmStatic
    fun parseJson(json: String?): String {
        val result = StringBuilder()
        for (response in Gson().fromJson(json, JsonArray::class.java)) {
            result.append(response.asJsonArray[1].asJsonArray[0].asString)
        }
        return result.toString()
    }
}