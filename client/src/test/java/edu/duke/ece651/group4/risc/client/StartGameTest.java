package edu.duke.ece651.group4.risc.client;

import java.io.IOException;

import javafx.scene.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.ButtonMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import javafx.stage.Stage;
import org.testfx.matcher.control.TextMatchers;

import static org.testfx.matcher.base.NodeMatchers.isVisible;

@ExtendWith(ApplicationExtension.class)
public class StartGameTest {
  StartGame game;
  //Button start;
  @Start
  public void start(Stage stage) throws IOException{
    game = new StartGame();
    game.start(stage);
    
  }
  
  @Test
  public void test_start_page(FxRobot robot) {
  FxAssert.verifyThat("#start_btn", isVisible());
    FxAssert.verifyThat("#exit_btn", isVisible());
  }

}
