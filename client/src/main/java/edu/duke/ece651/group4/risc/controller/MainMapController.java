package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.Action;
import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.Player;
import edu.duke.ece651.group4.risc.shared.Territory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainMapController {
    private Client client;//model

    @FXML
    Button oz;
    @FXML
    Button hogwarts;
    @FXML
    Button narnia;
    @FXML
    Button gondor;
    @FXML
    Button elantris;
    @FXML
    Button mordor;
    @FXML
    Tooltip oz_tooltip;
    @FXML
    Text player_color;
    @FXML
    AnchorPane background;
    public MainMapController(Client client){
        this.client = client;
        System.out.println(client.getPlayerId());
    }



    /**
     * tell user they are green player or blue player
     */
    public void displayPlayerMsg(){
        String player_name = client.getMap().getPlayerName(client.getPlayerId());
        player_color.setText(player_name);
    }

    /**
     * set border of territory according to which player the territory belong to
     */
    public void displayTerritoryBorder(){
        Map<Character> map = client.getMap();
        for (Player<Character> p: map.getMyPlayers()){
            for(Territory<Character> myTerri: p.getMyTerritories()){
                String terr_name = myTerri.getName().toUpperCase();
                for(Node element:background.getChildren()){
                    if(element instanceof Button){

                        if(element.getId()!=null){
                            Button btn = (Button) element;
                            String btn_name = btn.getText().toUpperCase();
                            if(btn_name.equals(terr_name)) {
                                String player_name = p.getName().toUpperCase();
                                if (player_name.equals("GREEN")) {
                                    btn.setStyle("-fx-border-color: green");
                                } else {
                                    btn.setStyle("-fx-border-color: blue");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @FXML
    public void displayTerritory(ActionEvent ae){
        Button source = (Button)ae.getSource();
        String territory_name = source.getId();
        System.out.println(territory_name);
        StringBuilder sb = new StringBuilder("Player: ");
        sb.append(territory_name);
        sb.append("\n");
        sb.append("Belong to: test\n");
        sb.append("Units information: \n");
        oz_tooltip.setText(sb.toString());
    }

    @FXML
    public void showMove(ActionEvent ae) throws IOException {
        Button source = (Button)ae.getSource();
        //show move page
        URL xmlResource = getClass().getResource("/ui/MoveAction.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        AnchorPane gp = FXMLLoader.load(xmlResource);
        Scene scene = new Scene(gp);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


}
