package io.github.bluesheep2804.japanize;

/**
 * ローマ字表記を漢字に変換するクラス
 * @author BlueSheep2804
 * @see <a href="https://github.com/ucchyocean/LunaChat/tree/master/src/main/java/com/github/ucchyocean/lc3/japanize/Japanizer.java">LunaChat Github</a>
 */
public class Japanizer {
    public static String Japanizer(String original) {
        String kana = RomaToKana.convert(original);
        return IMEConverter.googleIME(kana);
    }

    public static boolean shouldConvert(String original) {
        return (original.getBytes().length == original.length()  // 2バイト文字が無い
            && !original.matches("[ \\uFF61-\\uFF9F]+"));  // 半角カタカナが無い
    }
}
