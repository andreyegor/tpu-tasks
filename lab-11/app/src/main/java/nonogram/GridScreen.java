package nonogram;

import java.util.function.BiConsumer;

public interface GridScreen {
    public void blank(int ox, int oy);

    public void setOnCellClick(BiConsumer<Integer, Integer> mainHandler, BiConsumer<Integer, Integer> altHandler);

    public void set(int x, int y, boolean state);

    public void setLock(int x, int y, boolean state);

    public void hint(int x, int y, int hint);
}
