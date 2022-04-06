package edu.duke.ece651.group4.risc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

public class EndGameController {
    @FXML
    Text end_msg;

    EndGameController(){

    }


    public void setup(String msg){
        end_msg.setText(msg);
    }


    @FXML
    public void backToMain(ActionEvent ae){
        Button source = (Button) ae.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.close();
        URL xmlResource = getClass().getResource("/ui/StartPage.fxml");

        URL cssResource = getClass().getResource("/ui/button.css");

        AnchorPane gp = FXMLLoader.load(xmlResource);
        Scene scene = new Scene(gp, 640, 480);
        Stage stage = new Stage();
        scene.getStylesheets().add(cssResource.toString());
        stage.setTitle("Really Interesting Strategic Conquest");
        stage.setScene(scene);
        stage.show();
    }
}
