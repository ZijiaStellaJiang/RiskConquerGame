package edu.duke.ece651.group4.risc.controller;

import java.io.IOException;

import javafx.scene.control.ChoiceBox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(ApplicationExtension.class)
public class ChooseServerControllerTest {
  private Text text;
  private ChooseServerController cont;
  private ChoiceBox server;
  @Start
  private void start(Stage stage) {
    text = new Text();
    server = new ChoiceBox();
    cont = new ChooseServerController();
    cont.alert = text;
    cont.host_choice = server;
  }

  @Test
  public void test_server_connect(FxRobot robot) throws IOException{
    Platform.runLater(() -> {
        Button b = new Button("CONNECT");

        server.setValue("localhost");
      try {
        cont.connect_server(new ActionEvent(b, null));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
