package edu.duke.ece651.group4.risc.shared;

public class Spy<T> implements java.io.Serializable {
    @Override
    public boolean equals(Object o){
      if(o.getClass().equals(getClass())){
          return true;
      }
      return false;
    }
}
