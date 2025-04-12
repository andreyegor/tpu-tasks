package Figures;

interface Figure {
    public Figure move(Vector to);

    public boolean equals(Object other);

    public int hashCode();
}

interface ScalableFigure extends Figure {
    public ScalableFigure scale(double factor);

}

// Периметр существует только у замкнутых фигур
interface ClosedFigure extends ScalableFigure {
    public double getPerimeter();

    public double getArea();

}

abstract class Points implements ClosedFigure {
    protected Points(Vector pos, Vector... points) {

    }
}

// class Figure {
// protected Vector pos;

// protected Figure(Vector pos) {
// this.pos = pos;
// }

// public Figure move(Vector to) {
// return new Figure(pos.add(to));
// }

// public boolean equals(Object other) {
// if (this == other)
// return true;
// if (other == null || getClass() != other.getClass())
// return false;
// return ((Figure) other).pos == pos;
// }

// public int hashCode() {
// return Objects.hash(pos);
// }
// }

// class ScalableFigure extends Figure {
// protected Vector[] points;

// protected ScalableFigure(Vector pos, Vector... points) {
// super(pos);
// this.points = points;
// }

// }

// abstract class ClosedFigure extends Figure {
// private Vector[] points;

// protected ClosedFigure(Vector pos, Vector[] points) {
// super(pos);
// }

// }
