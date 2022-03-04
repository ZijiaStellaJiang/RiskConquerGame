package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;

public class Map<T> {
  private ArrayList<Territory<T>> myTerritory;
  public Map(){
    myTerritory = new ArrayList<>();
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

  public ArrayList<Territory<T>> getMyTerritory(){
    return myTerritory;
  }

  /**
   * get the name of a certain territory in the map
   */
//  public String getMyTerritoryName(Territory<T> territory) {
//    if(!checkTerritoryExists(territory)){
//      throw new IllegalArgumentException("this territory doesn't exist in this map.");
//    }
//    return territory.getName();
//  }
}