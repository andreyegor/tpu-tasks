package lab.infra.view;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import lab.algebra.infra.view.MessagesView;

public class MessagesViewImpl implements MessagesView {

    @Override
    public void win(String str) {
        show(Alert.AlertType.CONFIRMATION, str);
    }

    @Override
    public void loose(String str) {
        show(Alert.AlertType.WARNING, str);
    }

    @Override
    public void error(String str) {
        show(Alert.AlertType.ERROR, str);
    }

    private void show(Alert.AlertType type, String message) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> showAlert(type, message));
        } else {
            showAlert(type, message);
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }
}
