class Main {
    public static void main(String[] args) {
        System.out.printf("Мой вариант: %d\n", 108 % 4);
        int height = 100, width = 100;
        assert 0 < height && 0 < width && height <= 100 && width <= 100;

        var out = new StringBuilder();
        String template = "%%%dd * %%%dd = %%%dd %%s".formatted(String.valueOf(width).length(),
                String.valueOf(height).length(), String.valueOf(height * width).length());
        for (int w = 0; w <= width; w++)
            for (int h = 0; h <= height; h++) {
                out.append(template.formatted(w, h, h * w, h == height ? "\n" : "    "));
            }

        System.out.print(out);
    }
}