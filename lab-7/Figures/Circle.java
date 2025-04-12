package Figures;

import java.util.Objects;

public class Circle implements ClosedFigure {
    private final Vector Oxy;
    private final double r, perimeter, area;

    public Circle(Vector center, double r) {
        this(center, r, 2 * Math.PI * r, Math.PI * r * r);
    }

    private Circle(Vector Oxy, double r, double perimeter, double area) {
        this.Oxy = Oxy;
        this.r = r;
        this.perimeter = perimeter;
        this.area = area;
    }

    public Circle move(Vector to) {
        return new Circle(Oxy.add(to), r, perimeter, area);
    }

    public Circle scale(double k) {
        return new Circle(Oxy, r * k, perimeter * k, area * k * k);

    }

    public double getPerimeter() {
        return perimeter;
    }

    public double getArea() {
        return area;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        var otherSegment = (Circle) other;
        return otherSegment.Oxy == Oxy && otherSegment.r == r;
    }

    public int hashCode() {
        return Objects.hash(Oxy, r);
    }
}
