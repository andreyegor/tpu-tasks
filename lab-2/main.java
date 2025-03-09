import java.util.Arrays;

class MainLab2 {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    private static void writeTable(String[][] data) {
        assert data.length != 0;

        int[] widths = new int[data.length];
        int[] heights = new int[data[0].length];
        for (int i = 0; i < widths.length; i++)
            for (int j = 0; j < heights.length; j++) {
                String[] cell = data[i][j].split("\n"); // lines in cell
                heights[j] = cell.length;
                widths[i] = Arrays.stream(cell).map((s) -> s.length()).max(Integer::compare).orElseGet(null);// Inteher::compare?
                for (String line : cell) {
                    widths[i] = Math.max(widths[i], line.length());
                }
            }

        for (int i = 0; i < widths.length; i++) {
            for (int j = 0; j < heights.length; j++) {

            }
        }

    }
}