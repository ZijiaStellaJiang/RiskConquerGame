package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;

public class Territory<T> {
  private String name;
  private ArrayList<Territory<T>> myNeigh;//used to store neighbourhood information
  public Territory(String name){
    this.name = name;
    myNeigh = new ArrayList<Territory<T>>();
  }
  public String getName(){
    return this.name;
  }

  //temporarily delete, for goal1 just one territory, to make test coverage complete
  public void addNeigh(Territory<T> neighToAdd){
    if(neighToAdd.equals(this)==false){
      myNeigh.add(neighToAdd); 
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


  public boolean equals(Object o){
    if(o.getClass().equals(getClass())){
      Territory<T> terr = (Territory<T>) o;
      return name.equals(terr.getName());
    }
    return false;
  }

}
