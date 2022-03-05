package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;

public class Territory<T> implements java.io.Serializable {
  private String name;
  private ArrayList<Territory<T>> myNeigh;//used to store neighbourhood information
  private Player<T> myPlayer;
  private Unit<Integer> myUnits;
  public Territory(String name){
    this.name = name;
    myNeigh = new ArrayList<Territory<T>>();
  }

  public void setMyUnits(Unit<Integer> units){
    myUnits = units;
  }

  public Integer getMyUnits(){
    return myUnits.getUnit();
  }

  public void changePlayer(Player<T> playerToChange){
    if(myPlayer==null || !playerToChange.equals(myPlayer)){
      myPlayer = playerToChange;
    }
  }

  public String getPlayerName(){
    return myPlayer.getName();
  }

  public String getName(){
    return this.name;
  }

  /**
   * add a territory as this one's neighbour
   * at the same time, add this territory to neighToAdd's neighbour
   */
  public void addNeigh(Territory<T> neighToAdd){
    if(!neighToAdd.equals(this)){
      if(!this.checkNeigh(neighToAdd)){
        myNeigh.add(neighToAdd);
      }
      if(!neighToAdd.checkNeigh(this)){
        neighToAdd.addNeigh(this);
      }
    }
  }

  public void removeNeigh(Territory<T> neighToRemove){
    for(int i=0; i<myNeigh.size();i++){
      if(neighToRemove.equals(myNeigh.get(i))){
        myNeigh.remove(i);
        break;
      }
    }
  }

  public boolean checkNeigh(Territory<T> neighToCheck){
    for(int i=0; i<myNeigh.size();i++){
      if(neighToCheck.equals(myNeigh.get(i))){
        return true;
      }
    }
    return false;
  }

  public ArrayList<Territory<T>> getMyNeigh(){
    return myNeigh;
  }

  public boolean equals(Object o){
    if(o.getClass().equals(getClass())){
      Territory<T> terr = (Territory<T>) o;
      return name.equals(terr.getName());
    }
    return false;
  }

}
