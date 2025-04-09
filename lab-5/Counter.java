// BufferedReader лучше чем stream подходит для работы с длинными строками,
// и соответственно текстами из-за большего размера буфера

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.BreakIterator;
import java.util.HashMap;

class Counter {
    // Используется String, так как не любой unicode символ влезет в char
    public static Count count(Reader r) throws IOException {
        var br = new BufferedReader(r);
        var out = new HashMap<String, Integer>();
        String line = null;
        while ((line = br.readLine()) != null) {
            out.put("\n", out.getOrDefault("\n", 0) + 1);
            combine(out, countLine(line));
        }
        out.put("\n", out.getOrDefault("\n", 1) - 1);
        return new Count(out);
    }

    private static HashMap<String, Integer> countLine(String s) {
        var out = new HashMap<String, Integer>();
        BreakIterator bi = BreakIterator.getCharacterInstance();
        bi.setText(s);
        int start = bi.first();
        for (int end = bi.next(); end != BreakIterator.DONE; start = end, end = bi.next()) {
            String key = s.substring(start, end);
            out.put(key, out.getOrDefault(key, 0) + 1);
        }
        return out;
    }

    public static class Count {
        HashMap<String, Integer> cnt;
        private boolean caseIndependent;
        private boolean printDependent;

        private Count(HashMap<String, Integer> cnt) {
            this(cnt, false, false);
        }

        private Count(HashMap<String, Integer> cnt, boolean caseIndependent, boolean printDependent) {
            this.cnt = new HashMap<>(cnt);
            this.caseIndependent = caseIndependent;
            this.printDependent = printDependent;
        }

        public HashMap<String, Integer> getCnt() {
            return new HashMap<>(cnt);
        }

        public Count makeCaseIndependent() {
            if (this.caseIndependent)
                return new Count(cnt, caseIndependent, printDependent);
            var out = new HashMap<String, Integer>();
            for (var entry : cnt.entrySet())
                out.put(entry.getKey(), out.getOrDefault(entry.getKey(), 0) + entry.getValue());
            return new Count(out, true, printDependent);
        }

        public Count makePrintDependent() {
            if (this.printDependent)
                return new Count(cnt, caseIndependent, printDependent);
            var out = new HashMap<String, Integer>();
            for (var entry : cnt.entrySet())
                if (isPrintable(entry.getKey()))
                    out.put(entry.getKey(), entry.getValue());
            return new Count(out, caseIndependent, true);
        }

        public boolean isCaseIndependent() {
            return this.caseIndependent;
        }

        public boolean isPrintDependent() {
            return this.printDependent;
        }

        // не лучшая проверка, но как есть
        private static boolean isPrintable(String s) {
            return s.codePoints().allMatch(cp -> !Character.isISOControl(cp) && cp != KeyEvent.CHAR_UNDEFINED);
        }
    }

    // ! изменение мапы to является ожидаемым поведением
    private static void combine(HashMap<String, Integer> to, HashMap<String, Integer> from) {
        from.forEach((k, v) -> to.put(k, to.getOrDefault(k, 0) + v));
    }
}