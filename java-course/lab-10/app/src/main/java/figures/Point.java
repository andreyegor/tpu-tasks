package figures;

import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;

public class Point implements Figure {
    private final Vector Oxy;

    public Point(Vector pos) {
        this.Oxy = pos;
    }

    @Override
    public void draw(GraphicsContext grc) {
        var r = 2;
        grc.fillOval(Oxy.getX() - r, Oxy.getY() - r, 2 * r, 2 * r);
    };

    public Point move(Vector to) {
        return new Point(Oxy.add(to));
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        return ((Point) other).Oxy.equals(Oxy);
    }

    public int hashCode() {
        return Objects.hash(Oxy);
    }
}
