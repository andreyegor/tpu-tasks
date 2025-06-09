public class Run {
    public static void run() {
        // - ╱╲
        // - ╲╱ │ - 2
        // - ── - 4
        System.out.println(area(polygon(4, 2)));

        // Когда многоугольник почти круг площадь почти pi, классно
        System.out.println(area(polygon(100000, 1)));

        System.out.println(perimeter(polygon(4, 2)));// ~столько и есть
        System.out.println(perimeter(polygon(10000, 1)));// ~2*pi*r
    }

    public static double[][] polygon(int n, int r) {
        double[][] out = new double[n][2];
        double step = 2 * Math.PI / n;
        for (int i = 0; i < n; i++) {
            out[i][0] = r * Math.cos(i * step);
            out[i][1] = r * Math.sin(i * step);
        }
        return out;
    }

    public static double area(double[][] polygon) {
        assert polygon.length >= 3 && polygon[0].length == 2;
        // Выберем единую точку для всех треугольников и примем её за 0
        var Oxy = new Vector(polygon[0][0], polygon[0][1]);
        double out = 0.;
        for (int i = 1; i < polygon.length - 1; i++) {
            var a = new Vector(polygon[i][0], polygon[i][1]).subtract(Oxy);
            var b = new Vector(polygon[i + 1][0], polygon[i + 1][1]).subtract(Oxy);
            out += a.area(b);
        }
        return out;
    }

    public static double perimeter(double[][] polygon) {
        assert polygon.length >= 3 && polygon[0].length == 2;
        double out = 0.;
        for (int i = 0; i < polygon.length; i++) {
            var a = new Vector(polygon[i][0], polygon[i][1]);
            var b = new Vector(polygon[(i + 1) % polygon.length][0], polygon[(i + 1) % polygon.length][1]);
            out += b.subtract(a).getLength();
        }
        return out;
    }

}
