package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        t2.addUnit(u2);
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
        Action<Character> move1 = new MoveAction<>(parser1,map,p1,ruleChecker,true);
        //Action<Character> move = new MoveAction<>(parser1,map,p1);
        assertEquals(null,move1.doAction());
        assertEquals(5,t1.getUnitNumber());
        assertEquals(3,t.getUnitNumber());
        ActionParser parse2_invalid = new ActionParser("move t2 t1 1");
        Action<Character> move2 = new MoveAction<>(parse2_invalid,map,p2,ruleChecker,true);
        assertEquals("That action is invalid: enter a wrong name or do move on other's territories.",
                move2.doAction());
        ActionParser parse3_invalid = new ActionParser("move t1 t2 8");
        Action<Character> move3 = new MoveAction<>(parse3_invalid,map,p1,ruleChecker,true);
        assertEquals("That action is invalid: action number is larger than unit number in the territory.",
                move3.doAction());
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
        TextPlayer p1 = new TextPlayer("Green");
        TextPlayer p2 = new TextPlayer("Blue");
        p1.addToTerritory(terriN);
        p1.addToTerritory(terriO);
        p1.addToTerritory(terriM);
        p2.addToTerritory(terriH);
        p2.addToTerritory(check);
        map.addPlayer(p1);
        map.addPlayer(p2);
        ActionParser parse1 = new ActionParser("attack mordor hogwarts 3");
        Action<Character> attack_m1 = new MoveAction<>(parse1,map,p1,false);
        assertEquals(null,attack_m1.doAction());
        assertEquals(1,terriM.getUnitNumber());
        assertEquals(9,terriH.getUnitNumber());
        assertEquals(3,terriH.getEnemyUnitNum());
        ActionParser parse_invalid = new ActionParser("attack hogwarts oz 5");
        Action<Character> attack_m2 = new MoveAction<>(parse_invalid,map,p2,false);
        assertEquals("That action is invalid: you can only attack directly adjacent territories.",
                attack_m2.doAction());
    }
}
