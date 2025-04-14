package Figures;

import java.util.Objects;

public class Segment implements ScalableFigure {
    private final Vector Oxy, a, b;
    private final double length;

    public Segment(Vector a, Vector b) {
        // А красиво и не сделать :(
        this(a.add(b).mul(.5),
                b.sub(a).mul(.5).getAngleX() < a.sub(b).mul(.5).getAngleX() ? b.sub(a).mul(.5) : a.sub(b).mul(.5),
                b.sub(a).mul(.5).getAngleX() < a.sub(b).mul(.5).getAngleX() ? a.sub(b).mul(.5) : b.sub(a).mul(.5),
                b.sub(a).getLength());
    }

    private Segment(Vector Oxy, Vector a, Vector b, double length) {
        this.Oxy = Oxy;
        this.a = a;
        this.b = b;
        this.length = length;
    }

    public Segment move(Vector to) {
        return new Segment(Oxy.add(to), a, b, length);
    }

    public double getLength() {
        return length;
    }

    public Segment scale(double k) {
        return new Segment(Oxy, a.mul(k), b.mul(k), length * k);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        var otherSegment = (Segment) other;
        return otherSegment.Oxy == Oxy && otherSegment.a == a && otherSegment.b == b;
    }

    public int hashCode() {
        return Objects.hash(Oxy, a, b);
    }

}
