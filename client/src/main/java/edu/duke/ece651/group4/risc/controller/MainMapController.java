package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MainMapController {
    private Client client;//model
    @FXML
    Text player_color;
    public MainMapController(Client client){
        this.client = client;
        System.out.println(client.getPlayerId());
    }



    /**
     * tell user they are green player or blue player
     * @param id player id, if 0, then green player, else blue player
     */
    public void displayPlayerMsg(){
        String player_name = client.getMap().getPlayerName(client.getPlayerId());
        player_color.setText(player_name);
    }
}
