package lab;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lab.algebra.infra.controller.ScreenController;
import lab.algebra.infra.view.MessagesView;
import lab.algebra.infra.view.ScreenView;
import lab.algebra.infra.view.SettingsView;
import lab.core.logic.GameLogic;
import lab.infra.controller.CanvasController;
import lab.infra.controller.GridController;
import lab.infra.view.CanvasView;
import lab.infra.view.GridView;
import lab.infra.view.MessagesViewImpl;
import lab.infra.view.SettingsViewImpl;

public class App extends Application {

    private final int MIN_PIXELS_SIZE = 300, MAX_PIXELS_SIZE = 1000;
    private final int SETTINGS_PIXELS_SIZE = 200;

    private Stage stage;
    private BorderPane root;

    private final MessagesView messagesView = new MessagesViewImpl();
    private final SettingsView settingsView = new SettingsViewImpl();

    private GameLogic gameLogic;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        root = new BorderPane();

        Scene scene = new Scene(root, SETTINGS_PIXELS_SIZE, SETTINGS_PIXELS_SIZE);
        stage.setScene(scene);
        stage.setTitle("Настраиваем штуки");
        stage.show();

        settingsView.open((size, bombs, mode) -> {
            Platform.runLater(() -> startGame(size, bombs, mode));
        });
    }

    private void startGame(int gridSize, int bombsCnt, SettingsView.DisplayMode displayMode) {
        ScreenView screenView;
        ScreenController screenController;

        int pixelsSize = Math.max(MIN_PIXELS_SIZE, Math.min(MAX_PIXELS_SIZE, gridSize * 22));

        stage.setWidth(pixelsSize * 1.10);
        stage.setHeight(pixelsSize * 1.15);

        if (displayMode == SettingsView.DisplayMode.CANVAS) {
            Canvas canvas = new Canvas(pixelsSize, pixelsSize);
            screenView = new CanvasView(canvas);
            screenController = new CanvasController(canvas);
            root.setCenter(canvas);
        } else {
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(4));
            screenView = new GridView(grid, pixelsSize);
            screenController = new GridController(grid);
            root.setCenter(grid);
        }

        stage.setTitle("Разминировываем штуки");

        Platform.runLater(() -> {
            gameLogic = new GameLogic(screenView, messagesView, screenController);
            gameLogic.start(gridSize, bombsCnt);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
