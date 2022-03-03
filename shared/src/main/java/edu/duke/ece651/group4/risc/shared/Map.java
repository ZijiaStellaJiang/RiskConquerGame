package edu.duke.ece651.group4.risc.shared;

public class Map<T> {
  private Territory<T> myTerritory;
  public Map(Territory<T> territory){
    myTerritory = territory;
  }
  public Boolean checkTerritoryExists(Territory<T> territory){
    return territory.getName().equals(myTerritory.getName());
  }
  public String getMyTerritoriesName() {
    return myTerritory.getName();
  }
}