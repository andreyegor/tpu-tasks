package Figures;

public class Rectangle extends ClosePoints{
    public Rectangle(Vector a, Vector b, Vector c, Vector d) {
        super(findOxy(new Vector[] { a, b, c, d }), findPoints(new Vector[] { a, b, c, d }));
    }

    protected Rectangle(Vector Oxy, Vector[] points) {
        super(Oxy, points);
    }

    @Override

    public Rectangle move(Vector to) {
        return new Rectangle(moveOxy(to), points);
    }

    @Override
    public Rectangle scale(double k) {
        return new Rectangle(Oxy, scalePoints(k));
    }

    @Override
    protected boolean invariant() {
        for (int i = 0; i < 4; i++) {
            if (Double.compare(points[i].scalar(points[(i + 1) % points.length]), 0) != 0) {
                return false;
            }
        }
        return points.length == 4;
    }
}