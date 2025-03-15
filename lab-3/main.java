import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import java.util.Collection;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.function.BooleanSupplier;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.TreeSet;

class MainLab3 {
    public static void main(String[] args) {
        String ArrayName = "Array";
        String ArrayListName = "ArrayList";
        String PersizedArrayListName = "Persized ArrayList";
        String LinkedListName = "LinkedList";
        String HashSetName = "HashSet";
        String TreeSetName = "TreeSet";

        int add2HeadSize = 200_000;
        int add2MidSize = 50_000;
        int add2EndSize = 3_000_000;
        int rmvFHeadSize = 200_000;
        int rmvFMidSize = 100_000;
        int rmvFEndSize = 20_000_000;
        int getAllSize = 30_000;

        String add2HeadLabel = String.format("Add %s to head", Table.beautifulInteger(add2HeadSize));
        String add2MidLabel = String.format("Add %s to Mid", Table.beautifulInteger(add2MidSize));
        String add2EndLabel = String.format("Add %s to End", Table.beautifulInteger(add2EndSize));
        String rmvFHeadLabel = String.format("Remove %s from Head", Table.beautifulInteger(rmvFHeadSize));
        String rmvFMidLabel = String.format("Remove %s from Mid", Table.beautifulInteger(rmvFMidSize));
        String rmvFEndLabel = String.format("Remove %s from End", Table.beautifulInteger(rmvFEndSize));
        String getAllLabel = String.format("Get All %s", Table.beautifulInteger(getAllSize));

        // .boxed() оборачивает IntStream в Stream
        List<Integer> rmvFHeadData = IntStream.range(0, rmvFHeadSize)
                .boxed().collect(Collectors.toList());
        List<Integer> rmvFMidData = IntStream.range(0, rmvFMidSize)
                .boxed().collect(Collectors.toList());
        List<Integer> rmvFEndData = IntStream.range(0, rmvFEndSize)
                .boxed().collect(Collectors.toList());
        List<Integer> getAllData = IntStream.range(0, getAllSize)
                .boxed().collect(Collectors.toList());

        Test add2Head = new Test(Map.of(
                ArrayListName,
                () -> TestList.add2Head(new ArrayList<Integer>(), add2HeadSize),
                PersizedArrayListName,
                () -> TestList.add2Head(new ArrayList<Integer>(add2HeadSize), add2HeadSize),
                LinkedListName,
                () -> TestList.add2Head(new LinkedList<Integer>(), add2HeadSize)));

        Test add2Mid = new Test(Map.of(
                ArrayListName,
                () -> TestList.add2MidByPlace(new ArrayList<Integer>(), add2MidSize),
                PersizedArrayListName,
                () -> TestList.add2MidByPlace(new ArrayList<Integer>(add2MidSize), add2MidSize),
                LinkedListName,
                () -> TestList.add2MidByPlace(new LinkedList<Integer>(), add2MidSize)));

        Test add2End = new Test(Map.of(
                ArrayName,
                () -> TestArray.add(new Integer[add2EndSize], add2EndSize),
                ArrayListName,
                () -> TestList.add2End(new ArrayList<Integer>(), add2EndSize),
                PersizedArrayListName,
                () -> TestList.add2End(new ArrayList<Integer>(add2EndSize), add2EndSize),
                LinkedListName,
                () -> TestList.add2End(new LinkedList<Integer>(), add2EndSize),
                HashSetName,
                () -> TestCollection.add(new HashSet<Integer>(), add2EndSize),
                TreeSetName,
                () -> TestCollection.add(new TreeSet<Integer>(), add2EndSize)));

        Test rmvFHead = new Test(Map.of(
                ArrayListName,
                () -> TestList.rmvFHead(new ArrayList<Integer>(List.copyOf(rmvFHeadData))),
                PersizedArrayListName,
                () -> TestList.rmvFHead(new ArrayList<Integer>(List.copyOf(rmvFHeadData))),
                LinkedListName,
                () -> TestList.rmvFHead(new LinkedList<Integer>(List.copyOf(rmvFHeadData)))));

        Test rmvFMid = new Test(Map.of(
                ArrayListName,
                () -> TestList.rmvFMidByPlace(new ArrayList<Integer>(List.copyOf(rmvFMidData))),
                PersizedArrayListName,
                () -> TestList.rmvFMidByPlace(new ArrayList<Integer>(List.copyOf(rmvFMidData))),
                LinkedListName,
                () -> TestList.rmvFMidByPlace(new LinkedList<Integer>(List.copyOf(rmvFMidData)))));

        Test rmvFEnd = new Test(Map.of(
                ArrayListName,
                () -> TestList.rmvFEnd(new ArrayList<Integer>(List.copyOf(rmvFEndData))),
                PersizedArrayListName,
                () -> TestList.rmvFEnd(new ArrayList<Integer>(List.copyOf(rmvFEndData))),
                LinkedListName,
                () -> TestList.rmvFEnd(new LinkedList<Integer>(List.copyOf(rmvFEndData))),
                HashSetName,
                () -> TestCollection.rmv(new HashSet<Integer>(List.copyOf(rmvFEndData))),
                TreeSetName,
                () -> TestCollection.rmv(new TreeSet<Integer>(List.copyOf(rmvFEndData)))));

        // TODO компилятор точно что-то наоптимизирует
        Test getAll = new Test(Map.ofEntries(
                entry(ArrayName,
                        () -> TestArray.getAll(rmvFHeadData.toArray(new Integer[getAllData.size()]))),
                entry(ArrayListName,
                        () -> TestList.getAll(new ArrayList<Integer>(List.copyOf(getAllData)))),
                entry(PersizedArrayListName,
                        () -> TestList.getAll(new ArrayList<Integer>(List.copyOf(getAllData)))),
                entry(LinkedListName,
                        () -> TestList.getAll(new LinkedList<Integer>(List.copyOf(getAllData)))),
                entry(HashSetName,
                        () -> TestCollection.getAll(new HashSet<Integer>(List.copyOf(getAllData)))),
                entry(TreeSetName,
                        () -> TestCollection.getAll(new TreeSet<Integer>(List.copyOf(getAllData))))));

        Table.fromTests(Map.ofEntries(
                entry(add2HeadLabel, add2Head),
                entry(add2MidLabel, add2Mid),
                entry(add2EndLabel, add2End),
                entry(rmvFHeadLabel, rmvFHead),
                entry(rmvFMidLabel, rmvFMid),
                entry(rmvFEndLabel, rmvFEnd),
                entry(getAllLabel, getAll)));
    }
}

