package Figures;

import java.util.Objects;

public class Point implements Figure {
    private final Vector Oxy;

    public Point(Vector pos) {
        this.Oxy = pos;
    }

    @Override
    public void draw() {
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
