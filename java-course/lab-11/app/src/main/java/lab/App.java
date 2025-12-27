package lab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nonogram.Nonogram;
import nonogram.NonogramGame;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        var gridPane = new GridPane();
        var loadButton = new Button("Выбрать файл");

        var game = new NonogramGame(new GridPaneScreen(gridPane));
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите файл");
            String currentDir = System.getProperty("user.dir");
            fileChooser.setInitialDirectory(new File(currentDir));
            File file = fileChooser.showOpenDialog(stage);
            try {
                var fis = new FileInputStream(file);
                var s = new String(fis.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
                fis.close();
                game.create(Nonogram.deserialize(s));
            } catch (FileNotFoundException err) {
                return;
            } catch (IOException err) {
                return;
            }
        });

        var pane = new Pane();
        pane.setPrefSize(500, 500);
        gridPane.add(pane, 0, 0);

        VBox root = new VBox(10, gridPane, loadButton);
        Scene scene = new Scene(root);
        stage.setTitle("Разгадываем штуки");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
