import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Plot {
    public static String generate(HashMap<String, Integer> data,
            boolean isHorisontal, boolean sortDescending, boolean sortByCount) {
        var sortedData = sortByCount ? sortByCount(data, sortDescending) : sortAlphabetically(data, sortDescending);
        return isHorisontal ? generateHorisontal(sortedData, 20) : generateVertical(sortedData, 80);
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

        double valueDivider = (maxCount / (maxBarWidth - 1));
        for (Map.Entry<String, Integer> lineData : data)
            sb.append(String.format(lineTemplate, lineData.getKey(), lineData.getValue(),
                    "█".repeat(1 + (int) (lineData.getValue() / valueDivider))));

        sb.append(horisontalBorder('└', '┴', '┘', maxLabelWidth, maxBarWidth));
        assert Arrays.stream(sb.toString().split("\n")).allMatch(s -> s.length() == width);
        return sb.toString();
    }

    private static String generateHorisontal(ArrayList<Map.Entry<String, Integer>> data, int height) {
        int maxCount = data.stream().map(e -> e.getValue()).max((a, b) -> a - b).orElse(0);
        int maxCountWidth = String.valueOf(maxCount).length();
        int maxBarHeight = height - 3;

        var spreadedCounts = new int[maxBarHeight];
        for (int i = 0; i < maxBarHeight; i++)
            spreadedCounts[i] = (int) Math.ceil(maxCount * (i + 1) / (double) maxBarHeight);

        var spreadedCountsText = new String[maxBarHeight];
        spreadedCountsText[maxBarHeight - 1] = String.valueOf(spreadedCounts[maxBarHeight - 1]);
        for (int i = maxBarHeight - 2; i >= 0; i--) {
            if (spreadedCounts[i] == spreadedCounts[i + 1])
                spreadedCountsText[i] = "";
            else
                spreadedCountsText[i] = String.valueOf(spreadedCounts[i]);
        }

        var halfLineTemplate = String.format("│%%%ds│", maxCountWidth);

        var sb = new StringBuilder();
        sb.append(horisontalBorder('┌', '┬', '┐', String.format(halfLineTemplate, "").length(), data.size()));
        for (int i = maxBarHeight - 1; i >= 0; i--) {
            sb.append(String.format(halfLineTemplate, spreadedCountsText[i]));
            for (Map.Entry<String, Integer> entry : data)
                sb.append((i == 0 || spreadedCounts[i] <= entry.getValue()) ? "█" : " ");
            sb.append("│\n");
        }

        sb.append(String.format(halfLineTemplate, ""));
        data.forEach(e -> sb.append(e.getKey()));
        sb.append("│\n");
        sb.append(horisontalBorder('└', '┴', '┘', String.format(halfLineTemplate, "").length(), data.size()));

        return sb.toString();
    }

    private static ArrayList<Map.Entry<String, Integer>> sortByCount(HashMap<String, Integer> data, boolean reverse) {
        int k = reverse ? -1 : 1;
        var enteries = new ArrayList<>(data.entrySet());
        enteries.sort((a, b) -> {
            int cmp = b.getValue() - a.getValue();
            return k * (cmp == 0 ? a.getKey().compareTo(b.getKey()) : cmp);
        });
        return enteries;
    }

    private static ArrayList<Map.Entry<String, Integer>> sortAlphabetically(HashMap<String, Integer> data,
            boolean reverse) {
        int k = reverse ? -1 : 1;
        var enteries = new ArrayList<>(data.entrySet());
        enteries.sort((a, b) -> k * a.getKey().compareTo(b.getKey()));
        return enteries;
    }

    private static String horisontalBorder(char l, char m, char r, int lw, int rw) {
        return l + "─".repeat(lw - 2) + m + "─".repeat(rw) + r + '\n';
    }
}