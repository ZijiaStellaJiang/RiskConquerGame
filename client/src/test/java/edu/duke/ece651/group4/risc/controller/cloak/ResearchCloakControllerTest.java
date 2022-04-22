package edu.duke.ece651.group4.risc.controller.cloak;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.controller.TestMapGenerator;
import edu.duke.ece651.group4.risc.shared.Map;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@ExtendWith(ApplicationExtension.class)
class ResearchCloakControllerTest {
    private Client mockClient;
    private ResearchCloakController cont;
    private Text alert;
    private Button yes_btn;
    private AnchorPane pane;
    @Start
    private void start(Stage stage){
        mockClient = Mockito.mock(Client.class);
        Map<Character> testMap = TestMapGenerator.generateTestMap();
        Mockito.when(mockClient.getMap()).thenReturn(testMap);
        Mockito.when(mockClient.getPlayerId()).thenReturn(0);

        cont = new ResearchCloakController(mockClient);
        alert = new Text();
        Button yes_btn = new Button();
        pane = new AnchorPane();
        cont.yes_btn = yes_btn;
        cont.alert = alert;
        cont.pane = pane;
        pane.getChildren().addAll(alert, yes_btn);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }

    @Test
    void test_research_clock_failure() throws IOException {
        String expected = "failure";
        Mockito.when(mockClient.addOrder(any())).thenReturn(expected);
        cont.researchCloak();
        FxAssert.verifyThat(cont.alert, TextMatchers.hasText(expected));
    }

    @Test
    void test_research_clock_success() throws IOException {
        Platform.runLater(() -> {
            try {
                Mockito.when(mockClient.addOrder(any())).thenReturn(null);
                cont.researchCloak();
                cont.exit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        //FxAssert.verifyThat("#showMove", isVisible());
    }


}