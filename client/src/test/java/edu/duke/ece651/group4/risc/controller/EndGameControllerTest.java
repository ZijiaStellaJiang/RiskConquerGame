package edu.duke.ece651.group4.risc.controller;

import static org.testfx.matcher.base.NodeMatchers.isVisible;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class EndGameControllerTest {
  private EndGameController cont;
  private Text msg;
  private AnchorPane pane;
  private Button btn;
  @Start
  private void start(Stage stage) {
    msg = new Text();
    pane = new AnchorPane();
    btn = new Button();
    pane.getChildren().add(btn);
    pane.getChildren().add(msg);
    cont = new EndGameController();
    cont.pane = pane;
    cont.end_msg = msg;
    Scene scene = new Scene(pane);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * test whether text msg contains desired message
   */
  @Test
  public void test_msg_show(FxRobot robot) {
    cont.setup("test msg");
    FxAssert.verifyThat(msg, TextMatchers.hasText("test msg"));
  }

  @Test
  public void test_show_main(FxRobot robot) throws IOException {
    Platform.runLater(() -> {
      try {
        cont.backToMain(new ActionEvent(btn, null));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    //FxAssert.verifyThat(text,TextMatchers.hasText("cannot connect to server") );
    //robot.clickOn(btn);
    //cont.backToMain(new ActionEvent(btn, null));
    // check new stage pop up
    FxAssert.verifyThat("#start_btn", isVisible());
    FxAssert.verifyThat("#exit_btn", isVisible());

  }

}
