package edu.duke.ece651.group4.risc.controller;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.SimpleUnit;
import edu.duke.ece651.group4.risc.shared.Territory;
import edu.duke.ece651.group4.risc.shared.TextPlayer;
import edu.duke.ece651.group4.risc.shared.Unit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class AttackActionControllerTest {
  private ActionController cont;
  private Client mockClient;
  Button done;
  ChoiceBox<String> source;
  ChoiceBox<String> destination;
  Slider unit_level;
  //ChoiceBox<Integer> unit_level;
  TextField unit_num;
  AnchorPane pane;
  Text alert;

  @Start
  private void start(Stage stage) {
    mockClient = Mockito.mock(Client.class);
    Territory<Character> terriN = new Territory<Character>("Narnia", 5, 15, 10);
    Territory<Character> terriO = new Territory<Character>("Oz", 10, 20, 15);
    terriN.addNeigh(terriO);
    ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>(0)));
    terriN.addGroupUnit(nUnits);
    ArrayList<Unit<Character>> oUnits = new ArrayList<>(Collections.nCopies(3, new SimpleUnit<>(0)));
    terriO.addGroupUnit(oUnits);
    TextPlayer p1 = new TextPlayer("Green", 200, 200);
    ArrayList<Territory<Character>> myTerr = new ArrayList<Territory<Character>>();
    myTerr.add(terriN);
    myTerr.add(terriO);
    cont = new ActionController(mockClient, "ATTACK");
    // setup element
    done = new Button();
    done.setId("done");
    source = new ChoiceBox<String>(FXCollections.observableArrayList("oz", "narnia"));
    source.getSelectionModel().selectFirst();
    destination = new ChoiceBox<String>(FXCollections.observableArrayList("oz", "narnia"));
    destination.getSelectionModel().select(1);
    unit_level = new Slider(0,6,1);
    //unit_level = new ChoiceBox<Integer>(FXCollections.observableArrayList(0, 1));
    //unit_level.getSelectionModel().selectFirst();
    unit_level.setValue(0);
    unit_num = new TextField("1");
    cont.source = source;
    cont.destination = destination;
    cont.unit_num = unit_num;
    cont.unit_level = unit_level;
    cont.done_btn = done;
    alert = new Text();
    cont.alert = alert;
    pane = new AnchorPane();
    pane.getChildren().add(source);
    cont.pane = pane;
    Scene scene = new Scene(pane);
    // Stage primarystage = stage;
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void test_done() {
    // cont.source.getSelectionModel().selectFirst();
    // cont.destination.getSelectionModel().select(1);;

    // cont.unit_level.getSelectionModel().selectFirst();
    Platform.runLater(() -> {
      try {
        Mockito.when(mockClient.addOrder(any())).thenReturn("wrong"); 
        cont.done();
        Mockito.when(mockClient.addOrder(any())).thenReturn(null);
        cont.done();
        // FxAssert.verifyThat("#oz", );
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
