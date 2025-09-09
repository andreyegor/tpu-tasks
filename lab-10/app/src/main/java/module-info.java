module lab10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens lab to javafx.fxml;
    exports lab;
}
