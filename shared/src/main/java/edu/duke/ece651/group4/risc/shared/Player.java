package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Player<T> implements java.io.Serializable{
  private String name;
  private ArrayList<Territory<T>> myTerritories;
  //this field stores the combat result of last round. If wins a territory, map true; if loses, map false
  private HashMap<String,Boolean> lastRoundChange;
  private ArrayList<Resource<T>> myResource;
  private boolean researchCloak;
  private boolean moveSpyInEnemy;
  

  public Player(String name, int foodInit, int woodInit) {
    this.name = name;
    myTerritories = new ArrayList<>();
    lastRoundChange = new HashMap<>();
    myResource = new ArrayList<>();
    myResource.add(new FoodResource<>(foodInit));
    myResource.add(new WoodResource<>(woodInit));
    this.researchCloak = false;
    this.moveSpyInEnemy = false;
  }

  public String getName() {
    return this.name;
  }

  public boolean cloakIsResearch() {
    return researchCloak;
  }

  public void researchClock() {
    researchCloak = true;
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

  /**
   * Given a territory name, find move or attack destination
   * @param toFind
   * @param findMyself
   * @return
   */
  public ArrayList<Territory<T>> findDestinations(Territory<T> toFind,boolean findMyself){
    if(toFind==null){
      return null;
    }
    ArrayList<Territory<T>> canPerform = new ArrayList<>();
    //find territories a player can move to
    if(findMyself){
      for (Territory<T> t: myTerritories){
        if(t.getName().equals(toFind.getName())) continue;
        canPerform.add(t);
      }
    }
    //find territories a player can attack
    else {
      for (Territory<T> t: toFind.getMyNeigh()){
        if(myTerritories.contains(t)) continue;
        canPerform.add(t);
      }
    }
    return canPerform;
  }

  /**
   * check whether a territory that belongs to this player can be seen by enemy
   * for now just deal with adjacency
   * @return true if this territory can be seen by enemy at present, otherwise false.
   */
  public boolean checkTerritoryVisibility(Territory<T> toCheck) {
    if (!checkMyTerritory(toCheck)) {
      throw new IllegalArgumentException("The territory to check doesn't belong to the player");
    }
    boolean finalVisibility = false;
    // 1st condition: find a neighbour that belongs to enemy, then enemy can see
    for (Territory<T> neigh: toCheck.getMyNeigh()) {
      if (!checkMyTerritory(neigh)) {
        finalVisibility = true;
        break;
      }
    }
    // 2nd condition: if the territory is cloaked
    if(finalVisibility && toCheck.cloakgetCount()!=0) finalVisibility = false;
    // 3rd condition: if there's am enemy spy, then enemy can see
    if (toCheck.hasEnemySpy()) finalVisibility = true;
    return finalVisibility;
  }

  public void handleVisibility() {
    for (Territory<T> toHandle: myTerritories) {
      boolean visible = checkTerritoryVisibility(toHandle);
      toHandle.setCanBeSeen(visible);
      toHandle.setLatest(visible);
      if (!toHandle.checkSeen() && visible) toHandle.setSeen(true);
    }
  }

  public void updatePlayerTerritoriesInfo() {
    for (Territory<T> t: myTerritories) {
      t.updateOneRoundInfo();
    }
  }

  public void setMoveSpyInEnemy(boolean toSet) {
    this.moveSpyInEnemy = toSet;
  }

  public boolean getMoveSpyInEnemy() {
    return this.moveSpyInEnemy;
  }
}
