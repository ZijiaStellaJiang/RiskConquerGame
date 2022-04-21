package edu.duke.ece651.group4.risc.controller.spy;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.controller.Controller;
import edu.duke.ece651.group4.risc.shared.ActionParser;
import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.Player;
import edu.duke.ece651.group4.risc.shared.Territory;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UpgradeSpyController implements Controller {
    private Client client;
    @FXML
    ChoiceBox<String> source;
    @FXML
    Text cost;
    @FXML
    TextField unit_num;
    @FXML
    Slider unit_level;
    @FXML
    Text alert;
    private ObservableList<String> sources;
    public UpgradeSpyController(Client client){
        this.client = client;
    }

    /**
     * Get territory of user
     * @return
     */
    public ArrayList<Territory<Character>> displayMyTerritory() {
        Map<Character> myMap = client.getMap();
        ArrayList<Player<Character>> players = myMap.getMyPlayers();
        return players.get(client.getPlayerId()).getMyTerritories();
    }
    public void setup(){

    }
    /**
     * Check whether use input integer
     * @return
     */
    public boolean checkIntegerValid() {
        String unit_num_str = unit_num.getText();
        if (unit_num_str.matches("[0-9]+")) {
            return true;
        }
        return false;
    }
    /**
     * Show cost of user input
     */
    public void showCost() {
        alert.setText("");
        double level = unit_level.getValue();
        if(level % 1 !=0){
            return;
        }
        if ((source.getValue() != null) && (unit_num.getText() != null)) {
            if (checkIntegerValid() == false) {
                cost.setStyle("-fx-font:bold 10pt Copperplate");
                cost.setText("Level up or num needs to be integer");
                alert.setText("");
                return;
            }
            try {
                Integer num = Integer.parseInt(unit_num.getText());
                String cost_val = String.valueOf(num*20);
                cost.setStyle("-fx-font:bold 15pt Copperplate");
                cost.setText(cost_val);
                return;
            } catch (IllegalArgumentException e) {
            }
        }
        cost.setStyle("-fx-font:bold 15pt Copperplate");
        cost.setText("Unavailable");
    }
    public void setSlider(){
        unit_level.setMin(0);
        unit_level.setMax(6);
        unit_level.setBlockIncrement(1);
        unit_level.setMajorTickUnit(1);
        unit_level.setMinorTickCount(0);
        unit_level.setShowTickLabels(true);
        unit_level.setSnapToTicks(true);
    }
    @FXML
    public void initialize(){
        setSlider();
        sources = FXCollections.observableArrayList();
        for (Territory<Character> terr : displayMyTerritory()) {
            sources.add(terr.getName());
        }
        source.setItems(sources);
        ObservableList<Integer> level = FXCollections.observableArrayList();
        level.addAll(0, 1, 2, 3, 4, 5, 6);

        unit_num.textProperty().addListener((observable, oldValue, newValue) -> {
            showCost();
        });


        cost.setStyle("-fx-font:bold 15pt Copperplate");
        cost.setText("Unavailable");

    }

    @FXML
    public void done(ActionEvent actionEvent) {
        ActionParser parser = new ActionParser("SUPDATE", source.getValue(), null, Integer.parseInt(unit_num.getText()), (int) unit_level.getValue(), 0);
        String result = client.addOrder(parser);
        if(result!=null){
            alert.setText(result);
        }else{
            Stage primaryStage = (Stage) source.getScene().getWindow();
            primaryStage.close();
        }
    }

}
