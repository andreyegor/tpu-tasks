package lab.infra.controller;

import lab.algebra.infra.controller.ScreenController;
import lab.core.model.Click;
import lab.core.model.Pos;

import java.util.function.Consumer;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;

public class CanvasController implements ScreenController {

    private Consumer<Click> handler;
    private final Canvas canvas;
    private double cellSize;

    public CanvasController(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void init(int size) {
        this.cellSize = canvas.getWidth() / size;

        canvas.setOnMouseClicked(e -> {
            if (handler == null)
                return;

            var x = (int) (e.getX() / cellSize);
            var y = (int) (e.getY() / cellSize);

            var isRight = (e.getButton() == MouseButton.SECONDARY);

            handler.accept(
                    new Click(new Pos(x, y), isRight));
        });
    }

    @Override
    public void setClickHandler(Consumer<Click> handler) {
        this.handler = handler;
    }
}
