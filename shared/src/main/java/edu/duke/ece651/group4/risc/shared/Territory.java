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

  /*temporarily delete, for goal1 just one territory, to make test coverage complete
  public void addNeigh(Territory<T> neighToAdd){
    myNeigh.add(neighToAdd);
  }
  */

  /*  public void removeNeigh(Territory<T> neighToRemove){
    for(int i=0; i<myNeigh.size():i++){
      
    }
    }*/

}
