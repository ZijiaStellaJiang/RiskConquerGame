package edu.duke.ece651.group4.risc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.SimpleUnit;
import edu.duke.ece651.group4.risc.shared.Territory;
import edu.duke.ece651.group4.risc.shared.TextPlayer;
import edu.duke.ece651.group4.risc.shared.Unit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class MoveActionControllerTest {
  private ActionController cont;
  private Client mockClient;
  Button done;
  ChoiceBox<String> source;
  ChoiceBox<String> destination;
  ChoiceBox<Integer> unit_level;
  TextField unit_num;
  AnchorPane pane;
  Text alert;
  Text cost;
  private Map<Character> testMap;

  public void generateTestMap() {
    testMap = new Map<Character>();
    Territory<Character> terriN = new Territory<Character>("Narnia", 5, 15, 10);
    Territory<Character> terriO = new Territory<Character>("Oz", 10, 20, 15);
    terriN.addNeigh(terriO);
    ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>(0)));
    terriN.addGroupUnit(nUnits);
    ArrayList<Unit<Character>> oUnits = new ArrayList<>(Collections.nCopies(3, new SimpleUnit<>(0)));
    terriO.addGroupUnit(oUnits);
    testMap.addTerritory(terriN);
    testMap.addTerritory(terriO);
    TextPlayer p1 = new TextPlayer("Green", 200, 200);
    TextPlayer p2 = new TextPlayer("Blue", 200, 200);
    p1.addToTerritory(terriN);
    p2.addToTerritory(terriO);
    testMap.addPlayer(p1);
    testMap.addPlayer(p2);
  }

  @Start
  private void start(Stage stage) {
    generateTestMap();
    mockClient = Mockito.mock(Client.class);
    Mockito.when(mockClient.getMap()).thenReturn(testMap);
    Mockito.when(mockClient.addOrder(any())).thenReturn("wrong");
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
    cont = new ActionController(mockClient, "MOVE");
    // setup element
    done = new Button();
    done.setId("done");
    source = new ChoiceBox<String>(FXCollections.observableArrayList("oz", "narnia"));
    destination = new ChoiceBox<String>(FXCollections.observableArrayList("oz", "narnia"));
    unit_level = new ChoiceBox<Integer>(FXCollections.observableArrayList(0, 1));
    unit_num = new TextField("1");
    cost = new Text();
    source.getSelectionModel().selectFirst();
    destination.getSelectionModel().select(1);

    unit_level.getSelectionModel().selectFirst();
    alert = new Text();
    cont.cost = cost;
    cont.alert = alert;
    cont.source = source;
    cont.destination = destination;
    cont.unit_num = unit_num;
    cont.unit_level = unit_level;
    pane = new AnchorPane();
    pane.getChildren().add(source);
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void test_done() {
    Platform.runLater(() -> {
      try {
        // Mockito.when(mockClient.addOrder(any())).thenReturn("wrong");
        // cont.done();
        // Mockito.when(mockClient.addOrder(any())).thenReturn(null);
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

  @Test
  public void test_show_cost() {
    unit_num.setText("");
    cont.showCost();
  }

  @Test
  public void test_check_integer_valid() {
    cont.unit_num = new TextField();
    Platform.runLater(() -> {
      // System.err.println("level is:" + cont.unit_num.getText());
      assertEquals("", cont.unit_num.getText());
      assertEquals(true, cont.checkIntegerValid());
      cont.unit_num.setText("1.5");
      assertEquals(false, cont.checkIntegerValid());
      cont.done();
    });
    WaitForAsyncUtils.waitForFxEvents();
    // assertEquals(true, cont.checkIntegerValid());
    FxAssert.verifyThat(alert, TextMatchers.hasText("unit number needs to be integer")); 
  }

  @Test
  public void test_check_cost() {
    cont.unit_num.setText("1.5");
    Platform.runLater(() -> {
      cont.showCost();
    });
    WaitForAsyncUtils.waitForFxEvents();
    FxAssert.verifyThat(cost, TextMatchers.hasText("unit number needs to be integer"));
  }

  @Test
  public void test_check_cost_empty_input() {
    cont.unit_num.setText("");
    Platform.runLater(() -> {
      cont.showCost();
      cont.done();
    });
    WaitForAsyncUtils.waitForFxEvents();
    FxAssert.verifyThat(cost, TextMatchers.hasText("Unavailable"));
    FxAssert.verifyThat(alert, TextMatchers.hasText("Please fill in all blanks"));
  }

  @Test
  public void test_show_cost_correct() {
    cont.unit_num.setText("1");
    cont.source.getSelectionModel().selectFirst();
    cont.destination.getSelectionModel().selectFirst();
    cont.unit_level.getSelectionModel().selectFirst();
    Platform.runLater(() -> {
      cont.showCost();
    });
    WaitForAsyncUtils.waitForFxEvents();
    FxAssert.verifyThat(cost, TextMatchers.hasText("food: 10"));
  }

}
