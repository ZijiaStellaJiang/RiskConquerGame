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
    @FXML
    Text cost;
    private ObservableList<String> sources;
    private Client client;

    /**
     * Constructor
     * @param client
     */
    public MoveActionController(Client client) {
        this.client = client;

    }

    /**
     * Check wheter use input integer
     * @return
     */
    public boolean checkIntegerValid(){
        String unit = unit_num.getText();
        if(unit_num.getText()!=null){
            return unit.matches("[0-9]+");
        }
        return true;
    }

    /**
     * Show cost
     */
    public void showCost(){
        alert.setText("");
        if((source.getValue()!=null)&&(destination.getValue()!=null)&&(unit_level.getValue()!=null)&&(unit_num.getText()!=null)){
            if(checkIntegerValid()==false){
                cost.setText("unit number needs to be integer");
                alert.setText("");
                return;
            }
            try{
                String source_terr = source.getValue();
                String dest_terr = destination.getValue();
                Integer level = unit_level.getValue();
                Integer num = Integer.parseInt(unit_num.getText());
                System.out.println("Calculating cost: " + source_terr + " " + dest_terr + " " + level + " " + num);
                ActionParser parser = new ActionParser("MOVE", source_terr, dest_terr, num, level);
                String cost_val = parser.getCost(client.getMap());
                cost.setText(cost_val);
                return;
            } catch (IllegalArgumentException e) {
            }
        }
        cost.setText("Unavailable");
    }

    /**
     * Set up page
     */
    public void setup() {
        //set source territory name
        sources = FXCollections.observableArrayList();
        for(String terr:client.getClientTerritories()){
            sources.add(terr);
        }
        source.setItems(sources);
        //add destination
        source.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    String select_source = source.getItems().get((Integer) new_val);
                    System.out.println("chosen source: " + select_source);
                    //TODO: refactor
                    ArrayList<String> dest = client.getClientCanReach(select_source,true);
                    if(dest!=null) {
                        ObservableList<String> dest_names = FXCollections.observableArrayList();
                        for (String d : dest) {
                            dest_names.add(d);
                        }
                        destination.setItems(dest_names);
                    }else{

                    }
                });
        //set unit level
        ObservableList<Integer> level = FXCollections.observableArrayList();
        level.addAll(0,1,2,3,4,5,6);
        unit_level.setItems(level);
        //add listener
        source.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    showCost();
                });
        destination.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    showCost();
                });
        unit_level.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    showCost();
                });
        unit_num.textProperty().addListener((observable, oldValue, newValue) -> {
            showCost();
        });

    }

    /**
     * Action after click done button
     */
    @FXML
    public void done(){
        if(!(source.getValue()!=null)&&(destination.getValue()!=null)&&(unit_level.getValue()!=null)&&(unit_num.getText()!=null)) {
            alert.setText("Please fill in all blanks");
            return;
        }
        if(checkIntegerValid()==false){
            alert.setText("unit number needs to be integer");
            return;
        }
        String source_terr = source.getValue();
        String dest_terr =destination.getValue();
        Integer level = unit_level.getValue();
        String num = unit_num.getText();
        System.out.println(source_terr + " " + dest_terr + " " + level + " " + num);
        try {
            ActionParser newAction = new ActionParser("MOVE", source_terr, dest_terr, Integer.parseInt(num), level);
            String result = client.addOrder(newAction);
            if(result!=null){
                System.out.println(result);
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
