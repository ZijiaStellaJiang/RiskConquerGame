package edu.duke.ece651.group4.risc.client;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import java.io.IOException;

import edu.duke.ece651.group4.risc.controller.TestMapGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.util.WaitForAsyncUtils;

import edu.duke.ece651.group4.risc.shared.Map;
import javafx.application.Platform;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class MapGUIViewTest {
  private Client mockClient;
  private Map<Character> testMap;

  @Start
  private void start(Stage stage) {
    // mock client
    mockClient = Mockito.mock(Client.class);
    Mockito.when(mockClient.getPlayerId()).thenReturn(0);
    testMap = TestMapGenerator.generateTestMap();
    Mockito.when(mockClient.getMap()).thenReturn(testMap);
    Mockito.when(mockClient.getPlayerId()).thenReturn(0);
    //Mockito.when(mockClient.getMap().getPlayerName(anyInt())).thenReturn("Green");
  }

  @Test
  public void test_gui_view_showup(FxRobot robot) throws IOException {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try{
              MapGUIView guiview = new MapGUIView(mockClient);
              guiview.showMain();
              // check whether all element exists
              FxAssert.verifyThat("#oz", isVisible());
              FxAssert.verifyThat("#hogwarts", isVisible());
              FxAssert.verifyThat("#narnia", isVisible());
              FxAssert.verifyThat("#mordor", isVisible());
              FxAssert.verifyThat("#elantris", isVisible());
              FxAssert.verifyThat("#gondor", isVisible());
              //FxAssert.verifyThat("#player_color", TextInputControlMatchers.hasText("Green"));
        }catch (Exception e){

        }
      }
    });

    WaitForAsyncUtils.waitForFxEvents();

  }
}
