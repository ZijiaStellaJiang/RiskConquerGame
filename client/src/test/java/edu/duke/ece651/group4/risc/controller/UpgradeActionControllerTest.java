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
public class UpgradeActionControllerTest {
  private UpgradeActionController cont;
  private Client mockClient;
  Button done;
  private ChoiceBox<String> mockSource;
  private ChoiceBox<Integer> mockFrom;
  private TextField level_up;
  private TextField unit_num;
  private Text alert;
  private AnchorPane pane;
  private Map<Character> testMap;
  private Text cost;

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
    mockClient = Mockito.mock(Client.class);
    generateTestMap();
    Mockito.when(mockClient.addOrder(any())).thenReturn("wrong");
    Mockito.when(mockClient.getMap()).thenReturn(testMap);
    cont = new UpgradeActionController(mockClient);
    done = new Button("done");
    done.setId("done");
    cont.done = done;
    cost = new Text();
    mockSource = new ChoiceBox<String>(FXCollections.observableArrayList("oz"));
    mockFrom = new ChoiceBox<Integer>(FXCollections.observableArrayList(0, 1));
    level_up = new TextField("1");
    unit_num = new TextField("1");
    level_up.setId("levelup");
    // level_up = Mockito.spy(new TextField("1"));
    // unit_num = Mockito.spy(new TextField("1"));
    mockFrom.getSelectionModel().selectFirst();
    mockSource.getSelectionModel().selectFirst();
    cont.num = unit_num;
    cont.cost = cost;
    cont.levelup = level_up;
    cont.from = mockFrom;
    alert = new Text();
    cont.alert = alert;
    cont.source = mockSource;
    pane = new AnchorPane(mockSource);
    // pane.getChildren().addAll(alert, unit_num, cost, level_up, mockFrom,
    // mockSource);
    cont.pane = pane;
    Scene scene = new Scene(pane);
    // Stage primarystage = stage;
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void test_done() {
    Platform.runLater(() -> {
      try {
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
    // set up
    cont.showCost();
  }

  @Test
  public void test_check_integer() {
    cont.levelup.setText("1");
    cont.num.setText("1.5");
    Platform.runLater(() -> {
      assertEquals(false, cont.checkIntegerValid());
      cont.done();
    });
    WaitForAsyncUtils.waitForFxEvents();
    FxAssert.verifyThat(alert, TextMatchers.hasText("Please enter integer"));
  }

  @Test
  public void test_check_cost_failure() {
    cont.levelup.setText("1");
    cont.num.setText("1.5");
    Platform.runLater(() -> {
      assertEquals(false, cont.checkIntegerValid());
      cont.showCost();
    });
    WaitForAsyncUtils.waitForFxEvents();
    FxAssert.verifyThat(cost, TextMatchers.hasText("Level up or num needs to be integer"));
  }

  @Test
  public void test_max_level() {
    cont.levelup.setText("100");
    //cont.num.setText("1.5");
    Platform.runLater(() -> {
        //assertEquals(false, cont.checkIntegerValid());
      cont.showCost();
    });
    WaitForAsyncUtils.waitForFxEvents();
    FxAssert.verifyThat(alert, TextMatchers.hasText("Maximum level is 6"));

  }
}