class Test {
    Map<String, BooleanSupplier> testFunctions;
    Map<String, Integer> testResults;

    public Test(Map<String, BooleanSupplier> tests) {
        this.testFunctions = tests;
        this.run();
    }

    private void run() {
        testResults = testFunctions.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> timer(e.getValue())));
    }

    public Optional<Map<String, Integer>> getTestResults() {
        return Optional.of(testResults);
    }

    static private int timer(BooleanSupplier testFunction) {
        long t = System.currentTimeMillis();
        boolean isCorrect = testFunction.getAsBoolean();
        return isCorrect ? (int) (System.currentTimeMillis() - t) : -1;
    }
}

class TestList {
    // Чтобы уменьшить накладные расходы, передаём уже созданную структуру
    public static boolean add2Head(List<Integer> struct, int howMany) {
        return doNormal(struct, howMany, (List<Integer> s, Integer n) -> s.addFirst(n)) != 0;
    }

    public static boolean add2HeadByPlase(List<Integer> struct, int howMany) {
        assert struct.size() == howMany;
        return doReversed(struct, howMany, (List<Integer> s, Integer n) -> s.add(n, n)) != 0;
    }

    public static boolean add2MidByPlace(List<Integer> struct, int howMany) {
        return doNormal(struct, howMany, (List<Integer> s, Integer n) -> s.add(n / 2, n)) != 0;
    }

    public static boolean add2End(List<Integer> struct, int howMany) {
        return doNormal(struct, howMany, (List<Integer> s, Integer n) -> s.addLast(n)) != 0;
    }

    public static boolean add2EndByPlace(List<Integer> struct, int howMany) {
        assert struct.size() == howMany;
        return doNormal(struct, howMany, (List<Integer> s, Integer n) -> s.add(n, n)) != 0;
    }

    public static boolean rmvFHead(List<Integer> struct) {
        int howMany = struct.size();
        return doNormal(struct, howMany, (List<Integer> s, Integer n) -> s.removeFirst()) == 0;
    }

    public static boolean rmvFHeadByPlace(List<Integer> struct) {
        int howMany = struct.size();
        return doNormal(struct, howMany, (List<Integer> s, Integer n) -> s.remove(n)) == 0;
    }

    public static boolean rmvFMidByPlace(List<Integer> struct) {
        int howMany = struct.size();
        return doNormal(struct, howMany, (List<Integer> s, Integer n) -> s.remove(s.size() / 2)) == 0;
    }

    public static boolean rmvFEnd(List<Integer> struct) {
        int howMany = struct.size();
        return doNormal(struct, howMany, (List<Integer> s, Integer n) -> s.removeLast()) == 0;
    }

    public static boolean rmvFEndByPlace(List<Integer> struct) {
        int howMany = struct.size();
        return doReversed(struct, howMany, (List<Integer> s, Integer n) -> s.remove(n)) == 0;
    }

    public static boolean getAll(List<Integer> struct) {
        int howMany = struct.size();
        return doNormal(struct, howMany, (List<Integer> s, Integer n) -> s.get(n)) == howMany;
    }

    private static int doNormal(List<Integer> struct, int howMany,
            BiConsumer<List<Integer>, Integer> how) {
        return justDo(struct, howMany, how, false);
    }

