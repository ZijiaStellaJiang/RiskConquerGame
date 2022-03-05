package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;

public abstract class Player<T> {
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
    for (int i = 0; i < myTerritories.size(); i++) {
      if (territory.equals(myTerritories.get(i))) {
        myTerritories.remove(i);
        break;
      }
    }
  }

  public boolean checkMyTerritory(Territory<T> territory) {
    for (int i = 0; i < myTerritories.size(); i++) {
      if (territory.equals(myTerritories.get(i))) {
        return true;
      }
    }
    return false;
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
}
