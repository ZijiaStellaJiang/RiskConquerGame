package edu.duke.ece651.group4.risc.controller;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class StartPageController {
    @FXML
    public void onButton(ActionEvent ae){
        Object source = ae.getSource();
        if(source instanceof Button){
            Button btn = (Button) source;
            System.out.println(btn.getText()+ " pressed");
        }
    }
}
