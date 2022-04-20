package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.ActionParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CloakController implements Controller{

    @FXML
    Text cost;
    @FXML
    ChoiceBox<String> territory;
    @FXML
    Button done_btn;
    @FXML
    Text alert;
    private Client client;

    public CloakController(Client client){
        this.client = client;
    }

    @FXML
    public void initialize(){
        //add choice box listener
        ObservableList<String> sources = FXCollections.observableArrayList();
        for(String terr:client.getClientTerritories()){
            sources.add(terr);
        }
        territory.setItems(sources);

        cost.setText("20");
    }

    public void setup(){

    }
    @FXML
    public void done(ActionEvent actionEvent) {
        if(territory.getValue()==null){
            alert.setText("Please enter territory");
            return;
        }
        String terr = territory.getValue();
        //create cloak action
        ActionParser action  = new ActionParser("CLOAK", terr, null, 0, 0, 0);
        String result = client.addOrder(action);
        if(result!=null){
            System.out.println(result);
            alert.setText(result);
        }else{
            Stage primaryStage = (Stage) territory.getScene().getWindow();
            primaryStage.close();
        }
    }
}
