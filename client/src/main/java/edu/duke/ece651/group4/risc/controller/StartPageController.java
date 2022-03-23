package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.client.MapGUIView;
import edu.duke.ece651.group4.risc.client.SelectServerView;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class StartPageController {
    @FXML
    Button start_btn;
    @FXML
    Button exit_btn;
    /**
     * Deal with exit and start botton
     * if start button pressed, jump to display map view, if exit button pressed, exit the program
     * @param ae click botton
     * @throws IOException
     */
    @FXML
    public void onButtonStart(ActionEvent ae) {
        Object source = ae.getSource();
            Button btn = (Button) source;
            Stage primaryStage = (Stage) btn.getScene().getWindow();
                //initialize game
            SelectServerView view = new SelectServerView();
        try {
            view.showMain();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.close();
    }

    /**
     * When click exit button, the whole program exits
     * @param ae on click action
     */
    @FXML
    public void onButtonExit(ActionEvent ae){
        Object source = ae.getSource();
            Button btn = (Button) source;
            System.out.println(btn.getText() + " pressed");
            Stage primaryStage = (Stage) btn.getScene().getWindow();
            primaryStage.close();
        }
}
