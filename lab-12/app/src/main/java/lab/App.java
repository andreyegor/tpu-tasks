package lab;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

public class App extends Application {
    private int size;
    private int bombs;
    private boolean[][] bombGrid;
    private int[][] statusGrid;
    private Canvas canvas;

    @Override
    public void start(Stage stage) {
        var root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        root.add(new Label("Длина стороны поля (от 5 до 100):"), 0, 0);
        var sizeField = new TextField("10");
        root.add(sizeField, 1, 0);

        root.add(new Label("Количество бомб:"), 0, 1);
        var bombsField = new TextField("25");
        root.add(bombsField, 1, 1);

        var startButton = new Button("Начать игру");
        startButton.setOnAction(e -> startGame(stage, sizeField, bombsField));
        root.add(startButton, 1, 2);

        var scene = new Scene(root, 400, 200);
        stage.setTitle("Разминируем штуки");
        stage.setScene(scene);
        stage.show();
    }

    private void startGame(Stage stage, TextField sizeField, TextField bombsField) {
        try {
            size = Integer.parseInt(sizeField.getText());
            bombs = Integer.parseInt(bombsField.getText());

            if (size < 5 || size > 100) {
                showError("Неверный размер поля");
                return;
            }

            if (bombs < 1 || bombs >= size * size - 9) {
                showError("Неверное количество бомб");
                return;
            }

            statusGrid = new int[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    statusGrid[i][j] = -1;
                }
            }

            int canvasSize = Math.max(300, Math.min(800, size * 20));
            canvas = new Canvas(canvasSize, canvasSize);
            canvas.setOnMouseClicked(e -> handleClick(e));
            var root = new GridPane();
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(10));
            root.add(canvas, 0, 0);

            var scene = new Scene(root, canvasSize + 20, canvasSize + 20);
            stage.setScene(scene);
            drawGame();
        } catch (NumberFormatException e) {
            showError("Не число");
        }
    }

    private void showError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR,
                message);
        alert.showAndWait();
    }

    private void generateBombs(int firstX, int firstY) {
        java.util.Random rnd = new java.util.Random();
        int placed = 0;
        bombGrid = new boolean[size][size];
        while (placed < bombs) {
            int x = rnd.nextInt(size);
            int y = rnd.nextInt(size);

            if ((x >= firstX - 1 && x <= firstX + 1 && y >= firstY - 1 && y <= firstY + 1) || bombGrid[x][y]) {
                continue;
            }
            bombGrid[x][y] = true;
            placed++;
        }
    }

    private int countBombsAround(int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && nx < size && ny >= 0 && ny < size && bombGrid[nx][ny]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void openCell(int x, int y) {
        if (bombGrid == null) {
            generateBombs(x, y);
        }

        if (statusGrid[x][y] != -1) {
            return;
        }

        if (bombGrid[x][y]) {
            statusGrid[x][y] = -3;
            revealAllBombs();
            drawGame();
            showError("Вы проиграли!");
            return;
        }

        int bombsAround = countBombsAround(x, y);
        statusGrid[x][y] = bombsAround;
        if (bombsAround != 0)
            return;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 && dy != 0)
                    continue;
                int nx = x + dx, ny = y + dy;
                if (nx < 0 || nx >= size || ny < 0 || ny >= size)
                    continue;
                if (statusGrid[nx][ny] == -1 && !bombGrid[nx][ny])
                    openCell(nx, ny);
            }
        }
    }

    private void toggleFlag(int x, int y) {
        if (statusGrid[x][y] >= 0)
            return;
        statusGrid[x][y] = statusGrid[x][y] == -2 ? -1 : -2;
    }

    private void revealAllBombs() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (bombGrid != null && bombGrid[x][y])
                    statusGrid[x][y] = -3;
            }
        }
    }

    private boolean checkWin() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (!bombGrid[x][y] && statusGrid[x][y] < 0)
                    return false;
            }
        }
        return true;
    }

    private void handleClick(javafx.scene.input.MouseEvent e) {
        int cellSize = (int) (canvas.getWidth() / size);
        int x = (int) (e.getX() / cellSize);
        int y = (int) (e.getY() / cellSize);
        if (x < 0 || x >= size || y < 0 || y >= size)
            return;
        if (e.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
            openCell(x, y);
            if (checkWin()) {
                revealAllBombs();
                drawGame();
                showError("Победа!");
                return;
            }
        } else if (e.getButton() == javafx.scene.input.MouseButton.SECONDARY) {
            toggleFlag(x, y);
        }
        drawGame();
    }

    private void drawGame() {
        var grc = canvas.getGraphicsContext2D();
        int cellSize = (int) (canvas.getWidth() / size);
        grc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                double px = x * cellSize, py = y * cellSize;
                if (statusGrid[x][y] >= 0) {
                    grc.setFill(javafx.scene.paint.Color.LIGHTGRAY);
                } else {
                    grc.setFill(javafx.scene.paint.Color.WHITE);
                }
                grc.fillRect(px, py, cellSize, cellSize);
                grc.setStroke(javafx.scene.paint.Color.GRAY);
                grc.strokeRect(px, py, cellSize, cellSize);

                if (statusGrid[x][y] == -2) {
                    grc.setFill(javafx.scene.paint.Color.RED);
                    grc.fillText("F", px + cellSize / 2 - 5, py + cellSize / 2 + 5);
                    continue;
                }

                if (statusGrid[x][y] == -3) {
                    grc.setFill(javafx.scene.paint.Color.BLACK);
                    grc.fillOval(px + 5, py + 5, cellSize - 10, cellSize - 10);
                    continue;
                }

                if (statusGrid[x][y] > 0) {
                    grc.setFill(javafx.scene.paint.Color.BLUE);
                    grc.setFont(new javafx.scene.text.Font(Math.max(12, cellSize / 2)));
                    grc.fillText(Integer.toString(statusGrid[x][y]), px + cellSize / 2 - 5, py + cellSize / 2 + 5);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
