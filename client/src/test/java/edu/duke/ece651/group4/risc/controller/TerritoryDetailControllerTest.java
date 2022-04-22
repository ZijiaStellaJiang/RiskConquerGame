package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.shared.Territory;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class TerritoryDetailControllerTest {
    private TerritoryDetailController cont;
    private Territory<Character> terr;
    Text detail;
    Text name;
    Text size;
    Text food;
    Text wood;
    AnchorPane pane;
    Text cloak_num;
    Text unit0;
    Text unit1;
    Text unit2;
    Text unit3;
    Text unit4;
    Text unit5;
    Text unit6;
    Group group2;
    Group group4;
    Group group6;
    ImageView unit0_img;
    ImageView unit1_img;
    ImageView unit2_img;
    ImageView unit3_img;
    ImageView unit4_img;
    ImageView unit5_img;
    ImageView unit6_img;
    private Territory<Character> territory;

    @Start
    private void start(Stage stage){
        terr = Mockito.mock(Territory.class);
        Mockito.when(terr.getName()).thenReturn("Narnia");
        Mockito.when(terr.getSize()).thenReturn(1);
        Mockito.when(terr.getFoodAbility()).thenReturn(10);
        Mockito.when(terr.getWoodAbility()).thenReturn(10);
        //terr = new Territory<Character>("Narnia", 1, 10, 10);
        cont = new TerritoryDetailController(terr, 0, true, 1);
        detail = new Text();
        name = new Text();
        size = new Text();
        food = new Text();
        wood = new Text();
        unit0 = new Text();
        unit1 = new Text();
        unit2 = new Text();
        unit3 = new Text();
        unit4 = new Text();
        unit5 = new Text();
        unit6 = new Text();
        cloak_num = new Text();
        unit0_img = new ImageView();
        unit1_img = new ImageView();
        unit2_img = new ImageView();
        unit3_img = new ImageView();
        unit4_img = new ImageView();
        unit5_img = new ImageView();
        unit6_img = new ImageView();
        group2 = new Group();
        group4 = new Group();
        group6 = new Group();
        pane = new AnchorPane();
        cont.detail = detail;
        cont.name = name;
        cont.size = size;
        cont.wood = wood;
        cont.food = food;
        cont.pane = pane;
        cont.unit0 = unit0;
        cont.unit1 = unit1;
        cont.unit2 = unit2;
        cont.unit3 = unit3;
        cont.unit4 = unit4;
        cont.unit5 = unit5;
        cont.unit6 = unit6;
        cont.unit0_img = unit0_img;
        cont.unit1_img = unit1_img;
        cont.unit2_img = unit2_img;
        cont.unit3_img = unit3_img;
        cont.unit4_img = unit4_img;
        cont.unit5_img = unit5_img;
        cont.unit6_img = unit6_img;
        cont.cloak_num = cloak_num;
        cont.group6 = group6;
        cont.group2 = group2;
        cont.group4 = group4;
        pane.getChildren().addAll(detail, name, size, wood, food, cloak_num);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }


    @Test
    public void test_show_other_info(){
        Platform.runLater(() -> {
            try {
               cont.showOtherInfo("test");
               cont.showCloak();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(detail, TextMatchers.hasText("test"));
        FxAssert.verifyThat(name, TextMatchers.hasText("Narnia"));
        FxAssert.verifyThat(size, TextMatchers.hasText("1"));
        FxAssert.verifyThat(food, TextMatchers.hasText("10"));
        FxAssert.verifyThat(wood, TextMatchers.hasText("10"));
        FxAssert.verifyThat(cloak_num, TextMatchers.hasText("1"));
    }


    @Test
    public void test_show_cloak(){
        Platform.runLater(() -> {
            try {
                ArrayList<Integer> ans = new ArrayList<>();
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                Mockito.when(terr.getMyInfo()).thenReturn(ans);
                cont.setup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(detail, TextMatchers.hasText("This is your territory info"));
    }

    @Test
    public void test_show_cloak_notMy(){
        Platform.runLater(() -> {
            try {
                Mockito.when(terr.getEnemyInfo()).thenReturn(null);
                cont.setIfMine(false);
                cont.setup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(detail, TextMatchers.hasText("you can not see the territory details"));

        Platform.runLater(() -> {
            try {
                ArrayList<Integer> ans = new ArrayList<>();
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                Mockito.when(terr.getEnemyInfo()).thenReturn(ans);
                Mockito.when(terr.checkLatest()).thenReturn(true);
                cont.setIfMine(false);
                cont.setup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(detail, TextMatchers.hasText("you can see enemy's new info\n"));
    }


    @Test
    public void test_show(){
        Platform.runLater(() -> {
            try {
                ArrayList<Integer> ans = new ArrayList<>();
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                Mockito.when(terr.getEnemyInfo()).thenReturn(ans);
                Mockito.when(terr.checkLatest()).thenReturn(true);
                cont.setIfMine(false);
                cont.setup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(detail, TextMatchers.hasText("you can see enemy's new info\n"));
    }

    @Test
    public void test_show1(){
        Platform.runLater(() -> {
            try {
                ArrayList<Integer> ans = new ArrayList<>();
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                Mockito.when(terr.getEnemyInfo()).thenReturn(ans);
                Mockito.when(terr.checkLatest()).thenReturn(false);
                cont.setIfMine(false);
                cont.setup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(detail, TextMatchers.hasText("you can see the old info\n"));
    }

    @Test
    public void test_show2(){
        Platform.runLater(() -> {
            try {
                ArrayList<Integer> ans = new ArrayList<>();
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                ans.add(1);
                cont.setPlayer_id(0);
                Mockito.when(terr.getEnemyInfo()).thenReturn(ans);
                Mockito.when(terr.getMyInfo()).thenReturn(ans);
                Mockito.when(terr.checkLatest()).thenReturn(false);
                cont.setIfMine(true);
                cont.setup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(detail, TextMatchers.hasText("This is your territory info"));
    }


}