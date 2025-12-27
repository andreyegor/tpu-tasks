package figures;

abstract class ConnectedPoints extends Points implements OpenFigure {
    final double length;

    protected ConnectedPoints(Vector[] points) {
        this(findOxy(points), findPoints(points));
    }

    protected ConnectedPoints(Vector Oxy, Vector[] points) {
        super(Oxy, points);
        length = length();
    }

    @Override
    public void draw() {
    }

    @Override
    public double getLength() {
        return length;
    }

    protected double length() {
        double out = 0;
        for (int i = 0; i < points.length - 1; i++) {
            out += points[i].sub(points[i + 1]).getLength();
        }
        return out;
    }
}