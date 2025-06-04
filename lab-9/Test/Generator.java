package Test;

import Serialize.Serializable;
import java.util.List;

import Figures.*;

class Generator {
    static List<Class<? extends Serializable>> getAllSerializables() {
        return List.of(Vector.class, Circle.class, Ellipse.class,
                Polygon.class, Rectangle.class, Triangle.class, Group.class);
    }

    static Circle circle() {
        return new Circle(new Vector(0, 0), 5);
    }

    static Ellipse ellipse() {
        return new Ellipse(
                new Vector(0, 0),
                new Vector(5, 0),
                new Vector(0, 3));
    }

    static Polygon polygon() {
        return new Polygon(
                new Vector(1, 0),
                new Vector(3, 0),
                new Vector(4, 2),
                new Vector(2, 4),
                new Vector(0, 2));
    }

    static Rectangle rectangle() {
        return new Rectangle(
                new Vector(0, 0),
                new Vector(0, 4),
                new Vector(4, 0),
                new Vector(4, 4));
    }

    static Triangle triangle() {
        return new Triangle(
                new Vector(0, 0),
                new Vector(4, 0),
                new Vector(0, 4));
    }

    static List<Figure> figuresList() {
        return List.of(
                circle(),
                ellipse());
    }

    static List<Figure> otherFiguresList() {
        return List.of(
                rectangle(),
                polygon(),
                triangle());
    }

    static Group flatGroup() {
        return new Group(figuresList().toArray(new Figure[0]));
    }

    static Group nestedGroup() {
        return flatGroup().add(new Group(otherFiguresList().toArray(new Figure[0])));
    }
}