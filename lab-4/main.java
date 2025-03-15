import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.io.FileNotFoundException;
import java.awt.event.KeyEvent;

import java.util.HashMap;

class MainLab4 {
    public static void main(String[] args) {
        System.out.println(java.nio.charset.Charset.defaultCharset());
        String fileName = "java-course/lab-4/sample.txt";
        HashMap<String, Long> cnt;
        try {
            cnt = openAndCount(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            return;
        } catch (IOException e) {
            System.out.println("Что-то пошло не так при чтении файла");
            return;
        }
        System.out.println(cnt.toString());
    }
    
    private static HashMap<String, Long> openAndCount(String path) throws IOException {
        var br = new BufferedReader(new FileReader(path));
        var out = Counter.count(br);
        br.close();
        return out;

    }
}

// BufferedReader лучше чем stream подходит для работы с длинными строками,
// и соответственно текстами из-за большего размера буфера
class Counter {
    // Используется String, так как не любой unicode символ влезет в char
    public static HashMap<String, Long> count(BufferedReader br) throws IOException {
        return count(br, true, false);
    }

    public static HashMap<String, Long> count(BufferedReader br, boolean onlyPrintable, boolean caseDependent)
            throws IOException {
        var out = new HashMap<String, Long>();
        String line = null;
        while ((line = br.readLine()) != null) {
            combine(out, count(line, onlyPrintable, caseDependent));
        }
        return out;
    }

    public static HashMap<String, Long> count(String s, boolean onlyPrintable, boolean caseDependent) {
        var out = new HashMap<String, Long>();
        BreakIterator bi = BreakIterator.getCharacterInstance();
        bi.setText(s);
        int start = bi.first();
        for (int end = bi.next(); end != BreakIterator.DONE; start = end, end = bi.next()) {
            String key = s.substring(start, end);
            if (onlyPrintable && !isPrintable(key))
                continue;
            if (!caseDependent)
                key = key.toLowerCase();
            out.put(key, out.getOrDefault(key, 0L) + 1);
        }
        return out;
    }

    // не лучшая проверка, но как есть
    private static boolean isPrintable(String s) {
        return s.codePoints().allMatch(cp -> !Character.isISOControl(cp) && cp != KeyEvent.CHAR_UNDEFINED);
    }

    // ! изменение мапы to является ожидаемым поведением
    private static void combine(HashMap<String, Long> to, HashMap<String, Long> from) {
        from.forEach((k, v) -> to.put(k, to.getOrDefault(k, 0L) + v));
    }
}