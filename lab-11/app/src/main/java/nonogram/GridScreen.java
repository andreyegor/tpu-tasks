package nonogram;

public interface GridScreen {
    public void blank(Integer ox, Integer oy);

    public void setOnCellClick(java.util.function.BiConsumer<Integer, Integer> handler);

    public void set(Integer x, Integer y);

    public void unset(Integer x, Integer y);

    public void hint(Integer x, Integer y, Integer hint);
}
