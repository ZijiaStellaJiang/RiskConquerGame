package edu.duke.ece651.group4.risc.shared;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

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
    assertEquals(true, territory3.checkNeigh(territory1));
    territory3.addNeigh(territory1);
    assertEquals(true, territory3.checkNeigh(territory1));
    territory1.removeNeigh(territory3);
    assertEquals(false, territory1.checkNeigh(territory3));
    assertEquals(false,territory3.checkNeigh(territory1));
    territory2.removeNeigh(territory3);
    //territory1.addNeigh(territory3);
    //territory1.removeNeigh(territory2);
  }
 
  @Test
  public void test_equals(){
    Territory<Character> territory1 = new Territory<Character>("test1");
    Territory<Character> territory2 = new Territory<Character>("test2");
    assertEquals(true, territory1.equals(territory1));
    assertEquals(false, territory1.equals("test1"));
    assertEquals(false, territory1.equals(territory2));
  }

  @Test
  public void test_get_my_neigh(){
    Territory<Character> territory1 = new Territory<Character>("A");
    Territory<Character> territory2 = new Territory<Character>("B");
    Territory<Character> territory3 = new Territory<Character>("C");
    territory1.addNeigh(territory2);
    territory1.addNeigh(territory3);
    ArrayList<Territory<Character>> neigh = new ArrayList<>();
    neigh.add(territory2);
    neigh.add(territory3);
    assertEquals(neigh,territory1.getMyNeigh());
  }

  @Test
  public void test_operate_get_unit_number(){
    Territory<Character> t1 = new Territory<>("A");
    assertEquals(0,t1.getUnitNumber());
    Unit<Character> u1 = new SimpleUnit<>();
    assertEquals(0,u1.getLevel());
    t1.addUnit(u1);
    assertEquals(1,t1.getUnitNumber());
    Unit<Character> u2 = new SimpleUnit<>();
    Unit<Character> u3 = new SimpleUnit<>();
    t1.addUnit(u2);
    t1.addUnit(u3);
    assertEquals(3,t1.getUnitNumber());
    t1.removeUnit(u3);
    assertEquals(2,t1.getUnitNumber());
    t1.removeUnit(u1);
    assertEquals(1,t1.getUnitNumber());
    ArrayList<Unit<Character>> units = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
    t1.addGroupUnit(units);
    assertEquals(9,t1.getUnitNumber());
    Unit<Character> u_level = new SimpleUnit<>(3);
    assertEquals(3,u_level.getLevel());
  }

  @Test
  public void test_enemy_unit(){
    Territory<Character> t1 = new Territory<>("t1");
    assertEquals(0,t1.getEnemyUnitNum());
    Unit<Character> e1 = new SimpleUnit<>();
    Unit<Character> e2 = new SimpleUnit<>();
    t1.addEnemyUnit(e1);
    t1.addEnemyUnit(e2);
    assertEquals(2,t1.getEnemyUnitNum());
    t1.removeEnemyUnit(new SimpleUnit<>());
    assertEquals(1,t1.getEnemyUnitNum());
  }

  @Test
  public void test_resource(){
    Territory<Character> t = new Territory<>("a",2);
    assertEquals(0,t.getFoodNum());
    assertEquals(0,t.getWoodNum());
    t.produceResource(new FoodResource<>(3));
    t.produceResource(new WoodResource<>(5));
    assertEquals(3,t.getFoodNum());
    assertEquals(5,t.getWoodNum());
    t.consumeResource(new WoodResource<>(4));
    assertEquals(3,t.getFoodNum());
    assertEquals(1,t.getWoodNum());
  }
}
