package edu.duke.ece651.group4.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UnitTest {
  @Test
  public void test_construct() {
    Unit<Integer> unit1 = new Unit<Integer>(1);
    assertEquals(1, unit1.getUnit());
    Unit<String> unit2 = new Unit<String>("123");  
    assertEquals("123", unit2.getUnit());
    Unit<Character> unit3 = new Unit<Character>('a');
     assertEquals('a', unit3.getUnit());  
  }

}
