package edu.duke.ece651.group4.risc.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class StartGame extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    URL xmlResource = getClass().getResource("/ui/StartPage.fxml");

    URL cssResource = getClass().getResource("/ui/button.css");

    GridPane gp = FXMLLoader.load(xmlResource);
    Scene scene = new Scene(gp, 640, 480);
    scene.getStylesheets().add(cssResource.toString());
    stage.setTitle("Really Interesting Strategic Conquest");
    stage.setScene(scene);
    stage.show();
  }
}
