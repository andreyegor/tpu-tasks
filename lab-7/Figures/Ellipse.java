package Figures;

import java.util.Objects;

public class Ellipse implements ClosedFigure {
    private final Vector Oxy, semiaxisX, semiaxisY;
    private final double perimeter, area;

    public Ellipse(Vector center1, Vector center2, double r) {
        this(center1.add(center2).mul(.5), center2.sub(center1), center1.sub(center2));
    }

    public Ellipse(Vector center, Vector semiaxisX, Vector semiaxisY) {
        this(center,
                semiaxisX.getAngleX() < semiaxisY.getAngleY() ? semiaxisX : semiaxisY,
                semiaxisX.getAngleX() < semiaxisY.getAngleY() ? semiaxisY : semiaxisX,
                Math.PI * (semiaxisX.getLength() + semiaxisY.getLength()),
                Math.PI * semiaxisX.getLength() * semiaxisY.getLength());
    }

    private Ellipse(Vector Oxy, Vector semiaxisX, Vector semiaxisY, double perimeter, double area) {
        if (Double.compare(semiaxisX.scalar(semiaxisY), 0) != 0) {
            throw new IllegalArgumentException();
        }
        this.Oxy = Oxy;
        this.semiaxisX = semiaxisX;
        this.semiaxisY = semiaxisY;
        this.perimeter = perimeter;
        this.area = area;
    }

    public Ellipse move(Vector to) {
        return new Ellipse(Oxy.add(to), semiaxisX, semiaxisY, perimeter, area);
    }

    public Ellipse scale(double k) {
        return new Ellipse(Oxy, semiaxisX.mul(k), semiaxisY.mul(k), perimeter * k, area * k * k);

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
        var otherEllipsis = (Ellipse) other;
        return otherEllipsis.Oxy == Oxy && otherEllipsis.semiaxisX == semiaxisX && otherEllipsis.semiaxisY == semiaxisY;
    }

    public int hashCode() {
        return Objects.hash(Oxy, semiaxisX, semiaxisY);
    }

}
