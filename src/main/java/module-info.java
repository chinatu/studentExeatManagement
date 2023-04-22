module com.nayacodes.loginjfx.demologin {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;


    opens com.nayacodes.loginjfx.demologin to javafx.fxml;
    exports com.nayacodes.loginjfx.demologin;
    exports com.nayacodes.loginjfx.demologin.controllers;
    opens com.nayacodes.loginjfx.demologin.controllers to javafx.fxml;
}