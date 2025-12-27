package lab.infra.view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lab.algebra.infra.view.ScreenView;
import lab.core.model.Grid;
import lab.core.model.Pos;
import lab.core.model.State.Bomb;
import lab.core.model.State.Clear;
import lab.core.model.State.Flag;
import lab.core.model.State.Fog;
import lab.core.model.State.State;
import javafx.scene.control.OverrunStyle;

public class GridView implements ScreenView {

    private final GridPane grid;
    private final double totalPixels;
    private Grid<Button> buttons;
    private int size;

    public GridView(GridPane grid, double totalPixels) {
        this.grid = grid;
        this.totalPixels = totalPixels;
    }

    @Override
    public void init(int size) {
        this.size = size;

        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        buttons = new Grid<Button>(size, size);

        double cell = Math.max(1.0, totalPixels / (double) size);

        grid.setPrefSize(totalPixels, totalPixels);
        grid.setMinSize(totalPixels, totalPixels);
        grid.setMaxSize(totalPixels, totalPixels);

        for (int i = 0; i < size; i++) {
            javafx.scene.layout.RowConstraints rc = new javafx.scene.layout.RowConstraints();
            rc.setPrefHeight(cell);
            rc.setMinHeight(cell);
            rc.setMaxHeight(cell);
            rc.setVgrow(javafx.scene.layout.Priority.NEVER);
            grid.getRowConstraints().add(rc);

            javafx.scene.layout.ColumnConstraints cc = new javafx.scene.layout.ColumnConstraints();
            cc.setPrefWidth(cell);
            cc.setMinWidth(cell);
            cc.setMaxWidth(cell);
            cc.setHgrow(javafx.scene.layout.Priority.NEVER);
            grid.getColumnConstraints().add(cc);
        }

        for (var pos : buttons.iteratePos()) {
            Button b = new Button();

            b.setPrefSize(cell, cell);
            b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            b.setFocusTraversable(false);
            b.setFont(new Font(Math.max(8, cell * 0.5)));
            b.setUserData(pos);

            b.setTextOverrun(OverrunStyle.CLIP);
            b.setFont(new Font(size * 1.10));

            grid.add(b, pos.x(), pos.y());
            GridPane.setHalignment(b, HPos.CENTER);
            GridPane.setValignment(b, VPos.CENTER);
            GridPane.setHgrow(b, javafx.scene.layout.Priority.ALWAYS);
            GridPane.setVgrow(b, javafx.scene.layout.Priority.ALWAYS);

            b.setStyle(
                    "-fx-padding: 0; -fx-background-insets: 0; -fx-background-color: lightgray; -fx-border-color: #ffffff;");
            b.setOpacity(1.0);

            buttons.set(pos, b);
        }
    }

    @Override
    public void set(Pos pos, State state) {
        if (buttons == null)
            return;
        var x = pos.x();
        var y = pos.y();
        if (x < 0 || x >= size || y < 0 || y >= size)
            return;

        applyStateToButton(buttons.get(pos), state);
    }

    private void applyStateToButton(Button btn, State state) {
        if (state == null) {
            btn.setText("");
            btn.setDisable(false);
            btn.setMouseTransparent(false);
            btn.setStyle("-fx-background-color: lightgray; -fx-border-color: #ffffff;");
            btn.setTextFill(Color.BLACK);
            btn.setOpacity(1.0);
            return;
        }

        String baseFog = "-fx-background-color: linear-gradient(#ffffff, #d0d0d0); -fx-border-color: #ffffff;";
        String baseSimple = "-fx-background-color: lightgray; -fx-border-color: #ffffff;";

        switch (state) {
            case Fog f -> {
                btn.setText("");
                btn.setDisable(false);
                btn.setMouseTransparent(false);
                btn.setStyle(baseFog);
                btn.setTextFill(Color.BLACK);
                btn.setOpacity(1.0);
            }
            case Flag f -> {
                btn.setText("🚩");
                btn.setDisable(false);
                btn.setMouseTransparent(false);
                btn.setStyle(baseSimple);
                btn.setTextFill(Color.RED);
                btn.setOpacity(1.0);
            }
            case Bomb b -> {
                btn.setText("💣");
                btn.setDisable(true);
                btn.setMouseTransparent(true);
                btn.setStyle(baseSimple);
                btn.setTextFill(Color.BLACK);
                btn.setOpacity(1.0);
            }
            case Clear c -> {
                int n = c.nearby();

                if (n == 0) {
                    btn.setText("");
                } else {
                    btn.setText(Integer.toString(n));
                }

                btn.setDisable(true);
                btn.setMouseTransparent(true);
                btn.setStyle(baseSimple);

                Color textColor;
                switch (n) {
                    case 1 -> textColor = Color.BLUE;
                    case 2 -> textColor = Color.GREEN;
                    default -> textColor = Color.DARKRED;
                }
                btn.setTextFill(textColor);
                btn.setOpacity(1.0);
            }
        }
    }
}
