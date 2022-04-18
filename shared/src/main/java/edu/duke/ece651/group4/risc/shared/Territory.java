package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class Territory<T> implements java.io.Serializable {
  private final String name;
  private ArrayList<Territory<T>> myNeigh;
  private HashMap<Integer,ArrayList<Unit<T>>> myUnits;
  private HashMap<Integer,ArrayList<Unit<T>>> enemyUnits;
  private int size;
  private int foodAbility;  //indicate how much resource this territory can produce in each turn
  private int woodAbility;
  private int distance;  //indicate the distance from a source, used to find minimum cost
  private TerritoryDisplayInfo<T> infoForMyself;
  private TerritoryDisplayInfo<T> infoForEnemy;
  /**
   * if canBeSeen is true, show the latest info to enemy
   * if canBeSeen is false, if seen is true, show old information
   *                        if seen is false, show null (just outline)
   * should update visibility at the start of each round
   */
  private boolean seen;            //indicate whether this territory has seen by enemy
  private boolean canBeSeen;       //indicate whether this territory can be seen by enemy at present

  public Territory(String name, int size, int foodAbility, int woodAbility){
    this.name = name;
    this.myNeigh = new ArrayList<>();
    this.myUnits = new HashMap<>();
    this.enemyUnits = new HashMap<>();
    this.size = size;
    this.foodAbility = foodAbility;
    this.woodAbility = woodAbility;
    this.distance = 100;
    this.infoForMyself = new TerritoryDisplayInfo<>();
    this.infoForEnemy = new TerritoryDisplayInfo<>();
    this.seen = false;
    this.canBeSeen = false;
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

  public int getSize() {
    return size;
  }

  public int getDistance() {
    return distance;
  }

  public void setDistance(int newDis) {
    this.distance = newDis;
  }

  public int getMyMaxUnit(){
    if(getUnitNumber()==0) {
      throw new IllegalArgumentException("can not access this function here, no units");
    }
    return getMaxHelper(myUnits);
  }

  public int getMyMinUnit(){
    if(getUnitNumber()==0) {
      throw new IllegalArgumentException("can not access this function here, no units");
    }
    return getMinHelper(myUnits);
  }

  public int getEnemyMaxUnit(){
    if(getEnemyUnitNum()==0) {
      throw new IllegalArgumentException("can not access this function here, no units");
    }
    return getMaxHelper(enemyUnits);
  }

  public int getEnemyMinUnit(){
    if(getEnemyUnitNum()==0) {
      throw new IllegalArgumentException("can not access this function here, no units");
    }
    return getMinHelper(enemyUnits);
  }

  /**
   * this function update info of player's own or for enemy's view
   * should update myself after each round (own territory always visible)
   * should update enemy's view as long as enemy has right to see
   *     (if enemy lose the right to see the territory, do not update, keep the old version to display)
   *     (but if enemy hasn't gain right to see, also update, but don't show to enemy)
   * @param isForMyself: determine whose display to update
   */
  public void updateInfo (boolean isForMyself) {
    for (int i=0; i<7; i++){
      if(isForMyself) infoForMyself.updateLevelInfo(i, getLevelUnitNum(i));
      else infoForEnemy.updateLevelInfo(i, getLevelUnitNum(i));
    }
  }

  public void updateOneRoundInfo () {
    updateInfo(true);
    if (canBeSeen || !seen) updateInfo(false);
  }

  public ArrayList<Integer> getMyInfo(){
    return infoForMyself.getUnitNumList();
  }

  public ArrayList<Integer> getEnemyInfo() {
    if (!seen && !canBeSeen) return null;
    return infoForEnemy.getUnitNumList();
  }

  public void setSeen(boolean toSet) {
    this.seen = toSet;
  }

  public void setCanBeSeen(boolean toSet) {
    this.canBeSeen = toSet;
  }

  public void setLatest(boolean toSet) {
    infoForEnemy.setIsLatest(toSet);
  }

  public boolean checkSeen() {
    return seen;
  }

  /**
   * check getEnemyInfo() not null first !!!
   * this function should not be used when a territory has not been seen yet
   */
  public boolean checkLatest() {
    if(!seen && !canBeSeen) {
      throw new IllegalArgumentException("can not access here, infoForEnemy should return null");
    }
    return infoForEnemy.checkLatest();
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

  private int getMaxHelper(HashMap<Integer, ArrayList<Unit<T>>> units){
    int max_level = new SimpleUnit<T>().getMaxLevel();
    for (int i=max_level; i>0; i--){
      if(!units.containsKey(i)) continue;
      else if(units.get(i).size()>0) return i;
    }
    return 0;
  }

  private int getMinHelper(HashMap<Integer, ArrayList<Unit<T>>> units){
    int max_level = new SimpleUnit<T>().getMaxLevel();
    for (int i=0; i<max_level; i++){
      if(!units.containsKey(i)) continue;
      else if(units.get(i).size()>0) return i;
    }
    return max_level;
  }
}
