module lab10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires jdk.unsupported;

    opens lab to javafx.fxml;
    exports lab;
}
