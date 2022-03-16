package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;

public abstract class Player<T> implements java.io.Serializable{
  private String name;
  private ArrayList<Territory<T>> myTerritories;

  public Player(String name) {
    this.name = name;
    myTerritories = new ArrayList<Territory<T>>();
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
    if(myTerritories.size()==0){
      return true;
    }
    return false;
  }
}
