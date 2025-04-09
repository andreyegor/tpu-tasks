import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Writer;
import java.io.FileWriter;

class Main {
    private static final String FILE_NOT_FOUND_MSG = "По пути %s файл не найден.\n";
    private static final String FILES_NOT_FOUND_MSG = "Ни один файл не найден.\n";
    private static final String CANT_START_WRITE_MSG = "Не получилось начать запись данных.\n";
    private static final String CANT_READ_MSG = "Не получилось начать чтение данных.\n";
    private static final String CANT_WRITE_MSG = "Что-то пошло не так во время записи данных.\n";
    private static final String CANT_PARSE_MSG = "Не получилось прочитать аргументы командной строки:\n%s";
    private static final String CANT_CLOSE_MSG = "Что-то пошло не так во время закрытия файла.\n";
    private static final String SHOW_ALL_HORISONTAL_MSG = "Невозможно выровнять горизонтальный график если включено отображение невидимых символов, используйте вертикальный график.\n";

    private static final String SORT_BY_COUNT_FLG = "-count";
    private static final String SORT_DESCEDENTING_FLG = "-desk";
    private static final String HORISONTAL_PLOT_FLG = "-horizontal";
    private static final String SHOW_ALL_SYMBOLS_FLG = "-all";
    private static final String IGNORE_CASE_FLG = "-ignoreCase";
    private static final String DATA_OUTPUT_FLG = "-out";

    public static void main(String[] args) {
        run(args);
    }

    private static void run(String[] args) {
        var cli = new CliIn.Parser(
                new String[] { SORT_BY_COUNT_FLG, SORT_DESCEDENTING_FLG, HORISONTAL_PLOT_FLG, SHOW_ALL_SYMBOLS_FLG,
                        IGNORE_CASE_FLG },
                new String[] { DATA_OUTPUT_FLG });
        var parsed = cli.parse(args).build();
        if (!parsed.getStatus()) {
            System.out.print(String.format(CANT_PARSE_MSG, parsed.getMessage()));
            return;
        }
        var booleanFlags = parsed.getBooleanFlags();
        var inputFlags = parsed.getInputFlags();
        var noFlags = parsed.getNoFlags();
        if (booleanFlags.get(SHOW_ALL_SYMBOLS_FLG) && booleanFlags.get(HORISONTAL_PLOT_FLG)) {
            System.out.print(SHOW_ALL_HORISONTAL_MSG);
        }

        RichWriter rw;
        try {
            Writer writer;
            if (inputFlags.get(DATA_OUTPUT_FLG) != "")
                writer = new FileWriter(inputFlags.get(DATA_OUTPUT_FLG));
            else
                writer = new java.io.OutputStreamWriter(System.out);

            if (booleanFlags.get(HORISONTAL_PLOT_FLG))
                rw = new HorizontalPlotWriter(writer);
            else
                rw = new VerticalPlotWriter(writer);
        } catch (IOException e) {
            System.out.print(CANT_START_WRITE_MSG);
            return;
        }

        try {
            var rr = new RichReader();
            for (String path : noFlags) {
                try {
                    var fileReader = new FileReader(path);
                    rr.add(fileReader);
                } catch (FileNotFoundException e) {
                    rw.write(String.format(FILE_NOT_FOUND_MSG, path));
                }
            }
            if (rr.size() == 0) {
                rw.write(FILES_NOT_FOUND_MSG);
                return;
            }

            Counter.Count cnt;
            try {
                cnt = Counter.count(rr);
            } catch (IOException e) {
                rw.write(CANT_READ_MSG);
                return;
            } finally {
                try {
                    rr.close();
                } catch (IOException e) {
                    rw.write(CANT_CLOSE_MSG);
                }
            }

            if (!booleanFlags.get(SHOW_ALL_SYMBOLS_FLG)) {
                cnt = cnt.makePrintDependent();
            }
            if (booleanFlags.get(IGNORE_CASE_FLG)) {
                cnt = cnt.makeCaseIndependent();
            }
            rw.writePlot(cnt, booleanFlags.get(SORT_DESCEDENTING_FLG), booleanFlags.get(SORT_BY_COUNT_FLG));
        } catch (IOException e) {
            System.out.println(CANT_CLOSE_MSG);
            return;
        } finally {
            try {
                rw.close();
            } catch (IOException e) {
                System.out.println(CANT_WRITE_MSG);
            }
        }
    }
}