    private static int doReversed(List<Integer> struct, int howMany,
            BiConsumer<List<Integer>, Integer> how) {
        return justDo(struct, howMany, how, true);
    }

    private static int justDo(List<Integer> struct, int howMany,
            BiConsumer<List<Integer>, Integer> how, boolean reverse) {
        int from = reverse ? howMany : 0;
        int to = reverse ? 0 : howMany;
        int by = reverse ? -1 : 1;
        for (int i = from; i < to; i += by)
            how.accept(struct, i);
        return struct.size();
    }
}

class TestCollection {
    public static boolean add(Collection<Integer> struct, int howMany) {
        return Do(struct, howMany, (Collection<Integer> s, Integer n) -> s.add(n)) != 0;
    }

    public static boolean rmv(Collection<Integer> struct) {
        int howMany = struct.size();
        return Do(struct, howMany, (Collection<Integer> s, Integer n) -> s.remove(n)) != 0;
    }

    public static boolean getAll(Collection<Integer> struct) {
        for (Integer e : struct) {
            if (!struct.contains(e))
                return false;
        }
        return true;
    }

    private static int Do(Collection<Integer> struct, int howMany,
            BiConsumer<Collection<Integer>, Integer> how) {
        for (int i = 0; i < howMany; i++)
            how.accept(struct, i);
        return struct.size();
    }
}

class TestArray {
    public static boolean add(Integer[] struct, Integer howMany) {
        assert struct.length == howMany;
        for (int i = 0; i < howMany; i++)
            struct[i] = i;
        return true;
    }

    public static boolean getAll(Integer[] struct) {
        for (int i = 0; i < struct.length; i++) {
            @SuppressWarnings("unused")
            Integer t = struct[i];
        }
        return true;
    }
}

class Table {
    public static void writeTable(String[][] data) {
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
            assert h == 1; // многосторчные ячейки не поддержаны, todo i guess, хотя скорее пока не todo

        String first_horizontal_delimiter = horizontalDelimiter('┌', '┬', '┐', widths) + '\n';
        String horizontal_delimiter = horizontalDelimiter('├', '┼', '┤', widths) + '\n';
        String last_horizontal_delimiter = horizontalDelimiter('└', '┴', '┘', widths) + '\n';
        String vertical_line_delimiter = "│";

        StringBuilder out = new StringBuilder(first_horizontal_delimiter);
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < widths.length; j++) {
                out.append(vertical_line_delimiter).append(center(data[i][j], widths[j]));
            }
            out.append(vertical_line_delimiter).append('\n');
            if (i != data.length - 1)
                out.append(horizontal_delimiter);
            else
                out.append(last_horizontal_delimiter);
        }
        System.out.println(out);
    }

    public static void fromTests(Map<String, Test> testsData) {
        writeTable(makeTable(testsData));
    }

    public static String[][] makeTable(Map<String, Test> testsData) {
        HashSet<String> columnNames = new HashSet<String>();
        for (Test test : testsData.values()) {
            columnNames.addAll(test.getTestResults().orElse(Map.of()).keySet());
        }
        String[] columnNamesSorted = columnNames.stream().sorted().toArray(String[]::new);
        String[] lineNamesSorted = testsData.keySet().stream().sorted().toArray(String[]::new);

        String[][] table = new String[lineNamesSorted.length + 1][columnNamesSorted.length + 1];
        for (int i = 0; i < columnNamesSorted.length; i++) {
            table[0][i + 1] = columnNamesSorted[i];
        }
        for (int j = 0; j < lineNamesSorted.length; j++) {
            table[j + 1][0] = lineNamesSorted[j];
        }
        table[0][0] = "";

        Map<String, Map<String, Integer>> testsDataResult = testsData.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getTestResults().orElse(Map.of())));

        for (int i = 0; i < columnNamesSorted.length; i++)
            for (int j = 0; j < lineNamesSorted.length; j++) {
                int res = testsDataResult.get(lineNamesSorted[j]).getOrDefault(columnNamesSorted[i], -1);
                table[j + 1][i + 1] = res == -1 ? "" : beautifulInteger(res);
            }

        return table;
    }

    public static String beautifulInteger(Integer n) {
        String ns = n.toString();
        int ln = ns.length();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < ln; i++) {
            out.append(ns.charAt(i));
            if ((ln - i) % 3 == 0)
                out.append('_');
        }
        return out.toString();
    }

    private static String center(String s, int w){
        return " ".repeat((w - s.length()) / 2) + s + " ".repeat(w - s.length() - (w - s.length()) / 2);
    }

    private static String horizontalDelimiter(char split_left, char split, char split_right, int[] widths) {
        String line = Arrays.stream(widths).mapToObj(w -> split + "─".repeat(w)).collect(Collectors.joining());
        return split_left + line.substring(1) + split_right;
    }
}