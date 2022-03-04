package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;

public class Player {
  private String name;
  private ArrayList<Territory<Character>> myTerritories;

  public Player(String name) {
    this.name = name;
    myTerritories = new ArrayList<Territory<Character>>();
  }

  String getName() {
    return this.name;
  }

  public void addToTerritory(Territory<Character> territory) {
    myTerritories.add(territory);
  }

  public void removeFromTerriroty(Territory<Character> territory) {
    for (int i = 0; i < myTerritories.size(); i++) {
      if (territory.equals(myTerritories.get(i))) {
        myTerritories.remove(i);
        break;
      }
    }
  }

  public boolean checkMyTerritory(Territory<Character> territory) {
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
}
