package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.ActionParser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class ResearchCloakController implements Controller{

    @FXML
    Button yes_btn;
    @FXML
    Button no_btn;
    @FXML
    Text alert;
    private Client client;
    private HashMap<Class<?>, Object> controllers;

    public ResearchCloakController(Client client){
        this.client = client;
        controllers = new HashMap<>();
    }
    public void setup(){

    }
    public void exit_page(){
        Stage primaryStage = (Stage) yes_btn.getScene().getWindow();
        primaryStage.close();
    }
    @FXML
    public void exit(){
        exit_page();
    }

    @FXML
    public void researchCloak() throws IOException {
        ActionParser parser = new ActionParser("RCLOAK", null, null, 0,0,0);
        String result = client.addOrder(parser);
        if(result!=null){
            alert.setText(result);
        }else{
            //jump to cloak page
            URL xmlResource = getClass().getResource("/ui/Cloak.fxml");
            URL cssResource = getClass().getResource("/ui/button.css");
            FXMLLoader loader = new FXMLLoader(xmlResource);
            controllers.put(CloakController.class, new CloakController(client));
            loader.setControllerFactory((c) -> {
                return controllers.get(c);
            });
            AnchorPane gp = loader.load();
            Scene scene = new Scene(gp);
            scene.getStylesheets().add(cssResource.toString());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            exit_page();
        }
    }
}
