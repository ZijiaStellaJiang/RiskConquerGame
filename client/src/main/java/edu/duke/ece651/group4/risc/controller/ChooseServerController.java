package edu.duke.ece651.group4.risc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.client.MapGUIView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is the controller of select server page
 */
public class ChooseServerController {
    Client client;
    private String host;
    @FXML
    Text alert;
    @FXML
    TextField select;
    @FXML
    AnchorPane pane;
    @FXML
    Button connect;
    /**
     * Constructor
     */
    public ChooseServerController(){

    }
    @FXML
    public void initialize(){
        connect.addEventHandler(MouseEvent.MOUSE_ENTERED, e->connect.setCursor(Cursor.HAND));
        //connect.addEventHandler(MouseEvent.MOUSE_EXITED,e->connect.setStyle("-fx-border-color: black;-fx-background-color: transparent;-fx-text-fill: black"));
    }
    /**
     * After user click connect to server, create a new client and connect it to server
     * @param ae click connect button behavior
     * @throws IOException
     */
    @FXML
    public void connect_server(ActionEvent ae) throws IOException {
        Object source = ae.getSource();
        if (source instanceof Button){
         host = (String) select.getText();
         System.out.println("connect to host: " + host);
         try{
             //create socket
             client = new Client(host, 6066, new BufferedReader(new InputStreamReader(System.in)), System.out);
             //receive map and player color from server
             client.initializeGame();
             System.out.println("player: " + client.getPlayerId() + " playing");
             //close current stage and jump to show display map page
             MapGUIView mapGUIView = new MapGUIView(client);
             Stage primaryStage = (Stage) alert.getScene().getWindow();
             primaryStage.close();
             mapGUIView.showMain();
         }catch (RuntimeException e){
             //set text view to inform user
            alert.setText("cannot connect to server");
         }
        }
    }

}
