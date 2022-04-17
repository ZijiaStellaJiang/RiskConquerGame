package edu.duke.ece651.group4.risc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.SimpleUnit;
import edu.duke.ece651.group4.risc.shared.Territory;
import edu.duke.ece651.group4.risc.shared.TextPlayer;
import edu.duke.ece651.group4.risc.shared.Unit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class MainMapControllerTest {
  private Client mockClient;
  private MainMapController cont;
  private Map<Character> testMap;
  AnchorPane pane;
  Button oz;
  Button narnia;
  Button move;
  Button upgrade;
  Button attack;
  Button commit;
  Text text;
  Text wait_msg;
  private TerritoryDetailController detail_cont;

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
    cont = new MainMapController(mockClient);
    generateTestMap();
    Mockito.when(mockClient.getMap()).thenReturn(testMap);
    Mockito.when(mockClient.getPlayerId()).thenReturn(0);

    // set up element
    pane = new AnchorPane();
    wait_msg = new Text();
    wait_msg.setId("wait_msg");
    narnia = new Button("Narnia");
    oz = new Button("Oz");
    text = new Text("test");
    narnia.setId("narniz");
    oz.setId("oz");
    move = new Button("MOVE");
    move.setId("showMove");
    attack = new Button("attack");
    attack.setId("showAttack");
    upgrade = new Button("upgrade");
    upgrade.setId("showUpgrade");
    commit = new Button("commit");
    commit.setId("commit");
    Button empty = new Button();
    pane.getChildren().addAll(narnia, oz, text, empty, move, attack, upgrade, commit);
    cont.background = pane;
    cont.wait_msg = wait_msg;
  }

  @Test
  public void test_display_my_territory() {
    ArrayList<Territory<Character>> terr = cont.displayMyTerritory();
    for (Territory<Character> t : terr) {
      assertEquals("Narnia", t.getName());
    }
  }

  @Test
  public void test_display_territory_border() {
    Platform.runLater(() -> {
      try {
        cont.displayTerritoryBorder();

        // FxAssert.verifyThat("#oz", );
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Disabled
  @Test
  public void test_show_territory_details(FxRobot robot) throws IOException {
    // first get the string to display
    String expected = "Oz\n--------------------\nSize : 10\nFood Ability : 20\nWood Ability : 15\nUnits :\n   Level 0 : 3\n   Level 1 : 0\n   Level 2 : 0\n   Level 3 : 0\n   Level 4 : 0\n   Level 5 : 0\n   Level 6 : 0";
    Territory<Character> terr = testMap.findTerritory("Oz");
    assertEquals(expected, cont.displayTerritoryInfo(terr));
    // detail_setup();
    Platform.runLater(() -> {
      try {
        // FxAssert.verifyThat("#oz", isVisible());
        cont.displayTerritory(new ActionEvent(oz, null));
        // FxAssert.verifyThat((Stage) getWindowByIndex, WindowMatchers.isShowing());
      } catch (Exception e) {

      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }
  
  @Test
  public void test_show_move(FxRobot robot) {
    //robot.clickOn("#showMove");
    Platform.runLater(() -> {
      try {

        cont.showMove(new ActionEvent(move, null));
        cont.showAttack(new ActionEvent(attack, null));
        cont.showUpgrade(new ActionEvent(upgrade, null));
         cont.commit(new ActionEvent(commit, null));
        // FxAssert.verifyThat((Stage) getWindowByIndex, WindowMatchers.isShowing());
      } catch (Exception e) {
        e.printStackTrace();
      }
      });
    //robot.clickOn("#showMove");
    WaitForAsyncUtils.waitForFxEvents();
  }

}
