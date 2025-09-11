package figures;

public class Segment extends ConnectedPoints {
    public Segment(Vector a, Vector b) {
        this(findOxy(new Vector[] { a, b }), findPoints(new Vector[] { a, b }));
    }

    private Segment(Vector Oxy, Vector[] points) {
        super(Oxy, points);
    }

    @Override
    public Segment move(Vector to) {
        return new Segment(moveOxy(to), points);
    }

    @Override
    public Segment scale(double factor) {
        return new Segment(Oxy, scalePoints(factor));
    }

    @Override
    protected boolean invariant() {
        return points.length == 2;
    }
}
