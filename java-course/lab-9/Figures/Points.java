package figures;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

abstract class Points implements ScalableFigure {
    protected final Vector Oxy;
    protected final Vector[] points;

    protected Points(Vector Oxy, Vector[] points) {
        this.Oxy = Oxy;
        this.points = points;
        assert points.length > 1 && invariant() : "Нарушен инвариант";
    }

    abstract public Points move(Vector to);

    abstract public Points scale(double k);

    abstract protected boolean invariant();

    @Override
    public void draw() {

    }

    protected Vector[] scalePoints(double factor) {
        return Stream.of(points).map(v -> v.mul(factor)).toArray(Vector[]::new);
    }

    protected Vector moveOxy(Vector to) {
        return Oxy.add(to);
    }

    protected static Vector findOxy(Vector[] points) {
        return Stream.of(points).reduce(Vector::add).get().mul(1.0 / points.length);
    }

    protected static Vector[] findPoints(Vector[] points) {
        var Oxy = findOxy(points);// Это нужно чтобы не добавлять лишний уровень конструктора
        return sort(Stream.of(points).map(v -> v.sub(Oxy))).toArray(Vector[]::new);
    }

    protected static Stream<Vector> sort(Stream<Vector> points) {
        return points.sorted((v1, v2) -> {
            int angleComparison = Double.compare(v1.getAngleX(), v2.getAngleX());
            return angleComparison != 0 ? angleComparison : Double.compare(v1.getLength(), v2.getLength());
        });
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        var otherFigure = (Points) other;
        return Oxy.equals(otherFigure.Oxy) &&
                points.length == otherFigure.points.length &&
                Arrays.equals(points, otherFigure.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Oxy, Arrays.hashCode(points));
    }
}