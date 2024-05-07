module ge.never47 {
    requires javafx.controls;
    requires javafx.fxml;

    opens ge.never47 to javafx.fxml;
    opens ge.never47.controllers to javafx.fxml;
    exports ge.never47;
}