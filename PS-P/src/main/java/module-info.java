module com.example.psp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.psp to javafx.fxml;
    exports com.example.psp;
}