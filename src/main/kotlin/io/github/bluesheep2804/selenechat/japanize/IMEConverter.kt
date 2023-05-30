package io.github.bluesheep2804.selenechat.japanize

import com.google.common.io.CharStreams
import io.github.bluesheep2804.selenechat.japanize.GoogleIME.parseJson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.*

/**
 * ひらがなのみの文章を漢字に変換するクラス
 * @author ucchy, BlueSheep2804
 * @see [LunaChat Github](https://github.com/ucchyocean/LunaChat/tree/master/src/main/java/com/github/ucchyocean/lc3/japanize/IMEConverter.java)
 */
object IMEConverter {
    private const val GOOGLE_IME_URL = "http://www.google.com/transliterate?langpair=ja-Hira|ja&text="
    fun googleIME(original: String?): String {
        var urlconn: HttpURLConnection? = null
        var reader: BufferedReader? = null
        return try {
            val url = URL(GOOGLE_IME_URL + URLEncoder.encode(original, "UTF-8"))
            urlconn = url.openConnection() as HttpURLConnection
            urlconn.requestMethod = "GET"
            urlconn.instanceFollowRedirects = false
            urlconn.connect()
            reader = BufferedReader(
                    InputStreamReader(urlconn.inputStream, "UTF-8")
            )
            val json = CharStreams.toString(reader)
            parseJson(json)
        } catch (e: ProtocolException) {
            throw RuntimeException(e)
        } catch (e: MalformedURLException) {
            throw RuntimeException(e)
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        } finally {
            urlconn?.disconnect()
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }
        }
    }
}