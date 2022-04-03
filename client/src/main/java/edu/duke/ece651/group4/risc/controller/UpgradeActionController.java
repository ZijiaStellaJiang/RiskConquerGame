package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.ActionParser;
import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.Player;
import edu.duke.ece651.group4.risc.shared.Territory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UpgradeActionController {
    @FXML
    ChoiceBox<String> source;

    @FXML
    ChoiceBox<Integer> from;

    @FXML
    TextField levelup;

    @FXML
    TextField num;

    @FXML
    Button done;
    @FXML
    Text alert;

    private Client client;
    private ObservableList<String> sources;

    public  UpgradeActionController(Client client){
        this.client = client;
    }

    public ArrayList<Territory<Character>> displayMyTerritory() {
        Map<Character> myMap = client.getMap();
        ArrayList<Player<Character>> players = myMap.getMyPlayers();
//        for (Territory<Character> terr : players.get(client.getPlayerId()).getMyTerritories()) {
//            System.out.println(terr.getName());
//        }
        return players.get(client.getPlayerId()).getMyTerritories();
    }

    /**
     * set up source destination
     */
    public void setup(){
        sources = FXCollections.observableArrayList();
        for(Territory<Character> terr:displayMyTerritory()){
            sources.add(terr.getName());
        }
        source.setItems(sources);
        ObservableList<Integer> level = FXCollections.observableArrayList();
        level.addAll(0,1,2,3,4,5,6);
        from.setItems(level);

    }

    @FXML
    public void done(){
        String source_terr = source.getValue();
        Integer upgrade_from = from.getValue();
        Integer level_up = Integer.parseInt(levelup.getText());
        //TODO check the format of unit from

        Integer unit_num = Integer.parseInt(num.getText());
        System.out.println(source_terr + " " + upgrade_from+ " " + level_up + " " + unit_num);
        try {
            ActionParser newAction = new ActionParser("UPDATE", source_terr, null, unit_num, upgrade_from, level_up);
            String result = client.addOrder(newAction);
            if(result!=null){
                alert.setText(result);
            }else{
                Stage primaryStage = (Stage) source.getScene().getWindow();
                primaryStage.close();
            }
        }catch (IllegalArgumentException e) {
            alert.setText("Invalid input");
            return;
        }
    }

}
