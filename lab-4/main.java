import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.io.FileNotFoundException;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

class MainLab4 {
    public static void main(String[] args) {
        System.out.println(java.nio.charset.Charset.defaultCharset());
        String fileName = "java-course/lab-4/sample.txt";
        HashMap<String, Integer> data;
        try {
            data = openAndCount(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            return;
        } catch (IOException e) {
            System.out.println("Что-то пошло не так при чтении файла");
            return;
        }

        System.out.println(Plot.generate(data, false, false, true));
    }

    private static HashMap<String, Integer> openAndCount(String path) throws IOException {
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

class Plot {
    public static String generate(HashMap<String, Integer> data,
            boolean isHorisontal, boolean sortDescending, boolean sortByCount) {
        var sortedData = sortByCount ? sortByCount(data, sortDescending) : sortAlphabetically(data, sortDescending);
        return isHorisontal ? "" : generateVertical(sortedData, 80);
    }

    private static String generateVertical(ArrayList<Map.Entry<String, Integer>> data, int width) {
        int maxCount = data.stream().map(e -> e.getValue()).max((a, b) -> a - b).orElse(0);
        int maxCountWidth = String.valueOf(maxCount).length();
        var lineTemplate = String.format("│%%2s: %%-%dd │", maxCountWidth);
        int maxLabelWidth = String.format(lineTemplate, "", 0, "").length();
        int maxBarWidth = width - maxLabelWidth - 1;
        lineTemplate = lineTemplate + String.format("%%-%ds│\n", maxBarWidth);

        var sb = new StringBuilder();
        sb.append(horisontalBorder('┌', '┬', '┐', maxLabelWidth, maxBarWidth));

        double valueDivider = (maxCount/(maxBarWidth - 1));
        for (Map.Entry<String, Integer> lineData : data)
            sb.append(String.format(lineTemplate, lineData.getKey(), lineData.getValue(),
                    "█".repeat(1 + (int) (lineData.getValue() / valueDivider))));

        sb.append(horisontalBorder('└', '┴', '┘', maxLabelWidth, maxBarWidth));
        assert Arrays.stream(sb.toString().split("\n")).allMatch(s -> s.length() == width);
        return sb.toString();
    }

    private static ArrayList<Map.Entry<String, Integer>> sortByCount(HashMap<String, Integer> data, boolean reverse) {
        int k = reverse ? -1 : 1;
        var enteries = new ArrayList<>(data.entrySet());
        enteries.sort((a, b) -> {
            int cmp = b.getValue()- a.getValue();
            return k * (cmp == 0 ? a.getKey().compareTo(b.getKey()) : cmp);
        });
        return enteries;
    }

    private static ArrayList<Map.Entry<String, Integer>> sortAlphabetically(HashMap<String, Integer> data,
            boolean reverse) {
        int k = reverse ? -1 : 1;
        var enteries = new ArrayList<>(data.entrySet());
        enteries.sort((a, b) -> k*a.getKey().compareTo(b.getKey()));
        return enteries;
    }

    private static String horisontalBorder(char l, char m, char r, int lw, int rw){
        return l + "─".repeat(lw-2) + m + "─".repeat(rw)+r+'\n';
    }
}