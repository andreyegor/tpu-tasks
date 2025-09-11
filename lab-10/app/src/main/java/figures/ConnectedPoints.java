package figures;

import javafx.scene.canvas.GraphicsContext;

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
    public void draw(GraphicsContext grc) {
        if (points.length==0) {
            return;
        }

        grc.save();
        grc.translate(Oxy.getX(), Oxy.getY());
        grc.moveTo(points[0].getX(), points[0].getY());
        for (int i = 1; i < points.length; i++) {
            grc.lineTo(points[i].getX(), points[i].getY());
        }
        grc.restore();
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