package edu.duke.ece651.group4.risc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TerritoryDetailController {

    @FXML
    TextArea detail;

    public TerritoryDetailController(){

    }

    public void setup(String content){
        detail.setText(content);
    }
}
