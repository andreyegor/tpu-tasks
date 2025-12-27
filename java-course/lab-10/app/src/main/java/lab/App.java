package lab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import figures.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import serialize.JsonLike.JsonLikeSerializer;
import serialize.Stream.InputStreamSerializer;
import serialize.Stream.OutputStreamSerializer;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(500, 500);
        GraphicsContext grc = canvas.getGraphicsContext2D();
        Button button = new Button("Выбрать файл");

        button.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите файл");
            String currentDir = System.getProperty("user.dir");
            fileChooser.setInitialDirectory(new File(currentDir));
            File file = fileChooser.showOpenDialog(stage);
            try {
                var serializer = new JsonLikeSerializer();
                var fis = new FileInputStream(file);
                var iss = new InputStreamSerializer(fis, serializer);
                var fig = (Figure) iss.read();
                grc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                grc.beginPath();
                fig.draw(grc);
            } catch (FileNotFoundException err) {
                grc.fillText("Файл не найден", 0, 0);
            } catch (IOException err) {
                grc.fillText("Не получилось прочитать файл", 0, 0);
            } catch (ClassCastException err) {
                grc.fillText("Файл дата ис корраптед", 0, 0);
            }
            grc.stroke();
        });

        VBox root = new VBox(10, canvas, button);
        Scene scene = new Scene(root, 500, 600);
        stage.setTitle("Рисуем штуки");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        var bike = generate.Generate.bike();
        write(bike, "bike");
        var house = generate.Generate.house();
        write(house, "house");
        launch(args);
    }

    private static boolean write(Object figure, String name) {
        var serializer = new JsonLikeSerializer();
        try {
            var fos = new FileOutputStream(name);
            var oss = new OutputStreamSerializer(fos, serializer);
            oss.write(figure);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
