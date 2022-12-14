package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class TerritoryTest {
  @Test
  public void test_territory_constructor() {
    Territory<Character> territory = new Territory<Character>("test");
    assertEquals("test", territory.getName());
    assertEquals(0,territory.getSize());
    assertEquals(100,territory.getDistance());
    territory.setDistance(10);
    assertEquals(10,territory.getDistance());
    Territory<Character> t2 = new Territory<>("t2",4,3,3);
    assertEquals(4,t2.getSize());
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
  }
  @Test
  public void test_equals(){
    Territory<Character> territory1 = new Territory<Character>("test1");
    Territory<Character> terr1 = new Territory<>("test1");
    Territory<Character> territory2 = new Territory<Character>("test2");
    assertEquals(true, territory1.equals(territory1));
    assertEquals(true,terr1.equals(territory1));
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
    t1.addMyUnit(u1);
    assertEquals(1,t1.getUnitNumber());
    Unit<Character> u2 = new SimpleUnit<>();
    Unit<Character> u3 = new SimpleUnit<>();
    t1.addMyUnit(u2);
    t1.addMyUnit(u3);
    assertEquals(3,t1.getUnitNumber());
    t1.removeMyUnit(u3);
    assertEquals(2,t1.getUnitNumber());
    t1.removeMyUnit(u1);
    assertEquals(1,t1.getUnitNumber());
    ArrayList<Unit<Character>> units = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
    t1.addGroupUnit(units);
    assertEquals(9,t1.getUnitNumber());
    Unit<Character> u_level = new SimpleUnit<>(3);
    assertEquals(3,u_level.getLevel());
    assertEquals(0,t1.getLevelUnitNum(3));
    t1.addMyUnit(u_level);
    assertEquals(1,t1.getLevelUnitNum(3));
    assertEquals(10,t1.getUnitNumber());
    assertThrows(IllegalArgumentException.class,()->t1.removeMyUnit(new SimpleUnit<>(2)));
  }
  @Test
  public void test_enemy_unit(){
    Territory<Character> t1 = new Territory<>("t1");
    assertEquals(0,t1.getEnemyUnitNum());
    assertEquals(0,t1.getLevelEnemyUnitNum(1));
    Unit<Character> e1 = new SimpleUnit<>();
    Unit<Character> e2 = new SimpleUnit<>();
    t1.addEnemyUnit(e1);
    t1.addEnemyUnit(e2);
    assertEquals(2,t1.getEnemyUnitNum());
    t1.removeEnemyUnit(new SimpleUnit<>());
    assertEquals(1,t1.getEnemyUnitNum());
  }
  @Test
  public void test_max_min_level(){
    Territory<Character> t = new Territory<>("A");
    t.addMyUnit(new SimpleUnit<>(2));
    t.addMyUnit(new SimpleUnit<>());
    t.addMyUnit(new SimpleUnit<>(5));
    assertEquals(5,t.getMyMaxUnit());
    assertEquals(0,t.getMyMinUnit());
    assertThrows(IllegalArgumentException.class, ()->t.getEnemyMaxUnit());
    assertThrows(IllegalArgumentException.class, ()->t.getEnemyMinUnit());
    t.removeMyUnit(new SimpleUnit<>(5));
    assertEquals(2,t.getMyMaxUnit());
    t.addEnemyUnit(new SimpleUnit<>(1));
    t.addEnemyUnit(new SimpleUnit<>(4));
    t.addEnemyUnit(new SimpleUnit<>(2));
    t.addEnemyUnit(new SimpleUnit<>(4));
    assertEquals(4,t.getEnemyMaxUnit());
    assertEquals(1,t.getEnemyMinUnit());
    Territory<Character> test = new Territory<>("test");
    assertThrows(IllegalArgumentException.class, ()->test.getMyMaxUnit());
    assertThrows(IllegalArgumentException.class, ()->test.getMyMinUnit());
  }

  @Test
  public void test_update_display_info() {
    Territory<Character> t1 = new Territory<>("t1");
    t1.setCanBeSeen(true);
    t1.setSeen(true);
    ArrayList<Unit<Character>> units = new ArrayList<>(Collections.nCopies(3,new SimpleUnit<>()));
    units.addAll(Collections.nCopies(5,new SimpleUnit<>(2)));
    units.addAll(Collections.nCopies(4,new SimpleUnit<>(6)));
    t1.addGroupUnit(units);
    assertEquals(0,t1.getMyInfo().get(2));
    t1.updateInfo(true);
    assertEquals(5,t1.getMyInfo().get(2));
    assertEquals(4,t1.getMyInfo().get(6));
    t1.updateInfo(false);
    t1.removeMyUnit(new SimpleUnit<>());
    assertEquals(3,t1.getEnemyInfo().get(0));
    t1.updateInfo(false);
    assertEquals(2,t1.getEnemyInfo().get(0));
  }

  @Test
  public void test_visibility() {
    Territory<Character> t1 = new Territory<>("t1");
    assertFalse(t1.checkSeen());
    t1.setSeen(true);
    assertTrue(t1.checkSeen());
  }

  @Test
  public void test_add_remove_spy() {
    Territory<Character> t = new Territory<>("t1");
    assertEquals(0,t.getSpyNumber());
    assertEquals(0,t.getEnemySpyNumber());
    t.addMySpy();
    t.addMySpy();
    t.addEnemySpy();
    assertEquals(2,t.getSpyNumber());
    assertEquals(1,t.getEnemySpyNumber());
    assertTrue(t.hasEnemySpy());
    t.removeMySpy();
    t.removeEnemySpy();
    assertEquals(1,t.getSpyNumber());
    assertFalse(t.hasEnemySpy());
  }
}
