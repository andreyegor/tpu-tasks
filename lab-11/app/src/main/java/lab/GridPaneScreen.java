
package lab;

import nonogram.GridScreen;

import java.util.function.BiConsumer;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class GridPaneScreen implements GridScreen {

    private final GridPane gridPane;
    private StackPane[][] cells = new StackPane[0][0];
    private Rectangle[][] rects = new Rectangle[0][0];
    private Label[][] labels = new Label[0][0];

    private double cellSize = 28.0;

    public GridPaneScreen(GridPane gridPane) {
        this.gridPane = gridPane;
        this.gridPane.setHgap(0);
        this.gridPane.setVgap(0);
    }

    public void setCellSize(double size) {
        this.cellSize = size;
    }

    @Override
    public void blank(Integer ox, Integer oy) {
        if (ox == null || oy == null || ox < 0 || oy < 0) {
            throw new IllegalArgumentException("invalid dimensions");
        }
        gridPane.getChildren().clear();
        cells = new StackPane[ox][oy];
        rects = new Rectangle[ox][oy];
        labels = new Label[ox][oy];

        for (int x = 0; x < ox; x++) {
            for (int y = 0; y < oy; y++) {
                Rectangle rect = new Rectangle(cellSize, cellSize, Color.WHITE);
                rect.setStroke(Color.LIGHTGRAY);
                rect.setStrokeWidth(0.5);

                Label lbl = new Label("");
                lbl.setFont(new Font(11));
                lbl.setMouseTransparent(true);

                StackPane cell = new StackPane();
                cell.setPrefSize(cellSize, cellSize);
                cell.getChildren().addAll(rect, lbl);
                StackPane.setAlignment(lbl, Pos.CENTER);

                GridPane.setColumnIndex(cell, x);
                GridPane.setRowIndex(cell, y);
                gridPane.getChildren().add(cell);
                
                cells[x][y] = cell;
                rects[x][y] = rect;
                labels[x][y] = lbl;
            }
        }
    }

    @Override
    public void setOnCellClick(BiConsumer<Integer, Integer> handler) {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                final int fx = x;
                final int fy = y;
                var cell = cells[x][y];
                if (handler != null) {
                    cell.setOnMouseClicked(e -> handler.accept(fx, fy));
                } else {
                    cell.setOnMouseClicked(null);
                }
            }
        }
    }

    @Override
    public void set(Integer x, Integer y) {
        if (!validCoords(x, y))
            return;
        Platform.runLater(() -> applyFill(x, y, true));
    }

    @Override
    public void unset(Integer x, Integer y) {
        if (!validCoords(x, y))
            return;
        Platform.runLater(() -> applyFill(x, y, false));
    }

    @Override
    public void hint(Integer x, Integer y, Integer hint) {
        if (!validCoords(x, y))
            return;
        final String text = hint == null ? "" : hint.toString();
        Platform.runLater(() -> labels[x][y].setText(text));
    }

    private void applyFill(int x, int y, boolean value) {
        rects[x][y].setFill(value ? Color.BLACK : Color.WHITE);
    }

    private boolean validCoords(Integer x, Integer y) {
        if (x == null || y == null)
            return false;
        if (x < 0 || y < 0)
            return false;
        if (x >= rects.length)
            return false;
        if (rects[x] == null)
            return false;
        if (y >= rects[x].length)
            return false;
        return true;
    }
}
