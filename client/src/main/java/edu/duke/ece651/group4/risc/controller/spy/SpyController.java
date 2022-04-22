package edu.duke.ece651.group4.risc.controller.spy;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * Let user choose move or upgrade
 */
public class SpyController implements Controller {
    @FXML
    Button move_btn;
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
        Stage stage = (Stage) move_btn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setup(){

    }
    public void hide_stage() {
        Stage primaryStage = (Stage) move_btn.getScene().getWindow();
        primaryStage.hide();
    }
    /**
     * Jump to move spy page
     * @param ae
     */
    @FXML
    public void moveAction(ActionEvent ae) throws IOException {
        hide_stage();
        URL xmlResource = getClass().getResource("/ui/spy/MoveSpy.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        controllers.put(MoveSpyController.class, new MoveSpyController(client, (Stage) move_btn.getScene().getWindow()));// create a new controller and
        // add it
        loader.setControllerFactory((c) -> {
            return controllers.get(c);
        });
        loadNewPage(loader, "/ui/button.css", 650, 450);
    }

    /**
     * Jump tp upgrade spy page
     * @param ae
     */
    @FXML
    public void upgradeAction(ActionEvent ae) throws IOException {
        hide_stage();
        URL xmlResource = getClass().getResource("/ui/spy/UpgradeSpy.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        controllers.put(UpgradeSpyController.class, new UpgradeSpyController(client, (Stage) move_btn.getScene().getWindow()));// create a new controller and
        // add it
        loader.setControllerFactory((c) -> {
            return controllers.get(c);
        });
        loadNewPage(loader, "/ui/button.css", 650, 450);
    }
}
