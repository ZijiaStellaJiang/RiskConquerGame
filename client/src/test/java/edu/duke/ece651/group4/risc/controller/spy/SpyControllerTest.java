package edu.duke.ece651.group4.risc.controller.spy;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.controller.TestMapGenerator;
import edu.duke.ece651.group4.risc.shared.Map;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@ExtendWith(ApplicationExtension.class)
class SpyControllerTest {
    private SpyController cont;
    private Client mockClient;
    Button move_btn;
    Button upgrade_btn;
    AnchorPane pane;

    @Start
    private void start(Stage stage){
        mockClient = Mockito.mock(Client.class);
        Map<Character> testMap = TestMapGenerator.generateTestMap();
        Mockito.when(mockClient.getMap()).thenReturn(testMap);
        //Mockito.when(mockClient.getClientTerritories()).thenReturn(terr);
        Mockito.when(mockClient.getPlayerId()).thenReturn(0);
        cont = new SpyController(mockClient);
        move_btn = new Button();
        upgrade_btn = new Button();
        pane = new AnchorPane();
        cont.move_btn = move_btn;
        cont.upgrade_btn = upgrade_btn;
        pane = new AnchorPane();
        pane.getChildren().addAll(move_btn, upgrade_btn);
        cont.pane = pane;
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @Disabled
    @Test
    public void test_upgrade_action(){
        Platform.runLater(() -> {
            try {
                cont.upgradeAction(new ActionEvent(upgrade_btn, null));
                //FxAssert.verifyThat(cont.alert, TextMatchers.hasText("Please enter territory"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#unit_level", isVisible());
    }

}
