class Main {
    public static void main(String[] args) {
        System.out.printf("Мой вариант: %d\n", 108 % 4);
        int width = 10, height = 5;
        assert 1 <= width && 1 <= height && width <= 100 && height <= 100;

        var out = new StringBuilder();
        String template = "%%%dd * %%%dd = %%%dd %%s".formatted(String.valueOf(height).length(),
                String.valueOf(width).length(), String.valueOf(width * height).length());
        for (int w = 1; w <= height; w++)
            for (int h = 1; h <= width; h++) {
                out.append(template.formatted(w, h, h * w, h == width ? "\n" : "    "));
            }

        System.out.print(out);
    }
}