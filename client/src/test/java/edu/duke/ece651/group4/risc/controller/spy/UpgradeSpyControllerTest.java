package edu.duke.ece651.group4.risc.controller.spy;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.controller.TestMapGenerator;
import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.Territory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(ApplicationExtension.class)
class UpgradeSpyControllerTest {
    private UpgradeSpyController cont;
    ChoiceBox<String> source;
    Text cost;
    TextField unit_num;
    Slider unit_level;
    Text alert;
    AnchorPane pane;
    Button done_btn;
    private Client mockClient;

    @Start
    private void start(Stage stage){
        mockClient = Mockito.mock(Client.class);
        Map<Character> testMap = TestMapGenerator.generateTestMap();
        Mockito.when(mockClient.getMap()).thenReturn(testMap);
        Mockito.when(mockClient.getPlayerId()).thenReturn(0);
        cont = new UpgradeSpyController(mockClient, stage);
        //add fxml element
        source = new ChoiceBox<>();
        cost = new Text();
        unit_num = new TextField();
        alert = new Text();
        unit_level = new Slider();
        pane = new AnchorPane();
        done_btn = new Button();
        pane.getChildren().addAll(source, cost, unit_num, alert, unit_level, done_btn);
        cont.unit_level = unit_level;
        cont.alert = alert;
        cont.cost = cost;
        cont.source = source;
        cont.unit_num =unit_num;
        cont.done_btn = done_btn;
        cont.pane = pane;
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        cont.initialize();

    }

    @Test
    void checkIntegerValid() {
        Platform.runLater(() -> {
            try {
                FxAssert.verifyThat(cost, TextMatchers.hasText("Unavailable"));
                assertEquals(false, cont.checkIntegerValid());
                unit_num.setText("1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(true, cont.checkIntegerValid());
       // unit_num.setText("ab");
        //assertEquals(false, cont.checkIntegerValid());

        Platform.runLater(() -> {
            try {
                unit_num.setText("ab");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(false, cont.checkIntegerValid());

    }

    @Test
    void showCost_avai() {
        Platform.runLater(() -> {
            try {
                cont.showCost();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(cost, TextMatchers.hasText("Unavailable"));
    }

    @Test
    void showCost_integer() {
        Platform.runLater(() -> {
            try {
                unit_level.setValue(1.5);
                cont.showCost();
                unit_level.setValue(1);
                unit_num.setText("1");
                source.getSelectionModel().selectFirst();
                cont.showCost();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(cost, TextMatchers.hasText("20"));
    }

    @Test
    void showCost_unit_num_err() {
        Platform.runLater(() -> {
            try {
                unit_num.setText("abc");
                source.getSelectionModel().selectFirst();
                cont.showCost();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(cost, TextMatchers.hasText("Level up or num needs to be integer"));
    }

    @Test
    void done_err() {
        Platform.runLater(() -> {
            try {
                unit_num.setText("1");
                unit_level.setValue(1);
                source.getSelectionModel().selectFirst();
                Mockito.when(mockClient.addOrder(any())).thenReturn("failure");
                cont.done(new ActionEvent(done_btn, null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(alert, TextMatchers.hasText("failure"));
    }
    @Test
    public void done_correct(){
        Platform.runLater(() -> {
            try {
                unit_num.setText("1");
                unit_level.setValue(1);
                source.getSelectionModel().selectFirst();
                Mockito.when(mockClient.addOrder(any())).thenReturn(null);
                cont.done(new ActionEvent(done_btn, null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
    }
}