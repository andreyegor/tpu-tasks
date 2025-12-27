package lab.infra.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import lab.infra.controller.CalculatorController;

public class CalculatorView {
    private final CalculatorController controller;
    private Label expressionLabel;
    private Label resultLabel;
    private final Stage stage;
    
    public CalculatorView(Stage stage, CalculatorController controller) {
        this.stage = stage;
        this.controller = controller;
    }
    
    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14;");
        
        VBox displayPanel = createDisplayPanel();
        root.setTop(displayPanel);
        
        GridPane buttonPanel = createButtonPanel();
        root.setCenter(buttonPanel);
        BorderPane.setMargin(buttonPanel, new Insets(0));
        
        Scene scene = new Scene(root, 450, 600);
        stage.setScene(scene);
        stage.setTitle("Калькулятор");
        stage.setResizable(false);
        
        controller.addObserver(ctrl -> updateDisplay());
        
        stage.show();
        
        updateDisplay();
    }
    
    private VBox createDisplayPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-border-color: #ccc; -fx-border-width: 0 0 2 0;");
        panel.setAlignment(Pos.BOTTOM_RIGHT);
        
        expressionLabel = new Label();
        expressionLabel.setFont(Font.font("Arial", 18));
        expressionLabel.setWrapText(false);
        expressionLabel.setStyle("-fx-text-fill: #333;");
        
        resultLabel = new Label();
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        resultLabel.setStyle("-fx-text-fill: #0066cc;");
        
        panel.getChildren().addAll(expressionLabel, resultLabel);
        return panel;
    }
    
    private GridPane createButtonPanel() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setHgap(3);
        grid.setVgap(3);
        grid.setStyle("-fx-border-color: transparent;");
        
        addButton(grid, 0, 0, "√", this::handleSqrt, "-fx-font-size: 16; -fx-padding: 20;");
        addButton(grid, 1, 0, "(", this::handleOpenParen, "-fx-font-size: 16; -fx-padding: 20;");
        addButton(grid, 2, 0, ")", this::handleCloseParen, "-fx-font-size: 16; -fx-padding: 20;");
        addButton(grid, 3, 0, "CE", this::handleClear, "-fx-font-size: 14; -fx-text-fill: white; -fx-base: #ff6b6b; -fx-padding: 20;");
        
        addButton(grid, 0, 1, "7", () -> handleDigit("7"), "-fx-font-size: 18; -fx-padding: 20;");
        addButton(grid, 1, 1, "8", () -> handleDigit("8"), "-fx-font-size: 18; -fx-padding: 20;");
        addButton(grid, 2, 1, "9", () -> handleDigit("9"), "-fx-font-size: 18; -fx-padding: 20;");
        addButton(grid, 3, 1, "^", () -> handleOperator("^"), "-fx-font-size: 16; -fx-base: #ffe4cc; -fx-padding: 20;");
        
        addButton(grid, 0, 2, "4", () -> handleDigit("4"), "-fx-font-size: 18; -fx-padding: 20;");
        addButton(grid, 1, 2, "5", () -> handleDigit("5"), "-fx-font-size: 18; -fx-padding: 20;");
        addButton(grid, 2, 2, "6", () -> handleDigit("6"), "-fx-font-size: 18; -fx-padding: 20;");
        addButton(grid, 3, 2, "*", () -> handleOperator("*"), "-fx-font-size: 16; -fx-base: #ffe4cc; -fx-padding: 20;");
        
        addButton(grid, 0, 3, "1", () -> handleDigit("1"), "-fx-font-size: 18; -fx-padding: 20;");
        addButton(grid, 1, 3, "2", () -> handleDigit("2"), "-fx-font-size: 18; -fx-padding: 20;");
        addButton(grid, 2, 3, "3", () -> handleDigit("3"), "-fx-font-size: 18; -fx-padding: 20;");
        addButton(grid, 3, 3, "-", () -> handleOperator("-"), "-fx-font-size: 16; -fx-base: #ffe4cc; -fx-padding: 20;");
        
        addButton(grid, 0, 4, "0", () -> handleDigit("0"), "-fx-font-size: 18; -fx-padding: 20;");
        addButton(grid, 1, 4, ".", () -> handleDigit("."), "-fx-font-size: 18; -fx-padding: 20;");
        addButton(grid, 2, 4, "⌫", this::handleBackspace, "-fx-font-size: 16; -fx-text-fill: white; -fx-base: #ff9999; -fx-padding: 20;");
        addButton(grid, 3, 4, "+", () -> handleOperator("+"), "-fx-font-size: 16; -fx-base: #ffe4cc; -fx-padding: 20;");
        
        Button equalButton = new Button("=");
        GridPane.setColumnSpan(equalButton, 4);
        GridPane.setRowIndex(equalButton, 5);
        GridPane.setColumnIndex(equalButton, 0);
        equalButton.setMaxWidth(Double.MAX_VALUE);
        equalButton.setPrefHeight(50);
        equalButton.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-base: #4CAF50; -fx-padding: 20;");
        equalButton.setOnAction(e -> handleCalculate());
        grid.add(equalButton, 0, 5, 4, 1);
        
        return grid;
    }
    
    private void addButton(GridPane grid, int col, int row, String text, Runnable action, String style) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setPrefHeight(60);
        button.setStyle(style);
        button.setOnAction(e -> action.run());
        GridPane.setColumnSpan(button, 1);
        GridPane.setRowSpan(button, 1);
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
        GridPane.setHgrow(button, Priority.ALWAYS);
        GridPane.setVgrow(button, Priority.ALWAYS);
        grid.add(button, col, row);
    }
    
    private void handleDigit(String digit) {
        controller.addDigit(digit);
    }
    
    private void handleOperator(String operator) {
        controller.addOperator(operator);
    }
    
    private void handleSqrt() {
        controller.addOperator("√");
    }
    
    private void handleOpenParen() {
        controller.addOpenParen();
    }
    
    private void handleCloseParen() {
        controller.addCloseParen();
    }
    
    private void handleBackspace() {
        controller.backspace();
    }
    
    private void handleClear() {
        controller.clear();
    }
    
    private void handleCalculate() {
        controller.calculate();
    }
    
    private void updateDisplay() {
        Platform.runLater(() -> {
            if (expressionLabel != null && resultLabel != null) {
                String expr = controller.getExpressionString();
                String result = controller.getIntermediateResult();
                expressionLabel.setText(expr);
                resultLabel.setText(result);
            }
        });
    }
}
