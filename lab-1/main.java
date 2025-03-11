class Main {
    public static void main(String[] args) {
        System.out.printf("Мой вариант: %d\n", 108 % 4);
        int height = 5, width = 10;
        assert 1 <= width && 1 <= height && width <= 100 && height <= 100;

        var out = new StringBuilder();
        String template = "%%%dd * %%%dd = %%%dd %%s".formatted(String.valueOf(height).length(),
                String.valueOf(width).length(), String.valueOf(width * height).length());
        for (int h = 1; h <= height; h++)
            for (int w = 1; w <= width; w++) {
                out.append(template.formatted(h, w, h * w, w == width ? "\n" : "    "));
            }

        System.out.print(out);
    }
}