package Figures;

import java.util.Objects;
import java.lang.Math;

public final class Vector {
    private static final Vector OX = new Vector(1, 0, 0, Math.PI / 2);
    private static final Vector OY = new Vector(0, -1, Math.PI / 2, 0);

    private final double x;
    private final double y;
    private final double length;
    private final double angleX;
    private final double angleY;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.length = length();
        this.angleX = this == OX ? 0 : counterClockwiseAngle(OX);
        this.angleY = this == OY ? 0 : counterClockwiseAngle(OY);

    }

    // Ситуация когда мы хотим задать вручную углы Существует -- избежать рекурсии
    // при инициализации. А когда хотим также задать длину -- нет.
    // Как реализовать такую структуру без дублирования кода в этом месте я не знаю,
    // можно поговорить об этом на сдаче. Если что можно чекаут хэд 1, там класс
    // более аккуратен, но углы считаются каждый раз.
    private Vector(double x, double y, double angleX, double angleY) {
        this.x = x;
        this.y = y;
        this.length = length();
        this.angleX = angleX;
        this.angleY = angleY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLength() {
        return length;
    }

    public double getAngleX() {
        return angleX;
    }

    public double getAngleY() {
        return angleY;
    }

    public Vector mul(double k) {
        return new Vector(x * k, y * k);
    }

    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }

    public Vector sub(Vector other) {
        return new Vector(x - other.x, y - other.y);
    }

    public double angle(Vector other) {
        return Math.acos(scalar(other) / (getLength() * other.getLength()));
    }

    public double scalar(Vector other) {
        return x * other.x + y * other.y;
    }

    public double area(Vector other) {
        return Math.abs(x * other.y - y * other.x) / 2.;
    }

    private double counterClockwiseAngle(Vector other) {
        var cca = Math.atan2(x * other.y - y * other.x, this.scalar(other));
        return cca >= 0 ? cca : cca + 2 * Math.PI;
    }

    private double length() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        Vector vector2 = (Vector) other;
        // compare корректно сравнивает floating point
        return Double.compare(vector2.x, x) == 0 &&
                Double.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}