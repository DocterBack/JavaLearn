module ru.specialist.java.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ru.specialist.java.fx to javafx.fxml;
    opens ru.specialist.java.fx.controller to javafx.fxml;
    exports ru.specialist.java.fx;
    exports ru.specialist.java.fx.controller to javafx.fxml;
}