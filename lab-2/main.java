import java.util.Arrays;
import java.util.stream.Collectors;

class MainLab2 {
    public static void main(String[] args) {
        writeTable(new String[][] { { "1", "2", "3" }, { "4", "56", "789" } });
    }

    private static void writeTable(String[][] data) {
        assert data.length != 0;

        int[] widths = new int[data[0].length];
        int[] heights = new int[data.length];
        for (int i = 0; i < heights.length; i++)
            for (int j = 0; j < widths.length; j++) {
                String[] cell = data[i][j].split("\n"); // lines in cell
                widths[j] = Arrays.stream(cell).map((s) -> s.length()).max(Integer::compare).orElseGet(null);// Inteher::compare?
                heights[i] = Math.max(heights[i], cell.length);
            }

        for (int h : heights)
            assert h == 1; // многосторчные ячейки не поддержаны, TODO i guess

        String first_horisontal_delimiter = generateHorisontalDelimiter('┌', '┬', '┐', widths) + '\n';
        String horisontal_delimiter = generateHorisontalDelimiter('├', '┼', '┤', widths) + '\n';
        String last_horisontal_delimiter = generateHorisontalDelimiter('└', '┴', '┘', widths) + '\n';
        String vertical_line_delimiter = "│";

        String[] textCellFormat = Arrays.stream(widths)
                .mapToObj((w) -> String.format("%%%ds", w)).toArray(String[]::new);
        StringBuilder out = new StringBuilder(first_horisontal_delimiter);
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < widths.length; j++) {
                out.append(vertical_line_delimiter + String.format(textCellFormat[j], data[i][j]));
            }
            out.append(vertical_line_delimiter + '\n');
            if (i != data.length - 1)
                out.append(horisontal_delimiter);
            else
                out.append(last_horisontal_delimiter);
        }
        System.out.println(out);
    }

    private static String generateHorisontalDelimiter(char split_left, char split, char split_right, int[] widths) {
        String line = Arrays.stream(widths).mapToObj(w -> split + "─".repeat(w)).collect(Collectors.joining())
                .substring(0);
        return split_left + line.substring(1) + split_right;
    }
}