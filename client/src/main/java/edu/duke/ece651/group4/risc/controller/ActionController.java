package edu.duke.ece651.group4.risc.controller;
import java.util.ArrayList;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.ActionParser;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
public class ActionController implements Controller{
    private String type;//attack or move
    @FXML
    ChoiceBox<String> source;
    @FXML
    ChoiceBox<String> destination;
//    @FXML
//    ChoiceBox<Integer> unit_level;
    @FXML
    Slider unit_level;

    @FXML
    TextField unit_num;
    @FXML
    Text alert;
    @FXML
    AnchorPane pane;
    @FXML
    Button done_btn;
    @FXML
    Text cost;

    private ObservableList<String> sources;
    private Client client;

    public ActionController(Client client, String type) {
        this.client = client;
        this.type = type;
    }
    @FXML
    public void initialize(){
        unit_level.setMin(0);
        unit_level.setMax(6);
        unit_level.setBlockIncrement(1);
        unit_level.setMajorTickUnit(1);
        unit_level.setMinorTickCount(0);
        unit_level.setShowTickLabels(true);
        unit_level.setSnapToTicks(true);
    }
    /**
     * Check whether use entered interge in num and level up
     * @return
     */
    public boolean checkIntegerValid(){
        String unit = unit_num.getText();
        if(!unit_num.getText().equals("")){
            return unit.matches("[0-9]+");
        }
        return true;
    }

    /**
     * Show cost when the action has not been submited
     */
    public void showCost(){
        alert.setText("");
        if((source.getValue()!=null)&&(destination.getValue()!=null)&&(unit_num.getText()!=null)){
            if(checkIntegerValid()==false){
                cost.setText("unit number needs to be integer");
                alert.setText("");
                return;
            }
            try{
                String source_terr = source.getValue();
                String dest_terr = destination.getValue();
                double level = unit_level.getValue();
                Integer num = Integer.parseInt(unit_num.getText());
                System.out.println("Calculating cost: " + source_terr + " " + dest_terr + " " + level + " " + num);
                ActionParser parser = new ActionParser(type, source_terr, dest_terr, num, (int)level);
                String cost_val = parser.getCost(client.getMap());
                cost.setText(cost_val);
                return;
            } catch (IllegalArgumentException e) {
            }
        }
        cost.setText("Unavailable");
    }
    /**
     * Sets up the page
     */
    public void setup() {
        //set source territory name
        sources = FXCollections.observableArrayList();
        for(String terr:client.getClientTerritories()){
            sources.add(terr);
        }
        source.setItems(sources);
        source.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    String select_source = source.getItems().get((Integer) new_val);
                    System.out.println("chosen source: " + select_source);
                    //TODO: refactor
                    boolean ifMove;
                    if(type.equals("ATTACK")){
                        ifMove=false;
                    }else{
                        ifMove=true;
                    }
                    ArrayList<String> dest = client.getClientCanReach(select_source, ifMove);
                    if(dest!=null) {
                        ObservableList<String> dest_names = FXCollections.observableArrayList();
                        for (String t : dest) {
                            dest_names.add(t);
                        }
                        destination.setItems(dest_names);
                    }else{

                    }
                });

        source.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    showCost();
                });
        destination.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    showCost();
                });
        unit_level.valueProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    showCost();
                });
        unit_num.textProperty().addListener((observable, oldValue, newValue) -> {
            showCost();
        });

    }

    /**
     * What happens when user click done
     */
    @FXML
    public void done(){
      if((!(source.getValue()!=null)&&(destination.getValue()!=null)&&(unit_num.getText()!=null))) {
            alert.setText("Please fill in all blanks");
            return;
        }
        if(checkIntegerValid()==false){
            alert.setText("unit number needs to be integer");
            return;
        }
        String source_terr = source.getValue();
        String dest_terr =destination.getValue();
        double level = unit_level.getValue();
        String num = unit_num.getText();
        System.out.println(source_terr + " " + dest_terr + " " + level + " " + num);
        try {
            ActionParser newAction = new ActionParser(type, source_terr, dest_terr, Integer.parseInt(num), (int)level);
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
