package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.shared.Territory;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class TerritoryDetailController {
    @FXML
    AnchorPane pane;
    @FXML
    Text detail;
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
    @FXML
    Group group4;
    @FXML
    Group group2;
    @FXML
    Group group6;
    @FXML
    Text cloak_num;
    private Territory<Character> terr;
    private int player_id;
    private boolean ifMine;
    private int cNum;

    public TerritoryDetailController(){

    }

    /**
     * Constructor
     * @param terr territory
     * @param player_id player id
     * @param ifMine indicate if mine
     * @param cloak_num cloak number
     */
    public TerritoryDetailController(Territory<Character> terr, int player_id, boolean ifMine, int cloak_num){
        this.terr = terr;
        this.player_id = player_id;
        this.ifMine = ifMine;
        this.cNum = cloak_num;
    }

    /**
     * Shows name, food, wood, info
     * @param content content to display
     */
    public void showOtherInfo(String content){
        detail.setText(content);
        name.setText(terr.getName());
        size.setText(""+terr.getSize());
        food.setText(""+terr.getFoodAbility());
        wood.setText(""+terr.getWoodAbility());
    }

    /**
     * Set up page
     */
    public void setup(){
        //show unit
        showUnit();
        if(ifMine){
            showCloak();
        }
    }

    /**
     * Adjust image for better apparencec
     * @param image
     * @param width
     * @param height
     * @param images
     */
    public void adjustImageSize(ImageView image, int width, int height, Image images){
        image.setImage(images);
        image.setFitHeight(height);
        image.setFitWidth(width);
    }

    /**
     * Set if mine for test case
     * @param ans
     */
    public void setIfMine(boolean ans){
        ifMine = ans;
    }

    /**
     * Set player id for test case
     * @param id
     */
    public void setPlayer_id(int id){
        this.player_id = id;
    }

    /**
     * Display Unit Icon and image
     * @param units
     * @param ifMySelf
     */
    public void addUnit(ArrayList<Integer> units, boolean ifMySelf){
        if((player_id==1 && ifMySelf==true) || (player_id==0 && ifMySelf==false)){
            Image image1= new Image("pic/unit_icon/blue/jocker.png", 480, 360, true, false);
            unit0.setText(""+units.get(0));
            adjustImageSize(unit0_img, 102, 132, image1);

            Image image2= new Image("pic/unit_icon/blue/headsman.png", 400, 300, true, false);
            unit1.setText(""+units.get(1));
            unit1_img.setImage(image2);

            Image image3= new Image("pic/unit_icon/blue/archer.png", 400, 300, true, false);
            unit2.setText(""+units.get(2));
            unit2_img.setImage(image3);
            Image image4= new Image("pic/unit_icon/blue/swordsman3_1.png", 400, 300, true, false);
            unit3.setText(""+units.get(3));
            unit3_img.setImage(image4);

            Image image5 =new Image("pic/unit_icon/blue/knight4_1.png", 400, 300, true, false);
            unit4.setText(""+units.get(4));
            unit4_img.setImage(image5);

            Image image6= new Image("pic/unit_icon/blue/ship5_1.png", 400, 300, true, false);
            unit5.setText(""+units.get(5));
            unit5_img.setImage(image6);

            Image image7= new Image("pic/unit_icon/blue/wizard6_1.png", 400, 300, true, false);
            unit6.setText(""+units.get(6));
            unit6_img.setImage(image7);
        }

        if((player_id==0 && ifMySelf==true) || (player_id==1 && ifMySelf==false)){
            Image image1= new Image("pic/unit_icon/green/acrobat_1.png", 400, 300, true, false);
            unit0.setText(""+units.get(0));
            unit0_img.setImage(image1);
            unit0.setLayoutX(35);

            Image image2= new Image("pic/unit_icon/green/2.png", 560, 420, true, false);
            unit1.setText(""+units.get(1));
            adjustImageSize(unit1_img, 119, 152, image2);
            group2.setLayoutX(190);
            group2.setLayoutY(242);
            unit1.setLayoutY(120);

            Image image3= new Image("pic/unit_icon/green/3.png", 400, 300, true, false);
            unit2.setText(""+units.get(2));
            unit2_img.setImage(image3);
            unit2.setLayoutX(40);
            Image image4= new Image("pic/unit_icon/green/4.png", 300, 225, true, false);
            unit3.setText(""+units.get(3));
            unit3_img.setImage(image4);

            Image image5 =new Image("pic/unit_icon/green/5.png", 480, 360, true, false);
            unit4.setText(""+units.get(4));
            unit4_img.setImage(image5);
            adjustImageSize(unit4_img, 102, 132, image5);
            group4.setLayoutY(370);
            unit4.setLayoutY(140);

            Image image6= new Image("pic/unit_icon/green/6.png", 400, 300, true, false);
            unit5.setText(""+units.get(5));
            adjustImageSize(unit5_img, 112, 145, image6);
            group6.setLayoutX(200);
            unit5.setLayoutX(50);
            unit5.setLayoutY(115);

            Image image7= new Image("pic/unit_icon/green/7.png", 400, 300, true, false);
            unit6.setText(""+units.get(6));
            unit6_img.setImage(image7);
            unit6.setLayoutX(30);
        }

    }

    /**
     * Shows cloak info
     */
    public void showCloak(){
        cloak_num.setText(String.valueOf(cNum));
    }


    /**
     * Show number of each unit
     */
    public void showUnit(){
        String text;
        if(ifMine){
            text = "This is your territory info";
            ArrayList<Integer> myUnits = terr.getMyInfo();
            showOtherInfo(text);
            addUnit(myUnits, true);
        }else{
            ArrayList<Integer> enemyUnits = terr.getEnemyInfo();
            if(enemyUnits==null){
                detail.setText("you can not see the territory details");
                //never been seen before
            }else{
                if(terr.checkLatest()){
                    text = "you can see enemy's new info\n";
                }else{
                    text = "you can see the old info\n";
                }
                showOtherInfo(text);
                addUnit(enemyUnits, false);
            }
        }

    }
}
