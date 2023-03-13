package com.example.psp;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController  implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private HBox buttonHbox;
    @FXML
    private StackPane stackPaneHome;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stackPaneHome.widthProperty().addListener((observable, oldValue, newValue) -> {
            buttonHbox.setSpacing(newValue.doubleValue() / 3);
        });
        System.out.println("blabla");

    }
}