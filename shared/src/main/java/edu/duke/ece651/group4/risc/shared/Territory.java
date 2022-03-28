package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class Territory<T> implements java.io.Serializable {
  private final String name;
  private ArrayList<Territory<T>> myNeigh;
  private HashMap<Integer,ArrayList<Unit<T>>> myUnits;
  //private ArrayList<Unit<T>> enemyUnits;
  private HashMap<Integer,ArrayList<Unit<T>>> enemyUnits;
  private int size;
  private int foodAbility;  //indicate how much resource this territory can produce in each turn
  private int woodAbility;

  public Territory(String name, int size, int foodAbility, int woodAbility){
    this.name = name;
    this.myNeigh = new ArrayList<>();
    this.myUnits = new HashMap<>();
    //this.enemyUnits = new ArrayList<>();
    this.enemyUnits = new HashMap<>();
    this.size = size;
    this.foodAbility = foodAbility;
    this.woodAbility = woodAbility;
  }
  /**
   * constructor holder for evol1, no use
   */
  public Territory(String name){
    this(name,0,0,0);
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

  /**
   * remove a territory from its neighbour
   * at the same time, remove this territory from neighToRemove's neighbour
   */
  public void removeNeigh(Territory<T> neighToRemove){
    if(myNeigh.remove(neighToRemove)){
      neighToRemove.removeNeigh(this);
    }
  }

  public boolean checkNeigh(Territory<T> neighToCheck){
    return myNeigh.contains(neighToCheck);
  }

  public ArrayList<Territory<T>> getMyNeigh(){
    return myNeigh;
  }

  /**
   * return the number of units of a certain level in this territory
   */
  public Integer getLevelUnitNum(int level){
    if(myUnits.containsKey(level)) return myUnits.get(level).size();
    return 0;
  }

  /**
   * return the sum of all level units exist in this territory
   */
  public Integer getUnitNumber() {
    int sum = 0;
    for(int level: myUnits.keySet()){
      sum += getLevelUnitNum(level);
    }
    return sum;
  }

  public Integer getLevelEnemyUnitNum(int level){
    if(enemyUnits.containsKey(level)) return enemyUnits.get(level).size();
    return 0;
  }

  public Integer getEnemyUnitNum(){
    int sum = 0;
    for(int level: enemyUnits.keySet()){
      sum += getLevelEnemyUnitNum(level);
    }
    return sum;
  }

  public void addMyUnit(Unit<T> unitToAdd){
    addUnitHelper(unitToAdd,myUnits);
  }

  /** store attacker's unit */
  public void addEnemyUnit(Unit<T> enemyUnitToAdd){
    addUnitHelper(enemyUnitToAdd, enemyUnits);
  }

  public void removeMyUnit(Unit<T> unitToRemove){
    removeUnitHelper(unitToRemove,myUnits);
  }

  public void removeEnemyUnit(Unit<T> unitToRemove){
    removeUnitHelper(unitToRemove,enemyUnits);
  }

  public void addGroupUnit(ArrayList<Unit<T>> toAdd){
    for (Unit<T> u: toAdd){
      this.addMyUnit(u);
    }
  }

  public int getFoodAbility(){
    return foodAbility;
  }

  public int getWoodAbility(){
    return woodAbility;
  }
  @Override
  public boolean equals(Object o){
    if(o.getClass().equals(getClass())){
      Territory<T> terr = (Territory<T>) o;
      return name.equals(terr.getName());
    }
    return false;
  }

  private void addUnitHelper(Unit<T> UnitToAdd, HashMap<Integer, ArrayList<Unit<T>>> units) {
    int level = UnitToAdd.getLevel();
    if(units.containsKey(level)){
      units.get(level).add(UnitToAdd);
    }
    else{
      ArrayList<Unit<T>> u = new ArrayList<>();
      u.add(UnitToAdd);
      units.put(level,u);
    }
  }

  private void removeUnitHelper(Unit<T> toRemove, HashMap<Integer,ArrayList<Unit<T>>> units){
    int level = toRemove.getLevel();
    if(units.containsKey(level) && units.get(level).size()!=0){
      units.get(level).remove(0);
    }
    else {
      throw new IllegalArgumentException("You don't have this level of unit to remove");
    }
  }
}
