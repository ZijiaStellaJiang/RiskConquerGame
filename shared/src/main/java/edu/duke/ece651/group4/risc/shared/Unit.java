package edu.duke.ece651.group4.risc.shared;

public abstract class Unit<T> implements java.io.Serializable{
  private int level;

  public Unit(int level){
    this.level = level;
  }

  public int getLevel(){
    return level;
  }

  @Override
  public boolean equals(Object o){
    if(o.getClass().equals(getClass())){
      Unit<T> unit = (Unit<T>) o;
      return level == unit.getLevel();
    }
    return false;
  }
}