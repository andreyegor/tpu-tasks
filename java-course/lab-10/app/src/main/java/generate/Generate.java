package generate;

import java.util.ArrayList;

import java.lang.Math;

import figures.*;

public final class Generate {
    public static Group bike() {
        var wheelR = 50;
        var wheelLst = new ArrayList<Figure>();
        wheelLst.add(new Circle(new Vector(0, 0), wheelR));
        var spokesCnt = 15D;
        for (var i = 0D; i < spokesCnt; i++) {
            wheelLst.add(new Segment(new Vector(0, 0),
                    new Vector(wheelR * Math.cos(2 * Math.PI * i / spokesCnt),
                            wheelR * Math.sin(2 * Math.PI * i / spokesCnt))));
        }

        var wheels = new Group(new Group(wheelLst.toArray(new Figure[0])),
                new Group(wheelLst.toArray(new Figure[0])).move(new Vector(175, 0)));

        var frame = new Group(
                new Segment(new Vector(0, 0), new Vector(37, -150)),
                new Segment(new Vector(25, -100), new Vector(112, -25)),
                new Segment(new Vector(25, -100), new Vector(100, 12)),
                new Segment(new Vector(112, -25), new Vector(175, 0)),
                new Segment(new Vector(100, 12), new Vector(175, 0)),
                new Segment(new Vector(100, 12), new Vector(120, -50)));

        var handlebar = new Group(
                new Segment(new Vector(37, -150), new Vector(29, -150)),
                new Circle(new Vector(32, -150), 3));

        var seat = new Segment(new Vector(100, -50), new Vector(125, -50));

        var crank = new Group(
                new Segment(new Vector(100, 12), new Vector(100, 25)),
                new Segment(new Vector(100, 12), new Vector(100, 0)),
                new Segment(new Vector(95, 25), new Vector(105, 25)),
                new Segment(new Vector(95, 0), new Vector(105, 0)));

        return new Group(wheels, frame, handlebar, seat, crank).move(new Vector(250, 250));
    }

    public static Group house() {
        var body = new Rectangle(
                new Vector(0, 0),
                new Vector(100, 0),
                new Vector(100, -100),
                new Vector(0, -100));

        var roof = new Triangle(
                new Vector(0, -100),
                new Vector(50, -150),
                new Vector(100, -100));

        var window = new Rectangle(
                new Vector(15, -30),
                new Vector(45, -30),
                new Vector(45, -60),
                new Vector(15, -60));

        var door = new Rectangle(
                new Vector(60, 0),
                new Vector(90, 0),
                new Vector(90, -60),
                new Vector(60, -60));

        var leg = new Group(
                new Segment(new Vector(0, 0), new Vector(0, 75)),
                new Segment(new Vector(0, 75), new Vector(-6, 78)),
                new Segment(new Vector(0, 75), new Vector(6, 78)),
                new Segment(new Vector(0, 75), new Vector(0, 77)));

        var leg1 = leg.move(new Vector(25, 0));
        var leg2 = leg.move(new Vector(75, 0));

        return new Group(body, roof, window, door, leg1, leg2).move(new Vector(250, 250));
    }
}
