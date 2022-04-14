package edu.duke.ece651.group4.risc.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SelectServerView {
    /**
     * take control of main page
     * @throws IOException
     */
    public void showMain() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/ui/SelectServer.fxml"));
        URL cssResource = getClass().getResource("/ui/button.css");
        Scene scene = new Scene(root, 640, 480);
        scene.getStylesheets().add(cssResource.toString());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
