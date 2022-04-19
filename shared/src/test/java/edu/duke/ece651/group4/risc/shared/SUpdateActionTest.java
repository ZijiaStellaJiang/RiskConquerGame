package edu.duke.ece651.group4.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class SUpdateActionTest {
  @Test
  public void test_() {
    Territory<Character> t_test = new Territory<>("test",2,2,2);
    Territory<Character> t1 = new Territory<>("t1", 100, 1000, 1000);
    ArrayList<Unit<Character>> Units = new ArrayList<>(Collections.nCopies(5, new SimpleUnit<>()));
    t1.addGroupUnit(Units);
    ArrayList<Unit<Character>> check_units = new ArrayList<>(Collections.nCopies(4,new SimpleUnit<>(2)));
    t1.addGroupUnit(check_units);
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
    parse = new ActionParser("supdate", "t1", null, 5, 0, 1);
    update = new SUpdateAction<>();
    result = update.doAction(parse, map , p1);
    assertEquals("That action is invalid: The technology resources are not enough for updating spy.", result);
    p1.updateResource();
    // check number
    parse = new ActionParser("supdate", "t1", null, 6, 0, 1);
    update = new SUpdateAction<>();
    result = update.doAction(parse, map , p1);
    assertEquals("That action is invalid: not enough units for upgrading to spies", result);
    // valid 
    parse = new ActionParser("supdate", "t1", null, 5, 0, 5);
    update = new SUpdateAction<>();
    result = update.doAction(parse, map , p1);
    assertEquals(0, t1.getLevelUnitNum(0));
    assertEquals(5, t1.getSpyNumber());
  }

}
