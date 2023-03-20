package com.example.psp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WebviewController implements Initializable {

    private Stage stage;
    @FXML
    WebView webView;
    private WebEngine webEngine;

    @FXML
    private Button boutonRetour;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load("https://play.thingz.co/galaxia");
        boutonRetour.setOnAction((event) -> {
            FXMLLoader fxmlLoader = new FXMLLoader(WebviewController.class.getResource("hello-view.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 900, 500);
                HomeController homeController = fxmlLoader.getController();
                homeController.setStage(stage);
                stage.setTitle("Bienvenue !");
                stage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }});
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
