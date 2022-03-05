package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;

public class Map<T> {
  private ArrayList<Player> myPlayers;
  private ArrayList<Territory<T>> myTerritory;
  public Map(){
    myPlayers = new ArrayList<>();
    myTerritory = new ArrayList<>();
  }
  /**
   * add a player to this map, then add all his territories in the map
   */
  public void addPlayer(Player playerToAdd){
    if(!myPlayers.contains(playerToAdd)){
      myPlayers.add(playerToAdd);
    }
  }

  /**
   * add a territory to the current map
   */
  public void addTerritory(Territory<T> toAdd){
    if(!myTerritory.contains(toAdd)){
      myTerritory.add(toAdd);
    }
  }

  /**
   * check if a territory is in the map
   */
  public Boolean checkTerritoryExists(Territory<T> toCheck){
    for (Territory<T> t: myTerritory){
      if(toCheck.equals(t)){
        return true;
      }
    }
    return false;
  }

  public ArrayList<Player> getMyPlayers(){
    return myPlayers;
  }
}