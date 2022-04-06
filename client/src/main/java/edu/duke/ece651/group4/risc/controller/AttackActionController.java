//package edu.duke.ece651.group4.risc.controller;
//
//import java.util.ArrayList;
//
//import edu.duke.ece651.group4.risc.client.Client;
//import edu.duke.ece651.group4.risc.shared.ActionParser;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.TextField;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import javafx.scene.layout.AnchorPane;
//public class AttackActionController {
//    @FXML
//    ChoiceBox<String> source;
//    @FXML
//    ChoiceBox<String> destination;
//    @FXML
//    ChoiceBox<Integer> unit_level;
//    @FXML
//    TextField unit_num;
//    @FXML
//    Text alert;
//    @FXML
//    AnchorPane pane;
//    @FXML
//    Button done_btn;
//    @FXML
//    Text cost;
//    private ObservableList<String> sources;
//    private Client client;
//
//    public AttackActionController(Client client) {
//        this.client = client;
//    }
//
//    /**
//     * Check whether use entered interge in num and level up
//     * @return
//     */
//    public boolean checkIntegerValid(){
//        String unit = unit_num.getText();
//        if(unit_num.getText()!=null){
//            return unit.matches("[0-9]+");
//        }
//        return true;
//    }
//
//    /**
//     * Show cost when the action has not been submited
//     */
//    public void showCost(){
//        alert.setText("");
//        if((source.getValue()!=null)&&(destination.getValue()!=null)&&(unit_level.getValue()!=null)&&(unit_num.getText()!=null)){
//            if(checkIntegerValid()==false){
//                cost.setText("unit number needs to be integer");
//                alert.setText("");
//                return;
//            }
//            try{
//                String source_terr = source.getValue();
//                String dest_terr = destination.getValue();
//                Integer level = unit_level.getValue();
//                Integer num = Integer.parseInt(unit_num.getText());
//                System.out.println("Calculating cost: " + source_terr + " " + dest_terr + " " + level + " " + num);
//                ActionParser parser = new ActionParser("ATTACK", source_terr, dest_terr, num, level);
//                String cost_val = parser.getCost(client.getMap());
//                cost.setText(cost_val);
//                return;
//            } catch (IllegalArgumentException e) {
//            }
//        }
//        cost.setText("Unavailable");
//    }
//    /**
//     * Sets up the page
//     */
//    public void setup() {
//        //set source territory name
//        sources = FXCollections.observableArrayList();
//        for(String terr:client.getClientTerritories()){
//            sources.add(terr);
//        }
//        source.setItems(sources);
//        source.getSelectionModel().selectedIndexProperty().addListener(
//                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
//                    String select_source = source.getItems().get((Integer) new_val);
//                    System.out.println("chosen source: " + select_source);
//                    //TODO: refactor
//                    ArrayList<String> dest = client.getClientCanReach(select_source, false);
//                    if(dest!=null) {
//                        ObservableList<String> dest_names = FXCollections.observableArrayList();
//                        for (String t : dest) {
//                            dest_names.add(t);
//                        }
//                        destination.setItems(dest_names);
//                    }else{
//
//                    }
//                });
//        //set unit level
//        ObservableList<Integer> level = FXCollections.observableArrayList();
//        level.addAll(0,1,2,3,4,5,6);
//        unit_level.setItems(level);
//
//        source.getSelectionModel().selectedIndexProperty().addListener(
//                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
//                    showCost();
//                });
//        destination.getSelectionModel().selectedIndexProperty().addListener(
//                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
//                    showCost();
//                });
//        unit_level.getSelectionModel().selectedIndexProperty().addListener(
//                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
//                    showCost();
//                });
//        unit_num.textProperty().addListener((observable, oldValue, newValue) -> {
//            showCost();
//        });
//
//    }
//
//    /**
//     * What happens when user click done
//     */
//    @FXML
//    public void done(){
//        if(!(source.getValue()!=null)&&(destination.getValue()!=null)&&(unit_level.getValue()!=null)&&(unit_num.getText()!=null)) {
//            alert.setText("Please fill in all blanks");
//            return;
//        }
//        if(checkIntegerValid()==false){
//            alert.setText("unit number needs to be integer");
//            return;
//        }
//        String source_terr = source.getValue();
//        String dest_terr =destination.getValue();
//        Integer level = unit_level.getValue();
//        String num = unit_num.getText();
//        System.out.println(source_terr + " " + dest_terr + " " + level + " " + num);
//        try {
//            ActionParser newAction = new ActionParser("ATTACK", source_terr, dest_terr, Integer.parseInt(num), level);
//            String result = client.addOrder(newAction);
//            if(result!=null){
//                System.out.println(result);
//                alert.setText(result);
//            }else{
//                Stage primaryStage = (Stage) source.getScene().getWindow();
//                primaryStage.close();
//            }
//        }catch (IllegalArgumentException e) {
//            alert.setText("Invalid input");
//            return;
//        }
//    }
//}
