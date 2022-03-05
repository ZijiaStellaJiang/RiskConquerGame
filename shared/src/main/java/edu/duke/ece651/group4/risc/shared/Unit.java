package edu.duke.ece651.group4.risc.shared;

public class Unit<T> implements java.io.Serializable{
  T myUnit;

  public Unit(T unit){
    myUnit = unit;
  }

  public T getUnit(){
    return myUnit;
  }
}
