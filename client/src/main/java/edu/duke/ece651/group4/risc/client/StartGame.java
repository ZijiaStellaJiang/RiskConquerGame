package edu.duke.ece651.group4.risc.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartGame extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
    Scene scene = new Scene(new StackPane(l), 640, 480);
    stage.setTitle("Really Interesting Strategic Conquest");
    stage.setScene(scene);
    stage.show();
    //stage.close();
  }
}
