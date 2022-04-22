package edu.duke.ece651.group4.risc.controller.spy;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.controller.Controller;
import edu.duke.ece651.group4.risc.shared.ActionParser;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MoveSpyController implements Controller {
    private Client client;
    @FXML
    Button done_btn;
    @FXML
    ChoiceBox<String> source;
    @FXML
    ChoiceBox<String> destination;
    @FXML
    TextField unit_num;
    @FXML
    Text alert;
    @FXML
    Text cost;

    private Stage former_stage;
    public void setup(){
        source.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    showCost();
                });
        destination.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    showCost();
                });
        unit_num.textProperty().addListener((observable, oldValue, newValue) -> {
            showCost();
        });
    }
    @FXML
    public void initialize(){
        ObservableList<String> terrs = FXCollections.observableArrayList("Hogwarts", "Mordor", "Oz", "Narnia", "Elantris", "Gondor");
        destination.setItems(terrs);
        source.setItems(terrs);
    }
    public MoveSpyController(Client client, Stage stage){
        this.former_stage = stage;
        this.client = client;
    }

    /**
     * Check whether use entered interge in num and level up
     * @return
     */
    public boolean checkIntegerValid(String unit){
        if(!unit.equals("")){
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
            if(checkIntegerValid(unit_num.getText())==false){
                cost.setText("unit number needs to be integer");
                alert.setText("");
                return;
            }
            try{
                String source_terr = source.getValue();
                String dest_terr = destination.getValue();
                Integer num = Integer.parseInt(unit_num.getText());
                ActionParser parser = new ActionParser("SMOVE", source_terr, dest_terr, num, 0, 0);
                String cost_val = parser.getCost(client.getMap());
                cost.setText(cost_val);
                return;
            } catch (IllegalArgumentException e) {
            }
        }
        cost.setText("Unavailable");
    }

    @FXML
    public void done(ActionEvent ae){
        if((!(source.getValue()!=null)&&(destination.getValue()!=null)&&(unit_num.getText()!=null))) {
            alert.setText("Please fill in all blanks");
            return;
        }
        if(checkIntegerValid(unit_num.getText())==false){
            alert.setText("unit number needs to be integer");
            return;
        }
        String source_terr = source.getValue();
        String dest_terr =destination.getValue();
        String num = unit_num.getText();
        try {
            ActionParser newAction = new ActionParser("SMOVE", source_terr, dest_terr, Integer.parseInt(num), 0, 0);
            String result = client.addOrder(newAction);
            if(result!=null){
                System.out.println(result);
                alert.setText(result);
            }else{
                Stage primaryStage = (Stage) source.getScene().getWindow();
                primaryStage.close();
                former_stage.close();
            }
        }catch (IllegalArgumentException e) {
            alert.setText("Invalid input");
            return;
        }
    }
}
