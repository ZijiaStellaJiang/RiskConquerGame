package edu.duke.ece651.group4.risc.controller.spy;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.controller.TestMapGenerator;
import edu.duke.ece651.group4.risc.shared.Map;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.testfx.matcher.base.NodeMatchers.isVisible;


@ExtendWith(ApplicationExtension.class)
class MoveSpyControllerTest {
    AnchorPane pane;
    private MoveSpyController cont;
    private Client mockClient;
    private TextField unit_num;
    Text cost;
    Button done_btn;
    ChoiceBox<String> source;
    ChoiceBox<String> dest;
    Text alert;
    @Start
    private void start(Stage stage){
        mockClient = Mockito.mock(Client.class);
        Map<Character> testMap = TestMapGenerator.generateTestMap();
        Mockito.when(mockClient.getMap()).thenReturn(testMap);
        Mockito.when(mockClient.getPlayerId()).thenReturn(0);

        pane = new AnchorPane();
        unit_num = new TextField();
        source = new ChoiceBox<String>();
        dest = new ChoiceBox<String>();
        cost = new Text();
        alert = new Text();
        done_btn = new Button();
        cont = new MoveSpyController(mockClient, stage);
        pane.getChildren().addAll(unit_num, source, dest, cost, alert, done_btn);
        cont.unit_num = unit_num;
        cont.pane = pane;
        cont.done_btn = done_btn;
        cont.destination = dest;
        cont.source = source;
        cont.cost = cost;
        cont.alert = alert;
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        cont.initialize();
        cont.setup();
    }


    @Test
    public void test_write_unit_num(){
        Platform.runLater(() -> {
            try {
                assertEquals(true, cont.checkIntegerValid(""));
                assertEquals(false, cont.checkIntegerValid("a"));
                assertEquals(true, cont.checkIntegerValid("123"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void test_show_cost_avaibale(){
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
    public void test_show_cost_not_integer(){
        Platform.runLater(() -> {
            try {
                source.getSelectionModel().selectFirst();
                dest.getSelectionModel().select(0);
                unit_num.setText("abc");
                cont.showCost();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(cost, TextMatchers.hasText("unit number needs to be integer"));
    }

    @Test
    public void test_correct_cost(){
        Platform.runLater(() -> {
            try {
                source.getSelectionModel().select("Narnia");
                dest.getSelectionModel().select("Narnia");
                unit_num.setText("1");
                cont.showCost();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(cost, TextMatchers.hasText("food: 5"));
    }


    @Test
    public void test_done_no_enter(){
        Platform.runLater(() -> {
            try {
                unit_num.setText("");
                cont.done(new ActionEvent(done_btn, null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(alert, TextMatchers.hasText("Invalid input"));
    }

    @Test
    public void test_done_no_enter1(){
        Platform.runLater(() -> {
            try {
                source.getSelectionModel().clearSelection();
                cont.done(new ActionEvent(done_btn, null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(alert, TextMatchers.hasText("Invalid input"));
    }

    @Test
    public void test_done_no_integer(){
        Platform.runLater(() -> {
            try {
                source.getSelectionModel().select("Narnia");
                dest.getSelectionModel().select("Narnia");
                unit_num.setText("abc");
                cont.done(new ActionEvent(done_btn, null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(alert, TextMatchers.hasText("unit number needs to be integer"));
    }

    @Test
    public void test_done_add_order_failure(){
        Platform.runLater(() -> {
            try {
                source.getSelectionModel().select("Narnia");
                dest.getSelectionModel().select("Narnia");
                unit_num.setText("1");
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
    public void test_done_add_order_success(){
        Platform.runLater(() -> {
            try {
                source.getSelectionModel().select("Narnia");
                dest.getSelectionModel().select("Narnia");
                unit_num.setText("1");
                Mockito.when(mockClient.addOrder(any())).thenReturn(null);
                cont.done(new ActionEvent(done_btn, null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        //FxAssert.verifyThat("#done_btn", Node::isVisible());

    }

}