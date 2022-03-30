package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Player<T> implements java.io.Serializable{
  private String name;
  private ArrayList<Territory<T>> myTerritories;
  //this field stores the combat result of last round. If wins a territory, map true; if loses, map false
  private HashMap<String,Boolean> lastRoundChange;
  //private Resource<T> food;
  //private Resource<T> wood;
  private ArrayList<Resource<T>> myResource;

  public Player(String name, int foodInit, int woodInit) {
    this.name = name;
    myTerritories = new ArrayList<Territory<T>>();
    lastRoundChange = new HashMap<>();
    //food = new FoodResource<>(0);
    //wood = new WoodResource<>(0);
    myResource = new ArrayList<>();
    myResource.add(new FoodResource<>(foodInit));
    myResource.add(new WoodResource<>(woodInit));
  }

  public String getName() {
    return this.name;
  }

  public void addToTerritory(Territory<T> territory) {
    myTerritories.add(territory);
  }

  public void removeFromTerritory(Territory<T> territory) {
    myTerritories.remove(territory);
  }

  public boolean checkMyTerritory(Territory<T> territory) {
    return myTerritories.contains(territory);
  }

  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Player player = (Player) o;
      return name.equals(player.getName());
    }
    return false;
  }

  public ArrayList<Territory<T>> getMyTerritories(){
    return myTerritories;
  }

  public boolean checkLose(){
    return myTerritories.size() == 0;
  }

  public void addLoseTerritory(String loseTerritoryName){
    lastRoundChange.put(loseTerritoryName,false);
  }

  public void addWinTerritory(String winTerritoryName){
    lastRoundChange.put(winTerritoryName,true);
  }

  /**
   * @return the all the names of lose territories in one round of this player
   */
  public ArrayList<String> getLoseTerritories(){
    ArrayList<String> loseTerritories = new ArrayList<>();
    for (String s: lastRoundChange.keySet()){
      if(!lastRoundChange.get(s)){
        loseTerritories.add(s);
      }
    }
    return loseTerritories;
  }

  /**
   * @return the all the names of win territories in one round of this player
   */
  public ArrayList<String> getWinTerritories(){
    ArrayList<String> winTerritories = new ArrayList<>();
    for (String s: lastRoundChange.keySet()){
      if(lastRoundChange.get(s)){
        winTerritories.add(s);
      }
    }
    return winTerritories;
  }

  /**
   * resetLastRound the field lastRoundChange to an empty map, to store next round's information
   */
  public void resetLastRoundChange(){
    lastRoundChange = new HashMap<>();
  }

  public void consumeResource(Resource<T> consume){
    for (Resource<T> r: myResource){
      if(r.equals(consume)){
        r.consumeResource(consume.getNum());
        break;
      }
    }
  }

  /**
   * this function should be called at the end of each turn
   * to update the resource production in this turn
   */
  public void updateResource(){
    for (Territory<T> t: myTerritories){
      myResource.get(0).addResource(t.getFoodAbility());
      myResource.get(1).addResource(t.getWoodAbility());
    }
  }

  public int getFoodNum(){
    return myResource.get(0).getNum();
  }

  public int getWoodNum(){
    return myResource.get(1).getNum();
  }
}
