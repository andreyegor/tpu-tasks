package lab.infra.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lab.algebra.infra.controller.ScreenController;
import lab.core.model.Click;
import lab.core.model.Pos;

public class GridController implements ScreenController {

    private final GridPane grid;
    private Consumer<Click> handler;

    private final Map<Node, EventHandler<? super MouseEvent>> originalHandlers = new HashMap<>();
    private final Map<Node, EventHandler<MouseEvent>> internalHandlers = new HashMap<>();

    public GridController(GridPane grid) {
        this.grid = grid;
    }

    @Override
    public void init(int size) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> initOnFx(size));
        } else {
            initOnFx(size);
        }
    }

    @Override
    public void setClickHandler(Consumer<Click> handler) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> this.handler = handler);
        } else {
            this.handler = handler;
        }
    }

    private void unbindAllOnFx() {
        if (internalHandlers.isEmpty() && originalHandlers.isEmpty())
            return;

        for (Node node : internalHandlers.keySet()) {
            EventHandler<? super MouseEvent> orig = originalHandlers.get(node);
            node.setOnMouseClicked(orig);
        }
        internalHandlers.clear();
        originalHandlers.clear();
    }

    private void initOnFx(int size) {
        unbindAllOnFx();

        for (Node node : grid.getChildren()) {
            Integer col = GridPane.getColumnIndex(node);
            Integer row = GridPane.getRowIndex(node);
            final int x = (col == null) ? 0 : col;
            final int y = (row == null) ? 0 : row;

            originalHandlers.put(node, node.getOnMouseClicked());

            EventHandler<MouseEvent> internal = e -> {
                if (handler == null)
                    return;
                boolean right = e.getButton() == MouseButton.SECONDARY;
                handler.accept(new Click(new Pos(x, y), right));
                e.consume();
            };

            internalHandlers.put(node, internal);
            node.setOnMouseClicked(internal);
        }
    }
}
