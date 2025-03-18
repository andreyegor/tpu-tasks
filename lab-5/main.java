import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;

class Main {
    public static void main(String[] args) {
        var cli = new CliParser(new String[]{"-count","-desk", "-horizontal","-all", "-ignoreCase"}, new String[]{"-out"});
        var parsed = cli.parse(args);
        if (!parsed.getStatus()){
            System.out.println(parsed.getMessage());
            return;
        }
        
        System.out.println(java.nio.charset.Charset.defaultCharset());
        String fileName = "java-course/lab-4/sample.txt";
        HashMap<String, Integer> data;
        try {
            data = openAndCount(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            return;
        } catch (IOException e) {
            System.out.println("Что-то пошло не так при чтении файла");
            return;
        }
        System.out.println(Plot.generate(data, true, false, false));
    }

    private static HashMap<String, Integer> openAndCount(String path) throws IOException {
        var br = new BufferedReader(new FileReader(path));
        var out = Counter.count(br);
        br.close();
        return out;

    }
}





