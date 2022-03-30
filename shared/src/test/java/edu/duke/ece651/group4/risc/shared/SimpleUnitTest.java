package edu.duke.ece651.group4.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleUnitTest {
  @Test
  public void test_getLevel() {
    Unit<Character> unit = new SimpleUnit<Character>(5);
    assertEquals(unit.getLevel(), 5);
  }
  
  @Test
  public void test_getBonus() {
    Unit<Character> unit = new SimpleUnit<Character>(5);
    assertEquals(unit.getBonus(), 11);    
  }
  
  @Test
  public void test_updateCost() {
    Unit<Character> unit = new SimpleUnit<Character>(5);
    assertEquals(unit.updateCost(), 50);    
  }
  
  @Test
  public void test_getcanUpdate() {
    Unit<Character> unit = new SimpleUnit<Character>(5);
    assertEquals(true, unit.update());
    assertEquals(false, unit.update());        
  }
  
}
