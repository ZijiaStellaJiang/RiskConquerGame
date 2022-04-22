package edu.duke.ece651.group4.risc.controller.cloak;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.controller.TestMapGenerator;
import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.Territory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.ComboBoxMatchers;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.service.query.NodeQuery;
import org.testfx.util.WaitForAsyncUtils;

import java.util.ArrayList;

@ExtendWith(ApplicationExtension.class)
public class CloakControllerTest {
  private Client mockClient;
  private CloakController cont;
  Text cost;
  Button done_btn;
  ChoiceBox<String> territory;
  Text alert;
  private ArrayList<String> terr;
  AnchorPane pane;
  @Start
  private void start(Stage stage){
      terr = new ArrayList<String>();
      terr.add("Narnia");
      mockClient = Mockito.mock(Client.class);
      Map<Character> testMap = TestMapGenerator.generateTestMap();
      Mockito.when(mockClient.getMap()).thenReturn(testMap);
      Mockito.when(mockClient.getClientTerritories()).thenReturn(terr);
      Mockito.when(mockClient.getPlayerId()).thenReturn(0);
      cont = new CloakController(mockClient);
      cost = new Text();
      done_btn = new Button();
      territory = new ChoiceBox<String>(FXCollections.observableArrayList("Narnia", "Oz"));
      territory.getSelectionModel().selectFirst();
      alert = new Text();
      cont.cost = cost;
      cont.done_btn = done_btn;
      cont.territory = territory;
      cont.alert = alert;
      cont.initialize();
      pane = new AnchorPane();
      pane.getChildren().add(cost);
      cont.pane = pane;
      Scene scene = new Scene(pane);
      stage.setScene(scene);
      stage.show();
  }

    /**
     * Tests there is no selection of choice box
     */
  @Test
  public void test_failure_done() {
      Platform.runLater(() -> {
          try {
              cont.done(new ActionEvent(done_btn, null));
              FxAssert.verifyThat(cont.alert, TextMatchers.hasText("Please enter territory"));
          } catch (Exception e) {
              e.printStackTrace();
          }
      });
      WaitForAsyncUtils.waitForFxEvents();
  }
  @Test
  public void test_done() {
      Platform.runLater(() -> {
          try {
              cont.territory.getSelectionModel().selectFirst();
              cont.done(new ActionEvent(done_btn, null));
              FxAssert.verifyThat(cont.cost, TextMatchers.hasText("20"));
          } catch (Exception e) {
              e.printStackTrace();
          }
      });
      WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_add_order_failure(){
      Platform.runLater(() -> {
          try {
              cont.territory.getSelectionModel().selectFirst();
              String expected = "add order failure";
              Mockito.when(mockClient.addOrder(any())).thenReturn(expected);
              cont.done(new ActionEvent(done_btn, null));
              FxAssert.verifyThat(cont.alert, TextMatchers.hasText(expected));
          } catch (Exception e) {
              e.printStackTrace();
          }
      });
      WaitForAsyncUtils.waitForFxEvents();
  }

}
