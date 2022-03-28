package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.client.MapGUIView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ChooseServerController {
    Client client;
    private String host;
    @FXML
    ChoiceBox host_choice;
    @FXML
    Text alert;

    /**
     * Constructor
     */
    public ChooseServerController(){

    }

    @FXML
    public void connect_server(ActionEvent ae) throws IOException {
        Object source = ae.getSource();
        if (source instanceof Button){
         host = (String) host_choice.getValue();
         System.out.println("connect to host: " + host);
         try{
             //create socket
             client = new Client(host, 6066, new BufferedReader(new InputStreamReader(System.in)), System.out);
             //receive map and player color from server
             client.initializeGame();
             System.out.println("player: " + client.getPlayerId() + " playing");
             //jump to show display map page
             MapGUIView mapGUIView = new MapGUIView(client);
             mapGUIView.showMain();
         }catch (RuntimeException e){
             //set text view to inform user
            alert.setText("cannot connect to server");
         }

        }
        //Client.connectServer();
    }

}
