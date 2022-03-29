package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.shared.ActionParser;
import edu.duke.ece651.group4.risc.shared.Territory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MoveActionController {
    @FXML
    ChoiceBox<String> source;
    @FXML
    ChoiceBox<String> destination;
    @FXML
    ChoiceBox<Integer> unit_level;
    @FXML
    TextField unit_num;
    @FXML
    Text alert;
    private int playerId;
    private ArrayList<Territory<Character>> myTerr;
    private ObservableList<String> sources;

    public MoveActionController(int playerId, ArrayList<Territory<Character>> myTerr) {
        this.playerId = playerId;
        this.myTerr = myTerr;
    }


    public void setup() {
        //set source territory name
        sources = FXCollections.observableArrayList();
        for(Territory<Character> terr:myTerr){
            sources.add(terr.getName());
        }
        source.setItems(sources);
        destination.setItems(sources);//TODO: change to destination
        //set unit level
        ObservableList<Integer> level = FXCollections.observableArrayList();
        level.addAll(0,1,2,3,4,5,6);
        unit_level.setItems(level);
    }

    @FXML
    public void done(){
        String source_terr = source.getValue();
        String dest_terr =destination.getValue();
        Integer level = unit_level.getValue();
        String num = unit_num.getText();
        System.out.println(source_terr + " " + dest_terr + " " + level + " " + num);
        //TODO: check vadility

        MainMapController.actions.add(new ActionParser("MOVE", source_terr, dest_terr, Integer.parseInt(num)));

        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.close();
    }
}
