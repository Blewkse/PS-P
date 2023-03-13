module com.example.psp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens com.example.psp to javafx.fxml;
    exports com.example.psp;
}