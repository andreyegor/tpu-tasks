package lab;

import java.util.BitSet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nonogram.NonogramGame;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        int ox = 10, oy = 10;
        var image = new BitSet(ox * oy);
        for (int y = 0; y < oy; y++) {
            for (int x = 0; x < ox; x++) {
                if ((x + y) % 2 == 0) {
                    image.set(y * ox + x);
                }
            }
        }

        var grid = new GridPane();
        var gridScreen = new GridPaneScreen(grid);

        var nonogram = new NonogramGame(gridScreen);
        nonogram.create(image, ox, oy);

        Scene scene = new Scene(grid);
        stage.setTitle("Разгадываем штуки");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
