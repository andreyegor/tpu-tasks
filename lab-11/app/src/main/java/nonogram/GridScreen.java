package nonogram;

public interface GridScreen {
    public void blank(Integer ox, Integer oy);

    public void setOnCellClick(java.util.function.BiConsumer<Integer, Integer> handler);

    public void set(Integer x, Integer y, Boolean state);

    public void hint(Integer x, Integer y, Integer hint);
}
