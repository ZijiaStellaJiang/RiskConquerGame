package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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
    @FXML
    Tooltip hogwarts_tooltip;
    @FXML
    Tooltip narnia_tooltip;
    @FXML
    Tooltip gondor_tooltip;
    @FXML
    Tooltip elantris_tooltip;
    @FXML
    Tooltip mordor_tooltip;
    @FXML
    Text wait_msg;
    static ArrayList<ActionParser> actions = new ArrayList<>();
    private  HashMap<Class<?>, Object> controllers;
    public MainMapController(Client client){
        this.client = client;
        System.out.println(client.getPlayerId());
        controllers = new HashMap<>();
    }



    /**
     * tell user they are green player or blue player
     */
    public void displayPlayerMsg(){
        String player_name = client.getMap().getPlayerName(client.getPlayerId());
        player_color.setText(player_name);
    }

    public ArrayList<Territory<Character>> displayMyTerritory(){
        Map<Character> myMap = client.getMap();
        ArrayList<Player<Character>> players = myMap.getMyPlayers();
        for(Territory<Character> terr:players.get(client.getPlayerId()).getMyTerritories()){
            System.out.println(terr.getName());
        }
        return players.get(client.getPlayerId()).getMyTerritories();

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

    /**
     * Display territory info at tooltip
     * @param toDisplay territory
     * @return the details of this territory
     */
    public String displayTerritoryInfo(Territory<Character> toDisplay){
        StringBuilder sb = new StringBuilder(toDisplay.getName());
        sb.append("\n--------------------\nSize : ");
        sb.append(toDisplay.getSize());
        sb.append("\nFood Ability : ");
        sb.append(toDisplay.getFoodAbility());
        sb.append("\nWood Ability : ");
        sb.append(toDisplay.getWoodAbility());
        sb.append("\nUnits :\n   Level 0 : ");
        sb.append(toDisplay.getLevelUnitNum(0));
        sb.append("\n   Level 1 : ");
        sb.append(toDisplay.getLevelUnitNum(1));
        sb.append("\n   Level 2 : ");
        sb.append(toDisplay.getLevelUnitNum(2));
        sb.append("\n   Level 3 : ");
        sb.append(toDisplay.getLevelUnitNum(3));
        sb.append("\n   Level 4 : ");
        sb.append(toDisplay.getLevelUnitNum(4));
        sb.append("\n   Level 5 : ");
        sb.append(toDisplay.getLevelUnitNum(5));
        sb.append("\n   Level 6 : ");
        sb.append(toDisplay.getLevelUnitNum(6));
        return sb.toString();
    }

    @FXML
    public void displayTerritory(ActionEvent ae){
        Button source = (Button)ae.getSource();
        String territory_name = source.getId();
        System.out.println(territory_name);
        Territory<Character> terr = client.getMap().findTerritory(territory_name);
        source.getTooltip().setText(displayTerritoryInfo(terr));
    }

    @FXML
    public void showMove(ActionEvent ae) throws IOException {
        displayMyTerritory();
        Button source = (Button)ae.getSource();
        //show move page
        URL xmlResource = getClass().getResource("/ui/MoveAction.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        //setup controller
        controllers.put(MoveActionController.class, new MoveActionController(client, client.getPlayerId(), displayMyTerritory(), client.getMap().getMyPlayers().get(client.getPlayerId())));//create a new controller and add it
        loader.setControllerFactory((c) -> {
            return controllers.get(c);
        });
        AnchorPane gp = loader.load();
        MoveActionController moveActionController= loader.getController();
        moveActionController.setup();
        Scene scene = new Scene(gp);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
               showMyActions();
            }
        });
    }

    @FXML
    public void showAttack(ActionEvent ae) throws IOException {
        Button source = (Button)ae.getSource();
        //show move page
        URL xmlResource = getClass().getResource("/ui/AttackAction.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        controllers.put(AttackActionController.class, new AttackActionController(client, client.getPlayerId(), displayMyTerritory(), client.getMap().getMyPlayers().get(client.getPlayerId())));//create a new controller and add it
        loader.setControllerFactory((c) -> {
            return controllers.get(c);
        });
        AnchorPane gp = loader.load();
        AttackActionController attackActionController= loader.getController();
        attackActionController.setup();
        Scene scene = new Scene(gp);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                showMyActions();
            }
        });
    }

    @FXML
    public void showUpgrade(ActionEvent ae) throws IOException {
        Button source = (Button)ae.getSource();
        //show move page
        URL xmlResource = getClass().getResource("/ui/UpgradeAction.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        controllers.put(UpgradeActionController.class, new UpgradeActionController(client));
        loader.setControllerFactory((c) -> {
            return controllers.get(c);
        });
        AnchorPane gp = loader.load();
        UpgradeActionController upgradeActionController = loader.getController();
        upgradeActionController.setup();
        Scene scene = new Scene(gp);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void showMyActions(){
        for(ActionParser action : actions){
            System.out.println(action.getSource() + " " + action.getDest() + " " + action.getUnit());
        }
    }



    @FXML
    public void commit(ActionEvent ae) throws IOException {
        //receive connection
        ArrayList<ActionParser> actions = client.getOrder_list();
        if(actions==null){
            return;
        }
        for(ActionParser action: actions){
            System.out.println(action.getSource() +" "+action.getDest()+" "+action.getUnit());
        }
        //send to server
        client.oneRoundEnd();
        wait_msg.setText("Waiting for other player");
        //then update
        client.oneRoundUpdate();
        wait_msg.setText("");
        //display new map
        displayTerritoryBorder();
    }


}
