package lab;

import javafx.application.Application;
import javafx.stage.Stage;
import lab.infra.controller.CalculatorController;
import lab.infra.view.CalculatorView;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        CalculatorController controller = new CalculatorController();
        CalculatorView view = new CalculatorView(stage, controller);
        view.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
