import java.util.Objects;
import java.lang.Math;

public final class Vector2 {
    private final double x;
    private final double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 multiply(double k) {
        return new Vector2(x * k, y * k);
    }

    public double angleX() {
        return angle(new Vector2(1, 0));
    }

    public double angleY() {
        return angle(new Vector2(0, 1));
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    public double angle(Vector2 other) {
        return Math.acos(scalar(other) / (length() * other.length()));
    }

    public double scalar(Vector2 other) {
        return x * other.x + y * other.y;
    }

    public double area(Vector2 other) {
        return Math.abs(x * other.y - y * other.x) / 2.;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        Vector2 vector2 = (Vector2) other;
        // compare корректно сравнивает floating point
        return Double.compare(vector2.x, x) == 0 &&
                Double.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}