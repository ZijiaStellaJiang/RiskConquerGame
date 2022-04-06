package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.Map;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class UpdateThread extends Thread{
    private Client client;
    public UpdateThread(Client client){
        this.client = client;
    }

    @Override
    public void run(){
        try {
            client.recv_from_server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
