package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.ActionParser;
import edu.duke.ece651.group4.risc.shared.Player;
import edu.duke.ece651.group4.risc.shared.Territory;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AttackActionController {
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
    private Client client;
    private Player<Character> player;
    public AttackActionController(Client client, int playerId, ArrayList<Territory<Character>> myTerr, Player<Character> player) {
        this.client = client;
        this.playerId = playerId;
        this.myTerr = myTerr;
        this.player = player;
    }

    public void setup() {
        //set source territory name
        sources = FXCollections.observableArrayList();
        for(Territory<Character> terr:myTerr){
            sources.add(terr.getName());
        }
        source.setItems(sources);
        source.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    String select_source = source.getItems().get((Integer) new_val);
                    System.out.println("chosen source: " + select_source);
                    //TODO: refactor
                    ArrayList<Territory<Character>> dest = player.findDestinations(client.getMap().findTerritory(select_source), false);
                    if(dest!=null) {
                        ObservableList<String> dest_names = FXCollections.observableArrayList();
                        for (Territory<Character> t : dest) {
                            dest_names.add(t.getName());
                        }
                        destination.setItems(dest_names);
                    }else{

                    }
                });
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
        try {
            ActionParser newAction = new ActionParser("ATTACK", source_terr, dest_terr, Integer.parseInt(num));
            if(client.addOrder(newAction)==false){
                alert.setText("Invalid input");
            }
        }catch (IllegalArgumentException e) {
            alert.setText("Invalid input");
            return;
        }

        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.close();
    }
}
