package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.shared.Territory;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.checkerframework.dataflow.qual.TerminatesExecution;

import java.util.ArrayList;

public class TerritoryDetailController {

    @FXML
    TextArea detail;
    @FXML
    Text name;
    @FXML
    Text size;
    @FXML
    Text food;
    @FXML
    Text wood;
    @FXML
    ImageView unit0_img;
    @FXML
    Text unit0;
    @FXML
    ImageView unit1_img;
    @FXML
    Text unit1;
    @FXML
    ImageView unit2_img;
    @FXML
    Text unit2;
    @FXML
    ImageView unit3_img;
    @FXML
    Text unit3;
    @FXML
    ImageView unit4_img;
    @FXML
    Text unit4;
    @FXML
    ImageView unit5_img;
    @FXML
    Text unit5;
    @FXML
    ImageView unit6_img;
    @FXML
    Text unit6;
    private Territory<Character> terr;
    private int player_id;
    public TerritoryDetailController(){

    }
    public TerritoryDetailController(Territory<Character> terr, int player_id){
        this.terr = terr;
        this.player_id = player_id;
    }

    public void setup(String content){
        detail.setText(content);name.setText(terr.getName());
        size.setText(""+terr.getSize());
        food.setText(""+terr.getFoodAbility());
        wood.setText(""+terr.getWoodAbility());
        //show unit
        showUnit();

    }
    public void showUnit(){
        //Integer units = terr.getUnitNumber();
        //place images
        if(player_id==1){
            Image image1= new Image("pic/unit_icon/blue/jocker.png", 400, 300, true, false);
            unit0.setText(""+terr.getLevelUnitNum(0));
            unit0_img.setImage(image1);

            Image image2= new Image("pic/unit_icon/blue/headsman.png", 400, 300, true, false);
            unit1.setText(""+terr.getLevelUnitNum(1));
            unit1_img.setImage(image2);
            Image image3= new Image("pic/unit_icon/blue/archer.png", 400, 300, true, false);
            unit2.setText(""+terr.getLevelUnitNum(2));
            unit2_img.setImage(image3);
            Image image4= new Image("pic/unit_icon/blue/swordsman3_1.png", 400, 300, true, false);
            unit3.setText(""+terr.getLevelUnitNum(3));
            unit3_img.setImage(image4);

            Image image5 =new Image("pic/unit_icon/blue/knight4_1.png", 400, 300, true, false);
            unit4.setText(""+terr.getLevelUnitNum(4));
            unit4_img.setImage(image5);

            Image image6= new Image("pic/unit_icon/blue/ship5_1.png", 400, 300, true, false);
            unit5.setText(""+terr.getLevelUnitNum(5));
            unit5_img.setImage(image6);

            Image image7= new Image("pic/unit_icon/blue/wizard6_1.png", 400, 300, true, false);
            unit6.setText(""+terr.getLevelUnitNum(6));
            unit6_img.setImage(image7);

        }

    }
    //public void setup(){

    //}
}
