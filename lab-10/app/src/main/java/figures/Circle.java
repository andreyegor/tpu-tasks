package figures;

import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;

public class Circle implements CloseFigure {
    private final Vector Oxy;
    private final double r, perimeter, area;

    public Circle(Vector center, double r) {
        this.Oxy = center;
        this.r = r;
        this.perimeter = 2 * Math.PI * r;
        this.area = Math.PI * r * r;
    }

    @Override
    public void draw(GraphicsContext grc) {
        grc.save();
        grc.translate(Oxy.getX(), Oxy.getY());
        grc.strokeOval(r * -1, r * -1, r * 2, r * 2);
        grc.restore();
    };

    @Override
    public Circle move(Vector to) {
        return new Circle(Oxy.add(to), r);
    }

    @Override
    public Circle scale(double k) {
        return new Circle(Oxy, r * k);

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
        var otherSegment = (Circle) other;
        return otherSegment.Oxy.equals(Oxy) && otherSegment.r == r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Oxy, r);
    }
}
