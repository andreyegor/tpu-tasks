package figures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.scene.canvas.GraphicsContext;

public class Group implements OpenFigure, CloseFigure {
    private final Figure[] figures;
    final double perimeter, area, length;

    public Group(Figure... figures) {
        this.figures = figures.clone();
        this.perimeter = Stream.of(this.figures)
                .map(f -> (f instanceof CloseFigure) ? ((CloseFigure) f).getPerimeter() : 0.)
                .reduce(Double::sum).orElse(0.);
        this.area = Stream.of(this.figures)
                .map(f -> (f instanceof CloseFigure) ? ((CloseFigure) f).getArea() : 0.)
                .reduce(Double::sum).orElse(0.);
        this.length = Stream.of(this.figures)
                .map(f -> (f instanceof OpenFigure) ? ((OpenFigure) f).getLength() : 0.)
                .reduce(Double::sum).orElse(0.);
    };

    public static Group flatten(Figure... figures) {
        var out = new ArrayList<Figure>();
        for (var figure : figures) {
            if (figure instanceof Group)
                out.addAll(Arrays.asList(((Group) figure).getAll()));
            else
                out.add(figure);
        }
        return new Group(out.toArray(Figure[]::new));
    }

    public Figure get(int i) {
        return figures[i];// Если срабатывает борроучекер то нам так и так нужно развалиться
    }

    public Figure[] getAll() {
        return figures.clone();
    }

    public Group add(Figure... figures) {
        return new Group(Stream.concat(Stream.of(this.figures), Stream.of(figures)).toArray(Figure[]::new));
    }

    public Group remove(int... indices) {
        var s = Arrays.stream(indices).boxed().collect(Collectors.toSet());
        var out = IntStream.range(0, figures.length)
                .filter(i -> !s.contains(i)).mapToObj(i -> figures[i])
                .toArray(Figure[]::new);
        if (!s.isEmpty())
            throw new IllegalArgumentException();
        return new Group(out);
    }

    public Group removeLast(int... indices) {
        return remove(figures.length - 1);
    }

    @Override
    public void draw(GraphicsContext grc) {
        Stream.of(figures).forEach(f -> f.draw(grc));
    }

    @Override
    public Group move(Vector to) {
        return new Group(Stream.of(figures)
                .map(f -> f.move(to))
                .toArray(Figure[]::new));
    }

    @Override
    public Group scale(double factor) {
        return new Group(Stream.of(figures)
                .map(f -> (f instanceof ScalableFigure) ? ((ScalableFigure) f).scale(factor) : f)
                .toArray(Figure[]::new));
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
    public double getLength() {
        return length;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Group group = (Group) other;
        return java.util.Arrays.equals(this.figures, group.figures);
    }

    @Override
    public int hashCode() {
        return java.util.Arrays.hashCode(figures);
    }
}
