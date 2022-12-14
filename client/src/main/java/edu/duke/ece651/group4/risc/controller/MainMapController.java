package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.controller.cloak.CloakController;
import edu.duke.ece651.group4.risc.controller.cloak.ResearchCloakController;
import edu.duke.ece651.group4.risc.controller.spy.SpyController;
import edu.duke.ece651.group4.risc.shared.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainMapController {
  private Client client;// model

  @FXML
  Button oz;
  @FXML
  Button hogwarts;
  @FXML
  Button narnia;
  @FXML
  Button gondor;
  @FXML
  Button elantris;
  @FXML
  Button mordor;
  @FXML
  Tooltip oz_tooltip;
  @FXML
  Text player_color;
  @FXML
  AnchorPane background;
  @FXML
  Tooltip hogwarts_tooltip;
  @FXML
  Tooltip narnia_tooltip;
  @FXML
  Tooltip gondor_tooltip;
  @FXML
  Tooltip elantris_tooltip;
  @FXML
  Tooltip mordor_tooltip;
  @FXML
  Text wait_msg;
  @FXML
  Button showMove;
  @FXML
  Button showAttack;
  @FXML
  Button showUpgrade;
  @FXML
  Button commit_btn;
  @FXML
  Text food;
  @FXML
  Text wood;
  @FXML
  Text victory_msg;
  @FXML
  ImageView player_img;
  @FXML
  Button spy_btn;
  private HashMap<Class<?>, Object> controllers;
  private HashMap<Integer, String> name_set1;
  private HashMap<Integer, String> name_set2;
  @FXML
  Button cloak_btn;
  @FXML
  ImageView cloak1;
  @FXML
  ImageView cloak2;
  @FXML
  ImageView cloak3;
  @FXML
  ImageView cloak4;
  @FXML
  ImageView cloak5;
  @FXML
  ImageView cloak6;
  @FXML
  ImageView spy1;
  @FXML
  ImageView spy2;
  @FXML
  ImageView spy3;
  @FXML
  ImageView spy4;
  @FXML
  ImageView spy5;
  @FXML
  ImageView spy6;
  private ArrayList<ImageView> cloak_icons;
  private ArrayList<String> terr_names;
  private ArrayList<ImageView> spy_icons;
  public MainMapController(Client client) {
    this.client = client;
    System.out.println(client.getPlayerId());
    controllers = new HashMap<>();
    name_set1 = new HashMap<>();
    name_set2 = new HashMap<>();

    cloak_icons = new ArrayList<ImageView>();
    spy_icons = new ArrayList<ImageView>();

    terr_names = new ArrayList<>();
    terr_names.add("Oz");
    terr_names.add("Narnia");
    terr_names.add("Mordor");
    terr_names.add("Hogwarts");
    terr_names.add("Elantris");
    terr_names.add("Gondor");

  }
  @FXML
  public void initialize(){
    showMove.addEventHandler(MouseEvent.MOUSE_ENTERED, e->showMove.setCursor(Cursor.HAND));
    showAttack.addEventHandler(MouseEvent.MOUSE_ENTERED, e->showAttack.setCursor(Cursor.HAND));
    showUpgrade.addEventHandler(MouseEvent.MOUSE_ENTERED, e->showUpgrade.setCursor(Cursor.HAND));
    commit_btn.addEventHandler(MouseEvent.MOUSE_ENTERED, e->commit_btn.setCursor(Cursor.HAND));
    cloak_btn.addEventHandler(MouseEvent.MOUSE_ENTERED, e->cloak_btn.setCursor(Cursor.HAND));
    spy_btn.addEventHandler(MouseEvent.MOUSE_ENTERED, e->spy_btn.setCursor(Cursor.HAND));
    cloak_icons.add(cloak1);
    cloak_icons.add(cloak2);
    cloak_icons.add(cloak3);
    cloak_icons.add(cloak4);
    cloak_icons.add(cloak5);
    cloak_icons.add(cloak6);
    spy_icons.add(spy1);
    spy_icons.add(spy2);
    spy_icons.add(spy3);
    spy_icons.add(spy4);
    spy_icons.add(spy5);
    spy_icons.add(spy6);
    for(int i=0; i<cloak_icons.size(); i++){
      cloak_icons.get(i).setVisible(false);
      spy_icons.get(i).setVisible(false);
    }
    client.getMap().updateOneRound();
  }
  /**
   * tell user they are green player or blue player
   */
  public void displayPlayerMsg(){
    String player_name = client.getMap().getPlayerName(client.getPlayerId());
    player_color.setText(player_name);
    String image_path;
    if(client.getPlayerId()==0){
      image_path = "pic/map/golden.png";
    }else{
      image_path = "pic/map/roman.png";
    }

    Image player_title = new Image(image_path, 800, 600, true, false);
    player_img.setImage(player_title);
  }

  /**
   * Search for the territories belong to myself
   * @return the territories arraylist belong to myself
   */
  public ArrayList<Territory<Character>> displayMyTerritory() {
    Map<Character> myMap = client.getMap();
    ArrayList<Player<Character>> players = myMap.getMyPlayers();
    System.err.println(players.get(0).getName());
    for (Territory<Character> terr : players.get(client.getPlayerId()).getMyTerritories()) {
      System.out.println(terr.getName());
    }
    return players.get(client.getPlayerId()).getMyTerritories();

  }

  /**
   * Updates food and wood resources
   */
  public void updateFoodAndWood(){
    food.setText(""+client.getPlayerFood());
    wood.setText(""+client.getPlayerWood());
  }

  /**
   * set border of territory according to which player the territory belong to
   */
  public void displayTerritoryBorder() {
    Map<Character> map = client.getMap();
    Player<Character>  p= map.getMyPlayers().get(client.getPlayerId());
    //for (Player<Character> p : map.getMyPlayers()) {
      for (Territory<Character> myTerri : p.getMyTerritories()) {
        String terr_name = myTerri.getName().toUpperCase();
        for (Node element : background.getChildren()) {
          if (element instanceof Button) {
            if (element.getId() != null) {
              Button btn = (Button) element;
              String btn_name = btn.getText().toUpperCase();
              if (btn_name.equals(terr_name)) {
                String player_name = p.getName().toUpperCase();
                if (player_name.equals("GREEN")) {
                  btn.setStyle("-fx-border-color: green");
                } else {
                  btn.setStyle("-fx-border-color: blue");
                }
              }
            }
          }
        }
      }
    //}
    updateFoodAndWood();
  }

  /**
   * Display territory info at tooltip
   * 
   * @param toDisplay territory
   * @return the details of this territory
   */
//  public String displayTerritoryInfo(Territory<Character> toDisplay) {
//    StringBuilder sb = new StringBuilder(toDisplay.getName());
//    sb.append("\n--------------------\nSize : ");
//    sb.append(toDisplay.getSize());
//    sb.append("\nFood Ability : ");
//    sb.append(toDisplay.getFoodAbility());
//    sb.append("\nWood Ability : ");
//    sb.append(toDisplay.getWoodAbility());
//    sb.append("\nUnits :\n   Level 0 : ");
//    sb.append(toDisplay.getLevelUnitNum(0));
//    sb.append("\n   Level 1 : ");
//    sb.append(toDisplay.getLevelUnitNum(1));
//    sb.append("\n   Level 2 : ");
//    sb.append(toDisplay.getLevelUnitNum(2));
//    sb.append("\n   Level 3 : ");
//    sb.append(toDisplay.getLevelUnitNum(3));
//    sb.append("\n   Level 4 : ");
//    sb.append(toDisplay.getLevelUnitNum(4));
//    sb.append("\n   Level 5 : ");
//    sb.append(toDisplay.getLevelUnitNum(5));
//    sb.append("\n   Level 6 : ");
//    sb.append(toDisplay.getLevelUnitNum(6));
//    return sb.toString();
//  }

  /**
   * Show details of the territory
   * @param text
   * @throws IOException
   */
//  public void  showDetails(String text) throws IOException {
//    URL xmlResource = getClass().getResource("/ui/TerritoryDetail.fxml");
//    URL cssResource = getClass().getResource("/ui/button.css");
//    FXMLLoader loader = new FXMLLoader(xmlResource);
//    // setup controller
//    controllers.put(TerritoryDetailController.class, new TerritoryDetailController());
//    loader.setControllerFactory((c) -> {
//      return controllers.get(c);
//    });
//    AnchorPane gp = loader.load();
//    TerritoryDetailController territoryDetailController = loader.getController();
//    territoryDetailController.setup();
//    Scene scene = new Scene(gp);
//    scene.getStylesheets().add(cssResource.toString());
//    Stage stage = new Stage();
//    stage.setScene(scene);
//    stage.show();
//  }

  /*public void tooltipInit(){
    for(int i=0; i<cloak_icons.size(); i++){
      spy_tip.add(new Tooltip());
      cloak_tip.add(new Tooltip());

    }

  }


  public void tooltipInstall(){
    for(int i=0; i <cloak_icons.size(); i++){
      Tooltip.install(cloak_icons.get(i), cloak_tip.get(i));
      Tooltip.install(spy_icons.get(i), spy_tip.get(i));
    }
  }*/
  /**
   * Show the territory details in fog war
   * @param terr
   * @throws IOException
   */
  public void showFogWar(Territory<Character> terr) throws IOException {
    URL xmlResource = getClass().getResource("/ui/TerritoryDetail.fxml");
    URL cssResource = getClass().getResource("/ui/button.css");
    FXMLLoader loader = new FXMLLoader(xmlResource);

    int cloak_num = client.cloakRemain(terr.getName());
    // setup controller
    controllers.put(TerritoryDetailController.class, new TerritoryDetailController(terr, client.getPlayerId(), client.territoryIsMine(terr.getName()), cloak_num));
    loader.setControllerFactory((c) -> {
      return controllers.get(c);
    });
    AnchorPane gp = loader.load();
    TerritoryDetailController territoryDetailController = loader.getController();
    territoryDetailController.setup();
    Scene scene = new Scene(gp, 600, 600);
    scene.getStylesheets().add(cssResource.toString());
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * display details of each territory
   * 
   * @param ae action event
   */
  @FXML
  public void displayTerritory(ActionEvent ae) throws IOException {
    Button source = (Button) ae.getSource();
    String territory_name = source.getId();
    Territory<Character> terr = client.getMap().findTerritory(territory_name);
    //check whether this territory belongs to my self
    showFogWar(terr);

  }

  /**
   * Init loader
   * @param fxml loader file
   * @return
   */
  public FXMLLoader loadLoader(String fxml){
    URL xmlResource = getClass().getResource(fxml);
    FXMLLoader loader = new FXMLLoader(xmlResource);
    return loader;
  }

  /**
   * Loads new page
   * @param loader fxml loader
   * @param css css fime
   * @param width window width
   * @param height window height
   * @throws IOException
   */
  public void loadNewPage(FXMLLoader loader, String css, int width, int height) throws IOException {
    AnchorPane gp = loader.load();
    Controller controller = loader.getController();
    controller.setup();
    URL cssResource = getClass().getResource(css);
    Scene scene = new Scene(gp, width, height);
    scene.getStylesheets().add(cssResource.toString());
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.show();
    listenStageClose(stage);
  }

  /**
   * Actions after move button is clicked
   * @param ae
   * @throws IOException
   */
  @FXML
  public void showMove(ActionEvent ae) throws IOException {
    wait_msg.setText("");
    //    displayMyTerritory();
    Button source = (Button) ae.getSource();
    // show move page

    FXMLLoader loader =loadLoader("/ui/MoveAction.fxml");
    // setup controller
    controllers.put(ActionController.class, new ActionController(client, "MOVE"));// create a new controller and
                                                                                         // add it
    loader.setControllerFactory((c) -> {
      return controllers.get(c);
    });
    loadNewPage(loader, "/ui/button.css", 629, 450);
  }

  /**
   * Actions after attack button is clicked
   * @param ae
   * @throws IOException
   */
  @FXML
  public void showAttack(ActionEvent ae) throws IOException {
    wait_msg.setText("");
    Button source = (Button) ae.getSource();
    // show move page
    //URL xmlResource = getClass().getResource("/ui/AttackAction.fxml");
    FXMLLoader loader = loadLoader("/ui/MoveAction.fxml");
    controllers.put(ActionController.class, new ActionController(client, "ATTACK"));// create a new controller and
                                                                                         // add it
    loader.setControllerFactory((c) -> {
      return controllers.get(c);
    });
    loadNewPage(loader, "/ui/button.css", 629, 450);
  }

  /**
   * Update cloak icon visibility
   */
  public void updateCloakIconVisiblity(){
    if(client.cloakIsResearch()){
      for(int i=0; i<terr_names.size(); i++){
        if(client.cloakRemain(terr_names.get(i))>0){
          System.out.println("Find cloak in territory: " + terr_names.get(i) + "with turn: " + client.cloakRemain(terr_names.get(i)));
          cloak_icons.get(i).setVisible(true);
          Tooltip.install(cloak_icons.get(i), new Tooltip(""+client.cloakRemain(terr_names.get(i))));
        }else{
          cloak_icons.get(i).setVisible(false);
        }
      }
    }
  }

  /**
   * First find whether it is "my" territory
   */
  public void updateSpyVisibility(){
    for(int i=0; i<terr_names.size(); i++){
      if(client.territoryIsMine(terr_names.get(i))){
        if(client.getMySpyNum(terr_names.get(i))>0){
          spy_icons.get(i).setVisible(true);
          Tooltip.install(spy_icons.get(i), new Tooltip(""+client.getMySpyNum(terr_names.get(i))));
        }else{
          spy_icons.get(i).setVisible(false);
        }
      }else{
        if(client.getEnemySpyNum(terr_names.get(i))>0){
          spy_icons.get(i).setVisible(true);
          Tooltip.install(spy_icons.get(i), new Tooltip(""+client.getEnemySpyNum(terr_names.get(i))));
        }else{
          spy_icons.get(i).setVisible(false);
        }
      }

    }

  }
  public void listenStageClose(Stage stage){

    stage.setOnHidden(event -> {updateFoodAndWood();updateCloakIconVisiblity();updateSpyVisibility();
    });
  }

  /**
   * Actions after upgrade button is clicked
   * @param ae
   * @throws IOException
   */
  @FXML
  public void showUpgrade(ActionEvent ae) throws IOException {
    wait_msg.setText("");
    Button source = (Button) ae.getSource();
    // show move page
    //URL xmlResource = getClass().getResource("/ui/UpgradeAction.fxml");
    FXMLLoader loader = loadLoader("/ui/UpgradeAction.fxml");
    controllers.put(UpgradeActionController.class, new UpgradeActionController(client));
    loader.setControllerFactory((c) -> {
      return controllers.get(c);
    });
    loadNewPage(loader, "/ui/button.css", 681, 428);
  }

  /**
   * Set button disabled
   * @param whether
   */
  /*public void setButtonsDisable(boolean whether){
    showMove.setDisable(whether);
    showAttack.setDisable(whether);
    showUpgrade.setDisable(whether);
    commit_btn.setDisable(whether);

    }*/

  /**
   * Commit game
   * @param ae
   * @throws IOException
   * @throws InterruptedException
   * @throws ExecutionException
   */
  @FXML
  public void commit(ActionEvent ae) throws IOException {

    wait_msg.setText("Waiting for other player");

    System.out.println("commit clicked");
    //disable all button
    //setButtonsDisable(true);
    Button source = (Button) ae.getSource();
    // receive connection
    ArrayList<ActionParser> actions = client.getOrder_list();
    if (actions != null) {
      System.out.println("sending actions:");
      for (ActionParser action : actions) {
        System.out.println(action.getType()+ " " + action.getSource() + " " + action.getDest() + " " + action.getUnit());
      }
    }
    client.oneRoundEnd();
    client.oneRoundUpdate();
    System.out.println("updated!!!");
    wait_msg.setText("Please enter your next actions");
    // display new map

    displayTerritoryBorder();
    updateCloakIconVisiblity();
    updateSpyVisibility();
    String msg = client.getPlayerRoundInfo();
    victory_msg.setText(msg);
    if(client.checkGameOver()==true){
      String end_msg = client.getVictoryInfo();
      //close stage, jump to start page
      URL xmlResource = getClass().getResource("/ui/EndGame.fxml");
      FXMLLoader loader = new FXMLLoader(xmlResource);
      controllers.put(EndGameController.class, new EndGameController());
      loader.setControllerFactory((c) -> {
        return controllers.get(c);
      });

      URL cssResource = getClass().getResource("/ui/button.css");
      AnchorPane gp = loader.load();
      EndGameController cont = loader.getController();
      cont.setup(end_msg);
      Stage primaryStage = (Stage) source.getScene().getWindow();
      client.close_connection();
      primaryStage.close();
      Scene scene = new Scene(gp);
      scene.getStylesheets().add(cssResource.toString());
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.show();
    }
    System.out.println("finish one round game");
  }

  public void jumpToCloakPage() throws IOException {
    FXMLLoader loader = loadLoader("/ui/cloak/ResearchCloak.fxml");
    controllers.put(ResearchCloakController.class, new ResearchCloakController(client));
    loader.setControllerFactory((c) -> {
      return controllers.get(c);
    });
    loadNewPage(loader, "/ui/button.css", 600, 400);
  }

  /**
   * Handle cloak action
   * @param ae
   */
  @FXML
  public void cloakAction(ActionEvent ae) throws IOException {
    //determine whether cloaked before
    boolean ifCloak = client.cloakIsResearch();
    if(ifCloak){//if have cloaked
      //show cloak page
      FXMLLoader loader = loadLoader("/ui/cloak/Cloak.fxml");
      controllers.put(CloakController.class, new CloakController(client));// create a new controller and
      // add it
      loader.setControllerFactory((c) -> {
        return controllers.get(c);
      });
      loadNewPage(loader, "/ui/button.css", 600, 400);

    }else{//if have not cloaked
      jumpToCloakPage();

    }
  }

  @FXML
  public void spyAction() throws IOException {
    FXMLLoader loader = loadLoader("/ui/spy/Spy.fxml");
    controllers.put(SpyController.class, new SpyController(client));
    loader.setControllerFactory((c) -> {
      return controllers.get(c);
    });
    loadNewPage(loader, "/ui/button.css", 600, 400);
  }
}
