package Figures;

public class Polygon extends ClosePoints {
    public Polygon(Vector... points) {
        super(findOxy(points), findPoints(points));
    }

    protected Polygon(Vector Oxy, Vector[] points) {
        super(Oxy, points);
    }

    @Override
    public Polygon move(Vector to) {
        return new Polygon(moveOxy(to), points);
    }

    @Override
    public Polygon scale(double k) {
        return new Polygon(Oxy, scalePoints(k));
    }

    @Override
    protected boolean invariant() {
        return points.length > 2;
    }
}