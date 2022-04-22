package edu.duke.ece651.group4.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

public class ActionParserTest {
  @Test
  public void test_normal_constructor() {
    ActionParser parser = new ActionParser("move", "a", "test", 3);
    assertEquals("MOVE", parser.getType());
    assertEquals("A", parser.getSource());
    assertEquals("TEST", parser.getDest());
    assertEquals(3, parser.getUnit());

    //test with invalid case
    assertThrows(IllegalArgumentException.class, () -> new ActionParser("invalid", "a", "123", 3));
  }


  @Test
  public void test_string_constructor(){
    ActionParser parser = new ActionParser("move a test 3");
    assertEquals("MOVE", parser.getType());
    assertEquals("A", parser.getSource());
    assertEquals("TEST", parser.getDest());
    assertEquals(3, parser.getUnit());

    //test with error case
    assertThrows(IllegalArgumentException.class, () -> new ActionParser("a s v"));
    assertThrows(IllegalArgumentException.class, () -> new ActionParser("move a b 2 3 3"));
    assertThrows(IllegalArgumentException.class, () -> new ActionParser("move1 a test 3"));
  }
  @Test
  public void test_get_cost(){
    Territory<Character> s = new Territory<>("s",9,1,1);
    Territory<Character> d = new Territory<>("d",3,1,1);
    Territory<Character> a = new Territory<>("a",8,1,1);
    Territory<Character> b = new Territory<>("b",2,1,1);
    Territory<Character> enemy = new Territory<>("enemy",1,3,3);
    s.addNeigh(a);
    s.addNeigh(b);
    s.addNeigh(enemy);
    d.addNeigh(a);
    d.addNeigh(b);
    ArrayList<Unit<Character>> nsUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
    s.addGroupUnit(nsUnits);
    ArrayList<Unit<Character>> dUnits = new ArrayList<>(Collections.nCopies(3,new SimpleUnit<>(3)));
    d.addGroupUnit(dUnits);
    Player<Character> p1 = new TextPlayer("p1",100,100);
    Player<Character> p2 = new TextPlayer("p2",100,100);
    p1.addToTerritory(s);
    p1.addToTerritory(d);
    p1.addToTerritory(a);
    p1.addToTerritory(b);
    p2.addToTerritory(enemy);
    Map<Character> map = new Map<>();
    map.addTerritory(s);
    map.addTerritory(d);
    map.addTerritory(a);
    map.addTerritory(b);
    map.addTerritory(enemy);
    map.addPlayer(p1);
    map.addPlayer(p2);
    ActionParser parser_move = new ActionParser("move s d 3 0");
    assertEquals("food: 42", parser_move.getCost(map));
    ActionParser parser_attack = new ActionParser("attack s enemy 2 0");
    assertEquals("food: 2", parser_attack.getCost(map));
    ActionParser parser_update1 = new ActionParser("update","s",null,4,0,2);
    assertEquals("wood: 44", parser_update1.getCost(map));
    ActionParser parser_update2 = new ActionParser("update","d",null,2,3,3);
    assertEquals("wood: 220", parser_update2.getCost(map));
    ActionParser parser_supdate = new ActionParser("supdate","s",null,1);
    assertEquals("wood: 20", parser_supdate.getCost(map));
    ActionParser parser_smove = new ActionParser("smove","d","enemy",1,3);
    assertEquals("food: 15", parser_smove.getCost(map));
    ActionParser parser_cloak = new ActionParser("cloak","s",null,0);
    assertEquals("wood: 20", parser_cloak.getCost(map));
    ActionParser parser_rcloak = new ActionParser("rcloak",null,null,0);
    assertEquals("wood: 100", parser_rcloak.getCost(map));
  }

}
