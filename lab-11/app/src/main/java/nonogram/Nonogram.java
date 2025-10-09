package nonogram;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntPredicate;

public class Nonogram {
    protected GridScreen screen;
    protected BitSet image = new BitSet(0);
    protected Integer ox = 0, oy = 0;
    protected Integer oxHintsShift = 0, oyHintsShift = 0;

    public Nonogram(GridScreen screen) {
        this.screen = screen;
    }

    protected void create(BitSet image, Integer ox, Integer oy) {
        this.ox = ox;
        this.oy = oy;
        this.image = (BitSet) image.clone();

    }

    public void swap(int x, int y) {
        var newState = !state(x, y);
        set(x, y, newState);
        screen.set(oxHintsShift+x, oyHintsShift+y, newState);
    }

    protected boolean state(int x, int y) {
        return image.get(y * ox + x);
    }

    protected void set(int x, int y, boolean state) {
        image.set(y * ox + x, state);
    }

    protected List<Integer> countContinuous(int size, IntPredicate filled) {
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
        return res;
    }

    protected void drawHints(
            List<List<Integer>> hints,
            int primaryShift,
            BiFunction<Integer, Integer, Integer> screenXFunc,
            BiFunction<Integer, Integer, Integer> screenYFunc) {
        for (int line = 0; line < hints.size(); line++) {
            List<Integer> list = hints.get(line);
            if (list == null || list.isEmpty())
                continue;
            int hintCount = list.size();
            int start = primaryShift - hintCount;
            for (int i = 0; i < hintCount; i++) {
                int pos = start + i;
                int sx = screenXFunc.apply(line, pos);
                int sy = screenYFunc.apply(line, pos);
                screen.hint(sx, sy, list.get(i));
            }
        }
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(ox != null ? ox : 0).append(" ");
        sb.append(oy != null ? oy : 0).append(" ");
        long[] arr = image.toLongArray();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1)
                sb.append(" ");
        }
        return sb.toString();
    }

    public void deserialize(String s) {
        String[] tokens = s.split(" ");
        if (tokens.length < 2)
            throw new IllegalArgumentException();
        Integer ox = Integer.parseInt(tokens[0]);
        Integer oy = Integer.parseInt(tokens[1]);
        long[] arr = new long[Math.max(0, tokens.length - 2)];
        for (int i = 2; i < tokens.length; i++) {
            arr[i - 2] = Long.parseLong(tokens[i]);
        }
        BitSet image = BitSet.valueOf(arr);
        create(image, ox, oy);
    }
}
