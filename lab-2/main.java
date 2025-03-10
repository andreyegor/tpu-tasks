import java.util.Arrays;
import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;

class MainLab2 {
    public static void main(String[] args) {
        int a, b, c, x_from, x_to, table_size, steps_count;
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите коэффиценты уравнения ax^2+bx+c:");
        System.out.print("a = ");
        a = sc.nextInt();
        System.out.print("b = ");
        b = sc.nextInt();
        System.out.print("c = ");
        c = sc.nextInt();

        System.out.println("интервал интегрирования функции:");
        System.out.print("от ");
        x_from = sc.nextInt();
        System.out.print("и до ");
        x_to = sc.nextInt();

        System.out.print("размер таблицы: ");
        table_size = sc.nextInt();

        System.out.print("и число разбиений: ");
        steps_count = sc.nextInt();
        sc.close();

        // a = 1;
        // b = 0;
        // c = -3;
        // x_from = -5;
        // x_to = 5;
        // table_size = 11;
        // steps_count = 100000;

        double round_factor = 10000; // 5 after point
        double[][] table = tableOfValues((x) -> a * x * x + b * x + c, x_from, x_to, table_size, steps_count);
        double[][] table_rounded = Arrays.stream(table)
                .map(line -> Arrays.stream(line).map(n -> (Math.round(n * round_factor) / round_factor)).toArray())
                .toArray(double[][]::new);
        String[][] string_table = Arrays.stream(table_rounded)
                .map(line -> Arrays.stream(line)
                        .mapToObj(Double::toString)
                        .toArray(String[]::new))
                .toArray(String[][]::new);

        writeTable(string_table);
    }

    private static double[][] tableOfValues(DoubleUnaryOperator f, int from, int to, int table_size,
            int integration_steps) {
        double step = ((double) (to - from)) / table_size + to > 0 && from < 0 ? 1 : 0;
        ;

        double[][] out = new double[table_size + 1][table_size + 1];
        out[0][0] = 0;
        for (int i = 0; i < table_size; i++) {
            out[0][i + 1] = from + step * i;
            out[i + 1][0] = from + step * i;
        }

        for (int i = 0; i < table_size; i++)
            for (int j = 0; j < table_size; j++) {
                // TODO ?
                out[i + 1][j + 1] = integration(f, from + step * i, from + step * j, integration_steps);
            }
        return out;
    }

    // Принимает функциональный(!) интерфейс как параметр,
    // https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
    private static double integration(DoubleUnaryOperator f, double from, double to, int steps) {
        double step = (to - from) / steps;// тут ошибка будет наверное
        double out = 0;
        for (int i = 0; i < steps; i++) {
            double xf = from + i * step;
            double xt = from + (i + 1) * step;
            double yf = f.applyAsDouble(xf);
            double yt = f.applyAsDouble(xt);

            out += (yf + yt) * step / 2;
        }
        return out;

    }
    // Фунция интегрирования написанная согласно условию задачи.
    // В решении использую вместо неё более, по моему мнению, корректную.
    // private static double integration(int a, int b, int c, int from, int to, int steps) {
    //     double step = (to - from) / steps + to > 0 && from < 0 ? 1 : 0;
    //     double out = 0;
    //     for (int i = 0; i < steps; i++) {
    //         double xf = from + i * step;
    //         double xt = from + (i + 1) * step;
    //         double yf = a * xf * xf + b * xf + c;
    //         double yt = a * xt * xt + b * xt + c;

    //         out += (yf + yt) * step / 2;
    //     }
    //     return out;
    // }

    // private static double exactIntegration(int a, int b, int c, int x_from, int
    // x_to, int steps_count) {
    // Если нужна идеальная точность, реализовать и испоьзовать Rational,
    // отсутствует в stdlib
    // }

    private static void writeTable(String[][] data) {
        assert data.length != 0;

        int[] widths = new int[data[0].length];
        int[] heights = new int[data.length];
        for (int i = 0; i < heights.length; i++)
            for (int j = 0; j < widths.length; j++) {
                String[] cell = data[i][j].split("\n"); // lines in cell
                widths[j] = Math.max(widths[j],
                        Arrays.stream(cell).map(String::length).max(Integer::compare).orElse(0));
                heights[i] = Math.max(heights[i], cell.length);
            }

        for (int h : heights)
            assert h == 1; // многосторчные ячейки не поддержаны, TODO i guess, хотя скорее пока не TODO

        String first_horizontal_delimiter = horizontalDelimiter('┌', '┬', '┐', widths) + '\n';
        String horizontal_delimiter = horizontalDelimiter('├', '┼', '┤', widths) + '\n';
        String last_horizontal_delimiter = horizontalDelimiter('└', '┴', '┘', widths) + '\n';
        String vertical_line_delimiter = "│";

        String[] cell_format = Arrays.stream(widths)
                .mapToObj((w) -> String.format("%%%ds", w)).toArray(String[]::new);
        StringBuilder out = new StringBuilder(first_horizontal_delimiter);
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < widths.length; j++) {
                out.append(vertical_line_delimiter).append(String.format(cell_format[j], data[i][j]));
            }
            out.append(vertical_line_delimiter).append('\n');
            if (i != data.length - 1)
                out.append(horizontal_delimiter);
            else
                out.append(last_horizontal_delimiter);
        }
        System.out.println(out);
    }

    private static String horizontalDelimiter(char split_left, char split, char split_right, int[] widths) {
        String line = Arrays.stream(widths).mapToObj(w -> split + "─".repeat(w)).collect(Collectors.joining());
        return split_left + line.substring(1) + split_right;
    }
}