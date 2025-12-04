package lab.infra.view;

import lab.algebra.infra.view.ScreenView;
import lab.core.model.Pos;
import lab.core.model.State.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CanvasView implements ScreenView {

    private final Canvas canvas;
    private int cellSize;

    public CanvasView(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void init(int size) {
        this.cellSize = (int) (canvas.getWidth() / size);
        clear();
    }

    @Override
    public void set(Pos pos, State state) {
        drawCell(pos.x(), pos.y(), state);
    }

    private void clear() {
        var g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawCell(int x, int y, State state) {
        GraphicsContext g = canvas.getGraphicsContext2D();

        double px = x * cellSize;
        double py = y * cellSize;

        g.setFill(new javafx.scene.paint.LinearGradient(
                x * cellSize, y * cellSize,
                (x + 1) * cellSize, (y + 1) * cellSize - 1,
                false,
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0, javafx.scene.paint.Color.WHITE),
                new javafx.scene.paint.Stop(1, javafx.scene.paint.Color.GRAY)));

        switch (state) {
            case Fog f -> {
                g.fillRect(px, py, cellSize, cellSize);
                g.fillRect(px, py, cellSize, cellSize);
            }
            case Flag f -> {
                g.fillRect(px, py, cellSize, cellSize);
                g.setFont(new javafx.scene.text.Font(cellSize * 0.5));
                g.setFill(javafx.scene.paint.Color.RED);
                g.fillText("🚩", px + cellSize * 0.25, py + cellSize * 0.75);
            }
            case Bomb b -> {
                g.fillRect(px, py, cellSize, cellSize);
                g.setFont(new javafx.scene.text.Font(cellSize * 0.5));
                g.setFill(javafx.scene.paint.Color.BLACK);
                g.fillText("💣", px + cellSize * 0.25, py + cellSize * 0.75);
            }
            case Clear c -> {
                g.setFill(javafx.scene.paint.Color.DARKGRAY);
                g.fillRect(px, py, cellSize, cellSize);
                g.setFill(javafx.scene.paint.Color.BLUE);
                g.setFont(new javafx.scene.text.Font(cellSize * 0.5));
                g.fillText(c.nearby() != 0 ? Integer.toString(c.nearby()) : "",
                        px + cellSize * 0.25,
                        py + cellSize * 0.75);
            }
        }

        g.setStroke(Color.WHITE);
        g.strokeRect(px, py, cellSize, cellSize);
    }
}
