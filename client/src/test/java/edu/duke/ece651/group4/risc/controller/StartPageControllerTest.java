package edu.duke.ece651.group4.risc.controller;

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

@ExtendWith(ApplicationExtension.class)
public class StartPageControllerTest {
  private StartPageController controller;

  @Start
  private void start(Stage stage){
    controller = new StartPageController();
  }
  @Test
  public void test_button_pressed(FxRobot robot) {
    Platform.runLater(()->{Button b = new Button("START");
                      controller.onButton(new ActionEvent(b, null));
                      
      });
    WaitForAsyncUtils.waitForFxEvents();
  }

}
