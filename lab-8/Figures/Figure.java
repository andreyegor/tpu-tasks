package Figures;

public interface Figure {
    public Figure move(Vector to);

    public void draw();

    public boolean equals(Object other);

    public int hashCode();
}
