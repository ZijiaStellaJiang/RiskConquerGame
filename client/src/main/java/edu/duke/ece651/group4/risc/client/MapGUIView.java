package edu.duke.ece651.group4.risc.client;

import edu.duke.ece651.group4.risc.controller.MainMapController;
import edu.duke.ece651.group4.risc.shared.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class MapGUIView {
    private Client client;
    public MapGUIView(Client client){
        this.client = client;
    }

    public void showMain() throws IOException{
        try {
            System.out.println("showing the player" + client.getPlayerId());
            //
            URL xmlResource = getClass().getResource("/ui/Map.fxml");
            FXMLLoader loader = new FXMLLoader(xmlResource);

            //AnchorPane root = FXMLLoader.load(getClass().getResource("/ui/Map.fxml"));
            URL cssResource = getClass().getResource("/ui/button.css");
            HashMap<Class<?>, Object> controllers = new HashMap<>();
            controllers.put(MainMapController.class, new MainMapController(client));//create a new controller and add it
            loader.setControllerFactory((c) -> {
                return controllers.get(c);
            });

            AnchorPane root = loader.load();
            //display player's color
            MainMapController mapController=loader.getController();
            mapController.displayPlayerMsg();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(cssResource.toString());
            //displayPlayerMsg(player_id);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
