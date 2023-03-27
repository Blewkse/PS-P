package com.example.psp;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

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
    @FXML
    private Button bouton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            System.out.println(webEngine.getLoadWorker().stateProperty().toString());
            if(webEngine.getLoadWorker().stateProperty().getValue() == Worker.State.SUCCEEDED){
                webEngine.executeScript("console.log('Ouvrir la console.');");
                String script = "alert('Page loaded successfully!');";
                webEngine.executeScript(script);
                System.out.println("in");
            }

            setTimeout(() -> {if(webEngine.getLoadWorker().stateProperty().getValue() == Worker.State.RUNNING)
            webEngine.reload();
            }, 5000);


        });

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
        bouton.setOnAction((event) -> {
            System.out.println(webEngine.getLoadWorker().stateProperty().getValue());
            String script = "document.querySelector('button[title   =\"Tu es connecté au serveur de création de Thingz\"]').id = 'mon-bouton';";
            //webEngine.executeScript(script);
            //webEngine.executeScript("document.getElementById('mon-bouton').style.backgroundColor = 'green'");
//*[@id="HeaderFreeCreationMenu"]/div/div[2]/div[1]/div/button
        });
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public static Runnable setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                Platform.runLater(runnable);
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
        return runnable;
    }
}
