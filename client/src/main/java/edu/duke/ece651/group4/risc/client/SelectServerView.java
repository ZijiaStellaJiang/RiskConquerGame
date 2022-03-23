package edu.duke.ece651.group4.risc.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectServerView {

    public void showMain() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/ui/SelectServer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


    }


}
