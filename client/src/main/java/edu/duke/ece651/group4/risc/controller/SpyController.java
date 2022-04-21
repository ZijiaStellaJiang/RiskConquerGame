package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


public class SpyController implements Controller{
    private HashMap<Class<?>, Object> controllers;
    private Client client;
    public SpyController(Client client){
        controllers = new HashMap<>();
        this.client = client;
    }

    public void loadNewPage(FXMLLoader loader, String css, int width, int height) throws IOException {
        AnchorPane gp = loader.load();
        Controller controller = loader.getController();
        controller.setup();
        URL cssResource = getClass().getResource(css);
        Scene scene = new Scene(gp, width, height);
        scene.getStylesheets().add(cssResource.toString());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    public void setup(){

    }
    /**
     * Jump to move spy page
     * @param ae
     */
    @FXML
    public void moveAction(ActionEvent ae) throws IOException {
        URL xmlResource = getClass().getResource("/ui/MoveSpy.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        controllers.put(MoveSpyController.class, new MoveSpyController(client));// create a new controller and
        // add it
        loader.setControllerFactory((c) -> {
            return controllers.get(c);
        });
        loadNewPage(loader, "/ui/button.css", 600, 400);
    }

    /**
     * Jump tp upgrade spy page
     * @param ae
     */
    @FXML
    public void upgradeAction(ActionEvent ae) throws IOException {
        URL xmlResource = getClass().getResource("/ui/UpgradeSpy.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        controllers.put(UpgradeSpyController.class, new UpgradeSpyController(client));// create a new controller and
        // add it
        loader.setControllerFactory((c) -> {
            return controllers.get(c);
        });
        loadNewPage(loader, "/ui/button.css", 600, 400);
    }
}
