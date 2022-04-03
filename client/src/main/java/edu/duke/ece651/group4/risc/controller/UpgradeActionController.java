package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.Player;
import edu.duke.ece651.group4.risc.shared.Territory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class UpgradeActionController {
    @FXML
    ChoiceBox<String> source;

    @FXML
    TextField from;

    @FXML
    TextField to;

    @FXML
    TextField num;

    @FXML
    Button done;

    private Client client;
    private ObservableList<String> sources;

    public  UpgradeActionController(Client client){
        this.client = client;
    }

    public ArrayList<Territory<Character>> displayMyTerritory() {
        Map<Character> myMap = client.getMap();
        ArrayList<Player<Character>> players = myMap.getMyPlayers();
        for (Territory<Character> terr : players.get(client.getPlayerId()).getMyTerritories()) {
            System.out.println(terr.getName());
        }
        return players.get(client.getPlayerId()).getMyTerritories();
    }
    public void setup(){
        sources = FXCollections.observableArrayList();
        for(Territory<Character> terr:displayMyTerritory()){
            sources.add(terr.getName());
        }
        source.setItems(sources);
    }

}
