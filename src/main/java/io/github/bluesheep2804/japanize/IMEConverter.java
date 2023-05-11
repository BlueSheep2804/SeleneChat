package io.github.bluesheep2804.japanize;

import com.google.common.io.CharStreams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * ひらがなのみの文章を漢字に変換するクラス
 * @author ucchy, BlueSheep2804
 * @see <a href="https://github.com/ucchyocean/LunaChat/tree/master/src/main/java/com/github/ucchyocean/lc3/japanize/IMEConverter.java">LunaChat Github</a>
 */
public class IMEConverter {
    private static final String GOOGLE_IME_URL = "http://www.google.com/transliterate?langpair=ja-Hira|ja&text=";
    public static String googleIME(String original) {
        HttpURLConnection urlconn = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(GOOGLE_IME_URL + URLEncoder.encode(original, "UTF-8"));
            urlconn = (HttpURLConnection) url.openConnection();
            urlconn.setRequestMethod("GET");
            urlconn.setInstanceFollowRedirects(false);
            urlconn.connect();

            reader = new BufferedReader(
                    new InputStreamReader(urlconn.getInputStream(), "UTF-8")
            );

            String json = CharStreams.toString(reader);
            String parsed = GoogleIME.parseJson(json);

            return parsed;
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (urlconn != null) {
                urlconn.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
