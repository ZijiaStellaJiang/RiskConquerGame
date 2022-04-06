package edu.duke.ece651.group4.risc.controller;

import java.util.ArrayList;
import java.util.Collections;

import edu.duke.ece651.group4.risc.client.Client;
import edu.duke.ece651.group4.risc.shared.ActionParser;
import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.Player;
import edu.duke.ece651.group4.risc.shared.SimpleUnit;
import edu.duke.ece651.group4.risc.shared.Territory;
import edu.duke.ece651.group4.risc.shared.TextPlayer;
import edu.duke.ece651.group4.risc.shared.Unit;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UpgradeActionController {
  @FXML
  ChoiceBox<String> source;

  @FXML
  ChoiceBox<Integer> from;

  @FXML
  TextField levelup;

  @FXML
  TextField num;

  @FXML
  Button done;
  @FXML
  Text alert;
  @FXML
  AnchorPane pane;
  @FXML
  Text cost;
  private Client client;
  private ObservableList<String> sources;
  private Map<Character> testMap;
  public UpgradeActionController(Client client) {
    this.client = client;
  }

  /**
   * Get territory of user
   * @return
   */
  public ArrayList<Territory<Character>> displayMyTerritory() {
    Map<Character> myMap = client.getMap();
    ArrayList<Player<Character>> players = myMap.getMyPlayers();
    return players.get(client.getPlayerId()).getMyTerritories();
  }

  /**
   * Check whether use input integer
   * @return
   */
  public boolean checkIntegerValid() {
    String level_up_str = levelup.getText();
    String unit_num_str = num.getText();
    if (level_up_str.matches("[0-9]+") && unit_num_str.matches("[0-9]+")) {
      return true;
    }
    //String unit_num_str = num.getText();
    /*if (unit_num_str.matches("[0-9]+")) {
      return true;
      }*/
    return false;
  }

  /**
   * Show cost of user input
   */
  public void showCost() {
    alert.setText("");
    if ((source.getValue() != null) && (from.getValue() != null) && (levelup.getText() != null)
        && (num.getText() != null)) {
      if (checkIntegerValid() == false) {
        cost.setText("Level up or num needs to be integer");
        alert.setText("");
        return;
      }
      try {
        String source_terr = source.getValue();
        Integer upgrade_from = from.getValue();
        Integer level_up = Integer.parseInt(levelup.getText());
        Integer unit_num = Integer.parseInt(num.getText());
        if (level_up > 6) {
          alert.setText("Maximum level is 6");
          return;
        }
        ActionParser parser = new ActionParser("UPDATE", source_terr, null, unit_num, upgrade_from, level_up);
        String cost_val = parser.getCost(client.getMap());
        cost.setText(cost_val);
        return;
      } catch (IllegalArgumentException e) {
      }
    }
    cost.setText("Unavailable");
  }

  /**
   * set up source destination
   */
  public void setup() {
    sources = FXCollections.observableArrayList();
    for (Territory<Character> terr : displayMyTerritory()) {
      sources.add(terr.getName());
    }
    source.setItems(sources);
    ObservableList<Integer> level = FXCollections.observableArrayList();
    level.addAll(0, 1, 2, 3, 4, 5, 6);
    from.setItems(level);
    num.textProperty().addListener((observable, oldValue, newValue) -> {
      showCost();
    });
    levelup.textProperty().addListener((observable, oldValue, newValue) -> {
      showCost();
    });
    source.getSelectionModel().selectedIndexProperty()
        .addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
          showCost();
        });
    from.getSelectionModel().selectedIndexProperty()
        .addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
          showCost();
        });
    cost.setText("Unavailable");

  }

  /**
   * Actions after click done
   */
  @FXML
  public void done() {
    if (!((source.getValue() != null) && (from.getValue() != null) && (levelup.getText() != null)
        && (num.getText() != null))) {
      alert.setText("Please enter all blanks");
      return;
    }
    if (checkIntegerValid() == false) {
      alert.setText("Please enter integer");
      return;
    }

    String source_terr = source.getValue();
    Integer upgrade_from = from.getValue();
    Integer level_up = Integer.parseInt(levelup.getText());
    Integer unit_num = Integer.parseInt(num.getText());
    System.out.println(source_terr + " " + upgrade_from + " " + level_up + " " + unit_num);
    try {
      ActionParser newAction = new ActionParser("UPDATE", source_terr, null, unit_num, upgrade_from, level_up);
      String result = client.addOrder(newAction);
      if (result != null) {
        alert.setText(result);
      } else {
        Stage primaryStage = (Stage) source.getScene().getWindow();
        primaryStage.close();
      }
    } catch (IllegalArgumentException e) {
      alert.setText("Invalid input");
      return;// TODO: can be delete
    }
  }

}
