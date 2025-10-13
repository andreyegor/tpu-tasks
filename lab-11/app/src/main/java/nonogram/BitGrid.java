package nonogram;

import java.util.BitSet;

public class BitGrid implements Cloneable {
    private BitSet grid;
    private int ox, oy;

    public BitGrid(BitSet grid, int ox, int oy) {
        if (grid == null || grid.length() > ox * oy) {
            throw new IllegalArgumentException("Grid is corrupted");
        }
        this.ox = ox;
        this.oy = oy;
        this.grid = (BitSet) grid.clone();
    }

    public BitGrid(int ox, int oy) {
        this(new BitSet(), ox, oy);
    }

    public void set(int x, int y, boolean state) {
        grid.set(y * ox + x, state);
    }

    public boolean get(int x, int y) {
        return grid.get(y * ox + x);
    }

    public int getOx() {
        return ox;
    }

    public int getOy() {
        return oy;
    }

    public int cardinality() {
        return grid.cardinality();
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(ox).append(" ").append(oy).append(" ");
        long[] arr = grid.toLongArray();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1)
                sb.append(" ");
        }
        return sb.toString();
    }

    public static BitGrid deserialize(String s) {
        String[] tokens = s.split(" ");
        if (tokens.length < 2)
            throw new IllegalArgumentException();
        Integer ox = Integer.parseInt(tokens[0]);
        Integer oy = Integer.parseInt(tokens[1]);
        long[] arr = new long[Math.max(0, tokens.length - 2)];
        for (int i = 2; i < tokens.length; i++) {
            arr[i - 2] = Long.parseLong(tokens[i]);
        }
        return new BitGrid(BitSet.valueOf(arr), ox, oy);
    }

    public BitGrid clone() {
        try {
            return (BitGrid) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("0_0", e);
        }
    }
}
