package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MoveActionTest {
    @Test
    public void test_do_move(){
        ActionRuleChecker<Character> ruleChecker = new UnitNumberRuleChecker<>(
                new MoveOwnershipChecker<>(null));
        Territory<Character> t = new Territory<>("t");
        Territory<Character> t1 = new Territory<>("t1");
        Territory<Character> t2 = new Territory<>("t2");
        ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
        t1.addGroupUnit(nUnits);
        Unit<Character> u2 = new SimpleUnit<>();
        t2.addMyUnit(u2);
        Player<Character> p1 = new TextPlayer("p1");
        Player<Character> p2 = new TextPlayer("p2");
        p1.addToTerritory(t);
        p1.addToTerritory(t1);
        p2.addToTerritory(t2);
        Map<Character> map = new Map<>();
        map.addTerritory(t);
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addPlayer(p1);
        map.addPlayer(p2);
        ActionParser parser1 = new ActionParser("move", "t1", "t", 3);
        Action<Character> move1 = new MoveAction<>(ruleChecker,true);
        //Action<Character> move = new MoveAction<>(parser1,map,p1);
        assertNull(move1.doAction(parser1, map, p1));
        assertEquals(5,t1.getUnitNumber());
        assertEquals(3,t.getUnitNumber());
        ActionParser parser2 = new ActionParser("move t1 t 3");
        move1.doAction(parser2,map,p1);
        assertEquals(2,t1.getUnitNumber());
        ActionParser parse2_invalid = new ActionParser("move t2 t1 1");
        Action<Character> move2 = new MoveAction<>(ruleChecker,true);
        assertEquals("That action is invalid: enter a wrong name or do move on other's territories.",
                move2.doAction(parse2_invalid,map,p2));
        ActionParser parse3_invalid = new ActionParser("move t1 t2 8");
        Action<Character> move3 = new MoveAction<>(ruleChecker,true);
        assertEquals("That action is invalid: this territory doesn't have enough units for this level.",
                move3.doAction(parse3_invalid,map,p1));
    }

    @Test
    public void test_do_move_in_attack(){
        Territory<Character> terriN = new Territory<Character>("Narnia");
        Territory<Character> terriO = new Territory<Character>("Oz");
        Territory<Character> terriM = new Territory<Character>("Mordor");
        Territory<Character> terriH = new Territory<Character>("Hogwarts");
        Territory<Character> check = new Territory<>("check");
        terriN.addNeigh(terriO);
        terriO.addNeigh(terriM);
        terriM.addNeigh(terriH);
        terriH.addNeigh(check);
        ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
        terriN.addGroupUnit(nUnits);
        ArrayList<Unit<Character>> oUnits = new ArrayList<>(Collections.nCopies(3, new SimpleUnit<>()));
        terriO.addGroupUnit(oUnits);
        ArrayList<Unit<Character>> mUnits = new ArrayList<>(Collections.nCopies(4, new SimpleUnit<>()));
        terriM.addGroupUnit(mUnits);
        ArrayList<Unit<Character>> hUnits = new ArrayList<>(Collections.nCopies(9, new SimpleUnit<>()));
        terriH.addGroupUnit(hUnits);
        Map<Character> map = new Map<Character>();
        map.addTerritory(terriN);
        map.addTerritory(terriO);
        map.addTerritory(terriM);
        map.addTerritory(terriH);
        map.addTerritory(check);
        TextPlayer p1 = new TextPlayer("Green",100,100);
        TextPlayer p2 = new TextPlayer("Blue");
        p1.addToTerritory(terriN);
        p1.addToTerritory(terriO);
        p1.addToTerritory(terriM);
        p2.addToTerritory(terriH);
        p2.addToTerritory(check);
        map.addPlayer(p1);
        map.addPlayer(p2);
        ActionParser parse1 = new ActionParser("attack mordor hogwarts 3");
        Action<Character> attack_m1 = new MoveAction<>(false);
        assertNull(attack_m1.doAction(parse1, map, p1));
        assertEquals(1,terriM.getUnitNumber());
        assertEquals(9,terriH.getUnitNumber());
        assertEquals(3,terriH.getEnemyUnitNum());
        ActionParser parse_invalid = new ActionParser("attack hogwarts oz 5");
        Action<Character> attack_m2 = new MoveAction<>(false);
        assertEquals("That action is invalid: you can only attack directly adjacent territories.",
                attack_m2.doAction(parse_invalid,map,p2));
    }
    @Test
    public void test_move_for_myself_with_cost(){
        Territory<Character> s = new Territory<>("s",9,1,1);
        Territory<Character> d = new Territory<>("d",3,1,1);
        Territory<Character> a = new Territory<>("a",8,1,1);
        Territory<Character> b = new Territory<>("b",2,1,1);
        Territory<Character> c = new Territory<>("c",4,1,1);
        s.addNeigh(a);
        s.addNeigh(c);
        s.addNeigh(b);
        d.addNeigh(a);
        d.addNeigh(c);
        ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
        s.addGroupUnit(nUnits);
        Player<Character> p1 = new TextPlayer("p1",100,100);
        p1.addToTerritory(s);
        p1.addToTerritory(d);
        p1.addToTerritory(a);
        p1.addToTerritory(b);
        p1.addToTerritory(c);
        Map<Character> map = new Map<>();
        map.addTerritory(s);
        map.addTerritory(d);
        map.addTerritory(a);
        map.addTerritory(b);
        map.addTerritory(c);
        map.addPlayer(p1);
        ActionParser parser1 = new ActionParser("move", "s", "d", 5,0);
        Action<Character> move1 = new MoveAction<>(true);
        assertNull(move1.doAction(parser1, map, p1));
        assertEquals(5,d.getUnitNumber());
        assertEquals(3,s.getUnitNumber());
        assertEquals(20,p1.getFoodNum());
        assertEquals(100,p1.getWoodNum());
        //for test case coverage
        Territory<Character> test = new Territory<>("test");
        map.addTerritory(test);
        ActionParser parser2 = new ActionParser("move", "s", "test", 2,0);
        Action<Character> move_test = new MoveAction<>(new UnitNumberRuleChecker<>(null),true);
        assertNull(move_test.doAction(parser2,map,p1));
        assertEquals(20,p1.getFoodNum());
    }
}
