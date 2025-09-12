package figures;

import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse implements CloseFigure {
    private final Vector Oxy, semiaxisX, semiaxisY;
    private final double perimeter, area;

    public Ellipse(Vector center1, Vector center2, double r) {
        this(center1.add(center2).mul(.5), center2.sub(center1), center1.sub(center2));
    }

    public Ellipse(Vector center, Vector semiaxisX, Vector semiaxisY) {
        if (Double.compare(semiaxisX.scalar(semiaxisY), 0) != 0) {
            throw new IllegalArgumentException();
        }
        this.Oxy = center;
        this.semiaxisX = semiaxisX.getAngleX() < semiaxisY.getAngleY() ? semiaxisX : semiaxisY;
        this.semiaxisY = semiaxisX.getAngleX() < semiaxisY.getAngleY() ? semiaxisY : semiaxisX;
        this.perimeter = Math.PI * (semiaxisX.getLength() + semiaxisY.getLength());
        this.area = Math.PI * semiaxisX.getLength() * semiaxisY.getLength();
    }

    @Override
    public void draw(GraphicsContext grc) {
        grc.save();
        grc.translate(Oxy.getX(), Oxy.getY());
        grc.rotate(semiaxisX.getAngleX());
        grc.strokeOval(semiaxisX.getLength() * -1,
                semiaxisY.getLength() * -1,
                semiaxisX.getLength() * 2,
                semiaxisY.getLength() * 2);
        grc.restore();
    };

    @Override
    public Ellipse move(Vector to) {
        return new Ellipse(Oxy.add(to), semiaxisX, semiaxisY);
    }

    @Override
    public Ellipse scale(double k) {
        return new Ellipse(Oxy, semiaxisX.mul(k), semiaxisY.mul(k));

    }

    @Override
    public double getPerimeter() {
        return perimeter;
    }

    @Override
    public double getArea() {
        return area;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        var otherEllipsis = (Ellipse) other;
        return otherEllipsis.Oxy.equals(Oxy) && otherEllipsis.semiaxisX.equals(semiaxisX)
                && otherEllipsis.semiaxisY.equals(semiaxisY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Oxy, semiaxisX, semiaxisY);
    }

}
