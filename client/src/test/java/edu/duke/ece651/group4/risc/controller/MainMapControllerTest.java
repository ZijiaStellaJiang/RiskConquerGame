package edu.duke.ece651.group4.risc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import edu.duke.ece651.group4.risc.shared.*;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.group4.risc.client.Client;
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
  Text food;
  Text wood;
  Button cloak;
  Button spy;
  ImageView cloak1;
  ImageView cloak2;
  ImageView cloak3;
  ImageView cloak4;
  ImageView cloak5;
  ImageView cloak6;
  ImageView spy1;
  ImageView spy2;
  ImageView spy3;
  ImageView spy4;
  ImageView spy5;
  ImageView spy6;
  Text victory_msg;
//  private TerrioryDetailController detail_cont;

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
    spy = new Button();
    cloak = new Button();
    Button empty = new Button();
    victory_msg = new Text();
    food = new Text();
    wood = new Text();
    cloak1  =new ImageView();
    cloak2  =new ImageView();
    cloak3  =new ImageView();
    cloak4  =new ImageView();
    cloak5  =new ImageView();
    cloak6  =new ImageView();
    spy1  =new ImageView();
    spy2  =new ImageView();
    spy3  =new ImageView();
    spy4  =new ImageView();
    spy5  =new ImageView();
    spy6  =new ImageView();
    cont.food = food;
    cont.wood = wood;
    cont.showMove = move;
    cont.showAttack = attack;
    cont.showUpgrade = upgrade;
    cont.commit_btn = commit;
    cont.cloak_btn = cloak;
    cont.spy_btn = spy;
    cont.cloak1 = cloak1;
    cont.cloak2 = cloak2;
    cont.cloak3 = cloak3;
    cont.cloak4 = cloak4;
    cont.cloak5 = cloak5;
    cont.cloak6 = cloak6;
    cont.spy1 = spy1;
    cont.spy2 = spy2;
    cont.spy3 = spy3;
    cont.spy4 = spy4;
    cont.spy5 = spy5;
    cont.spy6 = spy6;
    cont.victory_msg = victory_msg;
    pane.getChildren().addAll(narnia, oz, text, empty, move, attack, upgrade, commit, food, wood, cloak, spy);
    cont.background = pane;
    cont.wait_msg = wait_msg;
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
    cont.initialize();
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
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

//  @Disabled
//  @Test
//  public void test_show_territory_details(FxRobot robot) throws IOException {
//    // first get the string to display
//    String expected = "Oz\n--------------------\nSize : 10\nFood Ability : 20\nWood Ability : 15\nUnits :\n   Level 0 : 3\n   Level 1 : 0\n   Level 2 : 0\n   Level 3 : 0\n   Level 4 : 0\n   Level 5 : 0\n   Level 6 : 0";
//    Territory<Character> terr = testMap.findTerritory("Oz");
//    assertEquals(expected, cont.displayTerritoryInfo(terr));
//    // detail_setup();
//    Platform.runLater(() -> {
//      try {
//        // FxAssert.verifyThat("#oz", isVisible());
//        cont.displayTerritory(new ActionEvent(oz, null));
//        // FxAssert.verifyThat((Stage) getWindowByIndex, WindowMatchers.isShowing());
//      } catch (Exception e) {
//
//      }
//    });
//    WaitForAsyncUtils.waitForFxEvents();
//  }
  @Disabled
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
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Disabled
  @Test
  public void test_show_fog(){
    Platform.runLater(() -> {
      try {
        Territory<Character> terr = new Territory<>("oz", 1, 1, 1);
        Mockito.when(mockClient.cloakRemain(any())).thenReturn(1);
        cont.showFogWar(terr);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }


  @Test
  public void test_show_map(){
    Platform.runLater(() -> {
      try {
        cont.displayTerritory(new ActionEvent(oz, null));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }


  @Test
  public void commit_version2(){
    Platform.runLater(() -> {
      try {
        ArrayList<ActionParser> actions = new ArrayList<ActionParser>();
        actions.add(new ActionParser("RCLOAK", "oz", "narnia", 0, 0, 0));
        Mockito.when(mockClient.getOrder_list()).thenReturn(actions);
        cont.commit((new ActionEvent(oz, null)));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }


  @Test
  public void test_update(){
    Platform.runLater(() -> {
      try {
        Mockito.when(mockClient.cloakIsResearch()).thenReturn(true);
        Mockito.when(mockClient.cloakRemain("Oz")).thenReturn(1);
        Mockito.when(mockClient.cloakRemain("Narnia")).thenReturn(0);
        cont.updateCloakIconVisiblity();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_spy(){
    Platform.runLater(() -> {
      try {
        Mockito.when(mockClient.territoryIsMine("Oz")).thenReturn(true);
        Mockito.when(mockClient.territoryIsMine("Narnia")).thenReturn(true);
        Mockito.when(mockClient.getMySpyNum("Oz")).thenReturn(1);
        Mockito.when(mockClient.getMySpyNum("Narnia")).thenReturn(1);
        cont.updateSpyVisibility();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_spy_notMy(){
    Platform.runLater(() -> {
      try {
        Mockito.when(mockClient.territoryIsMine("Oz")).thenReturn(true);
        Mockito.when(mockClient.territoryIsMine("Narnia")).thenReturn(false);
        Mockito.when(mockClient.getEnemySpyNum(any())).thenReturn(1);
        cont.updateSpyVisibility();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Disabled
  @Test
  public void test_new_action(){
    Platform.runLater(() -> {
      try {
        Mockito.when(mockClient.cloakIsResearch()).thenReturn(true);
        cont.cloakAction(new ActionEvent(cloak, null));
        cont.spyAction();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Disabled
  @Test
  public void test_game_over(){
    Platform.runLater(() -> {
      try {
        Mockito.when(mockClient.checkGameOver()).thenReturn(true);
        Mockito.when(mockClient.getVictoryInfo()).thenReturn("you win");
        Mockito.when(mockClient.getPlayerRoundInfo()).thenReturn("you win");
        cont.commit(new ActionEvent(commit, null));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    FxAssert.verifyThat("#end_msg", isVisible());
  }



}
