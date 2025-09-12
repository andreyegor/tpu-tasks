package figures;

import javafx.scene.canvas.GraphicsContext;

abstract class ClosePoints extends Points implements CloseFigure {
    protected double perimeter, area;

    protected ClosePoints(Vector[] points) {
        this(findOxy(points), findPoints(points));
    }

    @Override
    public void draw(GraphicsContext grc) {
        if (points.length == 0) {
            return;
        }

        grc.save();
        grc.translate(Oxy.getX(), Oxy.getY());
        grc.moveTo(points[0].getX(), points[0].getY());
        for (int i = 1; i < points.length; i++) {
            grc.lineTo(points[i].getX(), points[i].getY());
        }
        grc.closePath();
        grc.restore();
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
