package Figures;
// Периметр существует только у замкнутых фигур
public interface CloseFigure extends ScalableFigure {
    public double getPerimeter();

    public double getArea();

}