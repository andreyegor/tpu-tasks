package nonogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import javafx.util.Pair;

public class Nonogram implements Cloneable {
    protected BitGrid nonogram;
    protected Hints hints;

    public Nonogram(BitGrid nonogram) {
        if (nonogram == null) {
            throw new IllegalArgumentException("nongarm can not be empty");
        }
        this.nonogram = (BitGrid) nonogram.clone();

        int ox = nonogram.getOx(), oy = nonogram.getOx();
        this.hints = new Hints(new int[ox][(int) Math.ceil(oy / 2.0)], new int[oy][(int) Math.ceil(ox / 2.0)]);

        IntStream.range(0, ox).forEach(i -> updateHints(i, 0));
        IntStream.range(0, oy).forEach(i -> updateHints(0, i));
    }

    public Pair<int[], int[]> set(int x, int y, boolean state) {
        nonogram.set(x, y, state);
        updateHints(x, y);
        return new Pair<int[], int[]>(trimTrailingZeros(hints.ox[x]), trimTrailingZeros(hints.oy[y]));
    }

    public boolean get(int x, int y) {
        return nonogram.get(x, y);
    }

    public Pair<Integer, Integer> getLongestHint() {
        Function<int[][], Integer> findMax = (m -> Arrays
                .stream(m)
                .mapToInt(r -> IntStream.range(0, r.length)
                        .filter(i -> r[i] != 0)
                        .max().orElse(-1))
                .max().orElse(-1));

        return new Pair<Integer, Integer>(findMax.apply(hints.ox), findMax.apply(hints.oy));
    }

    public int getOx() {
        return nonogram.getOx();
    }

    public int getOy() {
        return nonogram.getOy();
    }

    public Pair<int[][], int[][]> getHints() {
        Function<int[][], int[][]> trimAllTrailingZeros = m -> Arrays.stream(m)
                .map(r -> trimTrailingZeros(r))
                .toArray(int[][]::new);

        return new Pair<int[][], int[][]>(
                trimAllTrailingZeros.apply(hints.ox),
                trimAllTrailingZeros.apply(hints.oy));
    }

    public int cardinality() {
        return nonogram.cardinality();
    }

    public String serialize() {
        return nonogram.serialize();
    }

    public static Nonogram deserialize(String s) {
        return new Nonogram(BitGrid.deserialize(s));
    }

    protected void updateHints(int x, int y) {
        if (hints == null)
            return;
        var row = countContinuous(nonogram.getOx(), i -> nonogram.get(i, y));
        var col = countContinuous(nonogram.getOy(), i -> nonogram.get(x, i));

        hints.oy[y] = new int[hints.oy[y].length];
        hints.ox[x] = new int[hints.ox[x].length];

        IntStream.range(0, row.length).forEach(i -> hints.oy[y][i] = row[i]);
        IntStream.range(0, col.length).forEach(i -> hints.ox[x][i] = col[i]);
    }

    private int[] trimTrailingZeros(int[] m) {
        int l = IntStream
                .iterate(m.length - 1, i -> i >= 0, i -> i - 1)
                .filter(i -> m[i] != 0).findFirst().orElse(-1);
        return l == -1 ? new int[0] : Arrays.copyOfRange(m, 0, l + 1);
    }

    private int[] countContinuous(int size, IntPredicate filled) {
        List<Integer> res = new ArrayList<>();
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            if (filled.test(i)) {
                cnt++;
            } else if (cnt > 0) {
                res.add(cnt);
                cnt = 0;
            }
        }
        if (cnt > 0)
            res.add(cnt);
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    public Nonogram clone() {
        try {
            return (Nonogram) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("0_0", e);
        }
    }

    private record Hints(int[][] ox, int[][] oy) implements Cloneable {
        public Hints clone() {
            try {
                return (Hints) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("0_0", e);
            }
        }
    }
}
