package edu.duke.ece651.group4.risc.controller;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
public class StartPageControllerTest {
  private StartPageController controller;
  Button b;
  Button exit;
  StackPane pane;

  @Start
  private void start(Stage stage) {

    controller = new StartPageController();
    pane = new StackPane();
    b = new Button("START");
    exit = new Button("EXIT");
    pane.getChildren().addAll(b, exit);
    Scene scene = new Scene(pane, 640, 480);
    stage.setScene(scene);
  }
  @Disabled
  @Test
  public void test_start_button_pressed(FxRobot robot) throws IOException {
    Platform.runLater(() -> {

      controller.onButtonStart(new ActionEvent(b, null));

    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void test_exit_button_pressed(FxRobot robot) {
    Platform.runLater(() -> {
      controller.onButtonExit(new ActionEvent(b, null));

    });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
