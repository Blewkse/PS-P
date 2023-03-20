package com.example.psp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private Stage stage;
    @FXML
    private Label welcomeText;
    @FXML
    private HBox buttonHbox;
    @FXML
    private StackPane stackPaneHome;

    @FXML
    private Button buttonProgram;
    @FXML
    private Button buttonUpdateCart;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stackPaneHome.widthProperty().addListener((observable, oldValue, newValue) -> {
            buttonHbox.setSpacing(newValue.doubleValue() / 3);
        }
        );
        buttonProgram.setOnAction((event)  -> {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("galaxia-website.fxml"));
            try {
               Scene scene = new Scene(fxmlLoader.load(), 900, 500);
                WebviewController webviewController = fxmlLoader.getController();
                webviewController.setStage(stage);
                stage.setTitle("Programmation de la galaxia");
                stage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }  );
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

}