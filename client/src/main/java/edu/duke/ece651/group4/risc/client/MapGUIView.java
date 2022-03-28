package edu.duke.ece651.group4.risc.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MapGUIView {
    public MapGUIView(){

    }
    public void showMain() throws IOException{

        AnchorPane root = FXMLLoader.load(getClass().getResource("/ui/Map.fxml"));
        URL cssResource = getClass().getResource("/ui/button.css");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssResource.toString());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }
}
