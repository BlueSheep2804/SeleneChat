package io.github.bluesheep2804.selenechat.japanize;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * GoogleIMEの最初の変換候補を抽出して結合するクラス
 *
 * @author ucchy, BlueSheep2804
 * @see <a href="https://github.com/ucchyocean/LunaChat/tree/master/src/main/java/com/github/ucchyocean/lc3/japanize/GoogleIME.java">LunaChat Github</a>
 */
public class GoogleIME {
    public static String parseJson(String json) {
        StringBuilder result = new StringBuilder();
        for (JsonElement response : new Gson().fromJson(json, JsonArray.class)) {
            result.append(response.getAsJsonArray().get(1).getAsJsonArray().get(0).getAsString());
        }
        return result.toString();
    }
}
