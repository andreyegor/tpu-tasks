package Figures;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

abstract class Points implements ScalableFigure {
    protected final Vector Oxy;
    protected final Vector[] points;

    protected Points(Vector Oxy, Vector[] points) {
        this.Oxy = Oxy;
        this.points = points;

        if (!(points.length > 1 && invariant()))
            throw new IllegalArgumentException("Нарушен инвариант");
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
                Arrays.stream(points)
                        .allMatch(p -> Arrays.stream(otherFigure.points).anyMatch(p::equals));
    }

    @Override
    public int hashCode() {
        return Objects.hash(Oxy, Arrays.hashCode(points));
    }
}

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

abstract class ClosePoints extends Points implements CloseFigure {
    protected double perimeter, area;

    protected ClosePoints(Vector[] points) {
        this(findOxy(points), findPoints(points));
    }

    @Override
    public void draw() {
    }

    @Override
    public double getPerimeter() {
        return perimeter;
    }

    @Override
    public double getArea() {
        return area;
    }

    protected ClosePoints(Vector Oxy, Vector[] points) {
        super(Oxy, points);
        perimeter = perimeter();
        area = area();
    }

    protected double perimeter() {
        double out = 0.;
        for (int i = 1; i < points.length - 1; i++) {
            points[i].sub(points[(i + 1) % points.length]).getLength();
        }
        return out;
    }

    protected double area() {
        var Oxy = points[0];
        double out = 0.;
        for (int i = 1; i < points.length - 1; i++) {
            var a = points[i].sub(Oxy);
            var b = points[i + 1].sub(Oxy);
            out += a.area(b);
        }
        return out;
    }

}
