module com.example.grades {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.grades to javafx.fxml;
    exports com.example.grades;
}