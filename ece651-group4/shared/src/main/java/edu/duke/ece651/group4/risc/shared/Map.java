package edu.duke.ece651.group4.risc.shared;

public class Map<T> {
  private Territory myTerritory;
  public Map(Territory territory){
    myTerritory = territory;
  }


  public Boolean checkTerritoryExists(Territory territory){
    if(territory.getName()==myTerritory.getName()){
      return true;
    }
    return false;
  }

}
