// BufferedReader лучше чем stream подходит для работы с длинными строками,
// и соответственно текстами из-за большего размера буфера

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.regex.Pattern;

class Counter {
    // Используется String, так как не любой unicode символ влезет в char
    public static HashMap<String, Integer> count(BufferedReader br) throws IOException {
        return count(br, true, false);
    }

    public static HashMap<String, Integer> count(BufferedReader br, boolean onlyPrintable, boolean caseDependent)
            throws IOException {
        var out = new HashMap<String, Integer>();
        String line = null;
        while ((line = br.readLine()) != null) {
            combine(out, count(line, onlyPrintable, caseDependent));
        }
        return out;
    }

    public static HashMap<String, Integer> count(String s, boolean onlyPrintable, boolean caseDependent) {
        var out = new HashMap<String, Integer>();
        BreakIterator bi = BreakIterator.getCharacterInstance();
        bi.setText(s);
        int start = bi.first();
        for (int end = bi.next(); end != BreakIterator.DONE; start = end, end = bi.next()) {
            String key = s.substring(start, end);
            if (onlyPrintable && !isPrintable(key))
                continue;
            if (!caseDependent)
                key = key.toLowerCase();
            out.put(key, out.getOrDefault(key, 0) + 1);
        }
        return out;
    }

    // не лучшая проверка, но как есть
    private static boolean isPrintable(String s) {
        return s.codePoints().allMatch(cp -> !Character.isISOControl(cp) && cp != KeyEvent.CHAR_UNDEFINED);
    }

    // ! изменение мапы to является ожидаемым поведением
    private static void combine(HashMap<String, Integer> to, HashMap<String, Integer> from) {
        from.forEach((k, v) -> to.put(k, to.getOrDefault(k, 0) + v));
    }
}

class Filters{
    public static HashMap<String, Integer> onlyAlphabet(HashMap<String, Integer> data) {
        var p = Pattern.compile("[A-ZА-Яa-zа-я]");
        var out = new HashMap<String, Integer>();
        for (var entry : data.entrySet())
            if (p.matcher(entry.getKey()).matches())
                out.put(entry.getKey(), entry.getValue());
        return out;
    }
}