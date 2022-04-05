package edu.duke.ece651.group4.risc.controller;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.group4.risc.client.Client;
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

  @Start
  private void start(Stage stage) {
    mockClient = Mockito.mock(Client.class);
    Mockito.when(mockClient.addOrder(any())).thenReturn("wrong");
    cont = new UpgradeActionController(mockClient);
    done = new Button("done");
    done.setId("done");
    cont.done = done;

    mockSource = new ChoiceBox<String>(FXCollections.observableArrayList("oz"));
    // mockFrom = (ChoiceBox<Integer>) Mockito.mock(ChoiceBox.class);
    // mockSource.setItems(FXCollections.observableArrayList("oz"));
    // mockFrom.setItems(FXCollections.observableArrayList(0));
    // Mockito.when(mockSource.getValue()).thenReturn("oz");
    // Mockito.when(mockFrom.getValue()).thenReturn(0);
    mockFrom = new ChoiceBox<Integer>(FXCollections.observableArrayList(0, 1));
    level_up = Mockito.spy(new TextField("1"));
    unit_num = Mockito.spy(new TextField("1"));
    mockFrom.getSelectionModel().selectFirst();
    mockSource.getSelectionModel().selectFirst();
    cont.num = unit_num;
    cont.levelup = level_up;
    cont.from = mockFrom;
    alert = new Text();
    cont.alert = alert;
    cont.source = mockSource;
    pane = new AnchorPane(mockSource);
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

}
