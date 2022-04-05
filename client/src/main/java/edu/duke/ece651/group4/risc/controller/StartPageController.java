package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.SelectServerView;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Start page controller for the whole game
 */
public class StartPageController {
    @FXML
    Button start_btn;
    @FXML
    Button exit_btn;
    @FXML
    Text title;
    /**
     * initialize the controller
     */
    @FXML
    public void initialize(){
        title.setFontSmoothingType(FontSmoothingType.LCD);
        exit_btn.addEventHandler(MouseEvent.MOUSE_ENTERED, e->exit_btn.setStyle("-fx-border-color: blue;-fx-background-color: transparent;-fx-text-fill: black"));
        exit_btn.addEventHandler(MouseEvent.MOUSE_EXITED,e->exit_btn.setStyle("-fx-border-color: black;-fx-background-color: transparent;-fx-text-fill: black"));
        start_btn.addEventHandler(MouseEvent.MOUSE_ENTERED, e->start_btn.setStyle("-fx-border-color: blue;-fx-background-color: transparent;-fx-text-fill: black"));
        start_btn.addEventHandler(MouseEvent.MOUSE_EXITED,e->start_btn.setStyle("-fx-border-color: black;-fx-background-color: transparent;-fx-text-fill: black"));
    }
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
        SelectServerView view = new SelectServerView();//jump to select server page
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
