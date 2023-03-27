/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package com.example.psp;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author selmene
 */
public class HelloApplication extends Application {

    private static final String OS = System.getProperty("os.name").toLowerCase();
    public static boolean IS_WINDOWS = (OS.contains("win"));
    public static boolean IS_MAC = (OS.contains("mac"));
    public static boolean IS_UNIX = (OS.contains("nix") || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    @Override
    public void start(Stage primaryStage) {
        Path pathMac = Paths.get("/volumes/GALAXIA");

        Path pathWin = Paths.get("E:");
        String fileName = "/example.py";
        String fileContent = "# This is a Python file created by Java\nprint('Hello, world!')";

        if (IS_WINDOWS) {
            if (Files.exists(pathWin)) {
                System.out.println("This is Windows");
                try {
                    FileWriter writer = new FileWriter(pathWin + fileName);
                    writer.write(fileContent);
                    writer.close();
                    System.out.println("Python file created successfully!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("This is Windows but not connected");
            }
        } else if (IS_MAC) {
            if (Files.exists(pathMac)) {
                System.out.println("This is Mac");
                try {
                    FileWriter writer = new FileWriter(pathMac + fileName);
                    writer.write(fileContent);
                    writer.close();
                    System.out.println("Python file created successfully!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("This is Mac but not connected");
            }


        } else if (IS_UNIX) {
            if (Files.exists(pathWin)) {
                System.out.println("This is Linux or UNIX");
                WebView webView = new WebView();
                webView.getEngine().load("https://google.com");
                Scene scene = new Scene(webView, 800, 600);
                primaryStage.setScene(scene);
                primaryStage.show();
            } else {
                System.out.println("This is Linux or UNIX but not connected");
            }

        }



    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
