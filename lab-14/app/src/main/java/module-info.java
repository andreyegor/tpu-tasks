module lab.calculator {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.base;

    requires jdk.unsupported;

    opens lab to javafx.fxml;
    opens lab.infra.view to javafx.fxml;
    opens lab.infra.controller to javafx.fxml;

    exports lab;
    exports lab.infra.controller;
    exports lab.infra.view;
}
