module com.example.biblioteka {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.oracle.database.jdbc;
    opens com.example.biblioteka to javafx.fxml;
    exports com.example.biblioteka;
    exports connect;
    exports Controllers;
    opens Controllers to javafx.fxml;
}