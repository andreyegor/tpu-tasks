package Figures;

public class Triangle extends ClosePoints {
    public Triangle(Vector a, Vector b, Vector c) {
        super(findOxy(new Vector[] { a, b, c }), findPoints(new Vector[] { a, b, c }));
    }

    protected Triangle(Vector Oxy, Vector[] points) {
        super(Oxy, points);
    }

    @Override
    public Triangle move(Vector to) {
        return new Triangle(moveOxy(to), points);
    }

    @Override
    public Triangle scale(double k) {
        return new Triangle(Oxy, scalePoints(k));
    }

    @Override
    protected boolean invariant() {
        return points.length == 3;
    }
}