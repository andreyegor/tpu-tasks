import Figures.*;

public class Test {
    public static void main(String[] args) {
        testPoint();
        testSegment();
        testTriangle();
        testRectangle();
        testEllipse();
        System.out.println("All tests passed.");
    }

    static void testPoint() {
        Point p1 = new Point(new Vector(10, 20));
        Point p2 = new Point(new Vector(10, 20));
        assert p1.equals(p2);
        assert p1.hashCode() == p2.hashCode();
        Vector delta = new Vector(5, -5);
        Point moved = p1.move(delta);
        Point expected = new Point(new Vector(15, 15));
        assert moved.equals(expected);
    }

    static void testSegment() {
        var start = new Vector(0, 0);
        var end = new Vector(3, 4);
        Segment seg = new Segment(start, end);
        Segment seg2 = new Segment(new Vector(0, 0), new Vector(3, 4));
        assert seg.equals(seg2);
        assert seg.hashCode() == seg2.hashCode();
        double expectedLength = 5.0;
        assert Double.compare(seg.getLength(), expectedLength) == 0;
        Vector delta = new Vector(10, 10);
        Segment moved = seg.move(delta);
        Segment expectedMoved = new Segment(start.add(delta), end.add(delta));
        assert moved == expectedMoved;
        Segment scaled = seg.scale(2);
        assert scaled.getLength() == seg.getLength() * 2;
    }

    static void testTriangle() {
        Vector a = new Vector(0, 0);
        Vector b = new Vector(4, 0);
        Vector c = new Vector(0, 3);
        Triangle triangle = new Triangle(a, b, c);
        double area = triangle.getArea();
        assert Math.abs(area - 6.0) < 1e-6;
        double perimeter = triangle.getPerimeter();
        // Периметр: 4 + 3 + 5 = 12
        assert Math.abs(perimeter - 12.0) < 1e-6;

        Vector delta = new Vector(10, 10);
        Triangle moved = triangle.move(delta);
        Triangle expected = new Triangle(a.add(delta), b.add(delta), c.add(delta));
        assert moved.equals(expected);

        Triangle scaled = triangle.scale(2);
        assert Double.compare(scaled.getPerimeter(), triangle.getPerimeter() * 2) == 0;
    }

    static void testRectangle() {
        Rectangle rect = new Rectangle(new Vector(0, 0), new Vector(0, 4), new Vector(4, 0), new Vector(4, 4));

        assert Double.compare(rect.getArea(), 16) == 0;
        assert Double.compare(rect.getPerimeter(), 16) == 0;

        Vector delta = new Vector(5, 5);
        Rectangle moved = rect.move(delta);
        Rectangle expected = new Rectangle(new Vector(5, 5), new Vector(5, 9), new Vector(9, 9), new Vector(9, 5));
        assert moved == expected;

        Rectangle scaled = rect.scale(2);
        assert Double.compare(scaled.getArea(), rect.getArea() * 4) == 0;
        assert Double.compare(scaled.getPerimeter(), rect.getPerimeter() * 2) == 0;

        try {
            new Rectangle(new Vector(0, 0), new Vector(0, 4), new Vector(5, 0), new Vector(4, 4));
            assert 0 == 1;
        } catch (java.lang.IllegalArgumentException e) {
        }
    }

    static void testEllipse() {
        Ellipse ellipse = new Ellipse(new Vector(0, 0), new Vector(5, 0), new Vector(0, 3));

        Vector delta = new Vector(10, 20);
        Ellipse moved = ellipse.move(delta);
        Ellipse expected = new Ellipse(new Vector(10, 20), new Vector(0, 3), new Vector(5, 0));
        assert moved.equals(expected) : "Moved ellipse doesn't match expected ellipse";
    }
}