package lab.infra.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lab.algebra.infra.view.SettingsView;

public class SettingsViewImpl implements SettingsView {

    @Override
    public void open(SettingsCallback callback) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Настройки игры");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        grid.add(new Label("Длина стороны поля (5-100):"), 0, 0);
        TextField sizeField = new TextField("10");
        grid.add(sizeField, 1, 0);

        grid.add(new Label("Количество бомб:"), 0, 1);
        TextField bombsField = new TextField("25");
        grid.add(bombsField, 1, 1);

        grid.add(new Label("Отображение:"), 0, 2);
        ComboBox<DisplayMode> displayModeBox = new ComboBox<>();
        displayModeBox.getItems().addAll(DisplayMode.CANVAS, DisplayMode.GRID);
        displayModeBox.setValue(DisplayMode.CANVAS);
        grid.add(displayModeBox, 1, 2);

        Button okBtn = new Button("Применить");
        grid.add(okBtn, 1, 3);

        okBtn.setOnAction(e -> {
            try {
                int size = Integer.parseInt(sizeField.getText());
                int bombs = Integer.parseInt(bombsField.getText());

                if (size < 5 || size > 100) {
                    showError("Размер поля должен быть от 5 до 100");
                    return;
                }
                if (bombs < 1 || bombs >= size * size - 9) {
                    showError("Неверное количество бомб");
                    return;
                }

                DisplayMode mode = displayModeBox.getValue();
                if (callback != null) {
                    callback.onSettingsConfirmed(size, bombs, mode);
                }
                stage.close();
            } catch (NumberFormatException ex) {
                showError("Введите число");
            }
        });

        stage.setScene(new Scene(grid, 350, 200));
        stage.showAndWait();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }
}
