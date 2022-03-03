package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TerritoryTest {
  @Test
  public void test_territory_constructor() {
    Territory<Character> territory = new Territory<Character>("test");
    assertEquals("test", territory.getName());
  }

  @Test
  public void test_neigh_check() {
    Territory<Character> territory1 = new Territory<Character>("test1");
    Territory<Character> territory2 = new Territory<Character>("test2");
    Territory<Character> territory3 = new Territory<Character>("test3");

    territory1.addNeigh(territory3);
    territory1.addNeigh(territory1);
    assertEquals(true, territory1.checkNeigh(territory3));
    assertEquals(false, territory1.checkNeigh(territory2));
    assertEquals(false, territory1.checkNeigh(territory1));
    territory1.removeNeigh(territory3);
    assertEquals(false, territory1.checkNeigh(territory3));
    territory2.removeNeigh(territory3);
    territory1.addNeigh(territory3);
    territory1.removeNeigh(territory2);

   
  }

  @Test
  public void test_equals(){
    Territory<Character> territory1 = new Territory<Character>("test1");
    Territory<Character> territory2 = new Territory<Character>("test2");
    assertEquals(true, territory1.equals(territory1));
    assertEquals(false, territory1.equals("test1"));
    assertEquals(false, territory1.equals(territory2));
  }

}
