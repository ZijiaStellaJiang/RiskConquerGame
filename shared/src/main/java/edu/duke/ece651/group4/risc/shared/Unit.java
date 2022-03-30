package edu.duke.ece651.group4.risc.shared;

public abstract class Unit<T> implements java.io.Serializable{
  protected int level;
  protected int bonus;

  public Unit(int level, int bonus){
    this.level = level;
    this.bonus = bonus;
  }

  public int getLevel(){
    return level;
  }

  public int getBonus() {
    return bonus;
  }

  public abstract int getMaxLevel();
  
  public abstract int updateCost();

  public abstract boolean canUpdate();

  public abstract boolean update();

  @Override
  public boolean equals(Object o){
    if(o.getClass().equals(getClass())){
      Unit<T> unit = (Unit<T>) o;
      return level == unit.getLevel() && bonus == unit.getBonus();
    }
    return false;
  }
}