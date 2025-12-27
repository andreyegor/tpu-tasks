package lab.core.model;

import java.util.Iterator;

public class Grid<T> implements Iterable<T> {
    T[][] grid;

    @SuppressWarnings("unchecked")
    public Grid(int xSize, int ySize) {
        grid = (T[][]) new Object[xSize][ySize];
    }

    @SuppressWarnings("unchecked")
    public Grid(int xSize, int ySize, java.util.function.Supplier<T> defaultValue) {
        grid = (T[][]) new Object[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                grid[i][j] = defaultValue.get();
            }
        }
    }

    public T get(Pos pos) {
        return grid[pos.x()][pos.y()];
    }

    public void set(Pos pos, T value) {
        grid[pos.x()][pos.y()] = value;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<Pos> positions = iteratePos().iterator();
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return positions.hasNext();
            }

            @Override
            public T next() {
                return get(positions.next());
            }
        };
    }

    public Iterable<Pos> iteratePos() {
        return () -> new Iterator<Pos>() {
            private int x = 0;
            private int y = 0;

            @Override
            public boolean hasNext() {
                return x < grid.length && y < grid[x].length;
            }

            @Override
            public Pos next() {
                Pos p = new Pos(x, y);
                if (y < grid[x].length - 1) {
                    y++;
                } else {
                    y = 0;
                    x++;
                }
                return p;
            }
        };
    }
}
