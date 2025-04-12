package Figures;

import java.util.stream.*;
import java.util.Arrays;
import java.util.Objects;

public class Rectangle implements ClosedFigure {
    private final Vector Oxy;
    private final Vector[] points;
    private final double perimeter, area;

    public Rectangle(Vector point1, Vector point2, Vector point3, Vector point4) {
        this(Stream.of(point1, point2, point3, point4).toArray(Vector[]::new));
        for (int i = 0; i < 4; i++) {
            if (Double.compare(points[i].scalar(points[(i + 1) % points.length]), 0) != 0) {
                throw new IllegalArgumentException("Получился не прямоугольник :(");
            }
        }
    }

    private Rectangle(Vector[] points) {
        this(Stream.of(points).reduce(Vector::add).get().mul(1.0 / points.length), points);
    }

    private Rectangle(Vector center, Vector[] points) {
        this(center,
                sort(Stream.of(points).map(v -> v.sub(center))).toArray(Vector[]::new),
                perimeter(points), area(points));
    }

    private Rectangle(Vector Oxy, Vector[] points, double perimeter, double area) {
        this.Oxy = Oxy;
        this.points = points;
        this.perimeter = perimeter;
        this.area = area;
    }

    public Rectangle move(Vector to) {
        return new Rectangle(Oxy.add(to), points, perimeter, area);
    }

    public Rectangle scale(double k) {
        var scaledPoints = Stream.of(points).map(v -> v.mul(k)).toArray(Vector[]::new);
        return new Rectangle(Oxy, scaledPoints, perimeter * k, area * k * k);
    }

    public double getPerimeter() {
        return perimeter;
    }

    public double getArea() {
        return area;
    }

    private static double perimeter(Vector[] points) {
        double out = 0.;
        for (int i = 1; i < points.length - 1; i++) {
            points[i].sub(points[(i + 1) % points.length]).getLength();
        }
        return out;
    }

    private static double area(Vector[] points) {
        var Oxy = points[0];
        double out = 0.;
        for (int i = 1; i < points.length - 1; i++) {
            var a = points[i].sub(Oxy);
            var b = points[i + 1].sub(Oxy);
            out += a.area(b);
        }
        return out;
    }

    private static Stream<Vector> sort(Stream<Vector> points) {
        return points.sorted((v1, v2) -> {
            int angleComparison = Double.compare(v1.getAngleX(), v2.getAngleX());
            return angleComparison != 0 ? angleComparison : Double.compare(v1.getLength(), v2.getLength());
        });
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        var otherFigure = (Rectangle) other;
        return Oxy.equals(otherFigure.Oxy) &&
                points.length == otherFigure.points.length &&
                Arrays.stream(points)
                        .allMatch(p -> Arrays.stream(otherFigure.points).anyMatch(p::equals));
    }

    public int hashCode() {
        return Objects.hash(Oxy, Arrays.hashCode(points));
    }

}