package edu.duke.ece651.group4.risc.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class UpdateActionTest {
  @Test
  public void test_update() {
    Territory<Character> t_test = new Territory<>("test",2,2,2);
    Territory<Character> t1 = new Territory<>("t1", 100, 1000, 1000);
    ArrayList<Unit<Character>> Units = new ArrayList<>(Collections.nCopies(5, new SimpleUnit<>()));
    t1.addGroupUnit(Units);
    Map<Character> map = new Map<Character>();
    map.addTerritory(t_test);
    map.addTerritory(t1);
    TextPlayer p1 = new TextPlayer("Green");
    p1.addToTerritory(t_test);
    p1.addToTerritory(t1);
    map.addPlayer(p1);
    String result;
    ActionParser parse = null;
    Action<Character> update;
    // check resource
    parse = new ActionParser("update", "t1", null, 5, 0, 1);
    update = new UpdateAction<>();
    result = update.doAction(parse, map , p1);
    assertEquals("That action is invalid: The technology resources are not enough for this updating action.", result);
    p1.updateResource();
    // check number
    parse = new ActionParser("update", "t1", null, 6, 0, 1);
    update = new UpdateAction<>();
    result = update.doAction(parse, map , p1);
    assertEquals("That action is invalid: the number of certain level's unit is not enough.", result);
    // valid 
    parse = new ActionParser("update", "t1", null, 5, 0, 5);
    update = new UpdateAction<>();
    result = update.doAction(parse, map , p1);
    assertEquals(0, t1.getLevelUnitNum(0));
    assertEquals(0, t1.getLevelUnitNum(1));
    assertEquals(0, t1.getLevelUnitNum(2));
    assertEquals(0, t1.getLevelUnitNum(3));
    assertEquals(0, t1.getLevelUnitNum(4));
    assertEquals(5, t1.getLevelUnitNum(5));
    assertEquals(0, t1.getLevelUnitNum(6));
    // check max level
    parse = new ActionParser("update", "t1", null, 5, 5, 2);
    update = new UpdateAction<>();
    result = update.doAction(parse, map , p1);
    assertEquals("That action is invalid: the unit already achieved max level.", result);
    
  }

}
