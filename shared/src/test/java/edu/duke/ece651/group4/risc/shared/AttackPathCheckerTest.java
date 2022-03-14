package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttackPathCheckerTest {
    protected void addUnitsInTerritory(Territory<Character> target, ArrayList<Unit<Character>> toAdd){
        for (Unit<Character> u: toAdd){
            target.addUnit(u);
        }
    }
    @Test
    public void test_attack_path_checker(){
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
        addUnitsInTerritory(terriN,nUnits);
        ArrayList<Unit<Character>> oUnits = new ArrayList<>(Collections.nCopies(3, new SimpleUnit<>()));
        addUnitsInTerritory(terriO,oUnits);
        ArrayList<Unit<Character>> mUnits = new ArrayList<>(Collections.nCopies(4, new SimpleUnit<>()));
        addUnitsInTerritory(terriM,mUnits);
        ArrayList<Unit<Character>> hUnits = new ArrayList<>(Collections.nCopies(9, new SimpleUnit<>()));
        addUnitsInTerritory(terriH,hUnits);
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
        ActionRuleChecker<Character> checker = new AttackPathChecker<>(null);
        ActionParser parse1 = new ActionParser("attack Mordor Hogwarts 2");
        assertEquals(null,checker.checkActionRule(parse1,map,p1));
        ActionParser parse_invalid = new ActionParser("attack hogwarts oz 8");
        assertEquals("That action is invalid: you can only attack directly adjacent territories.",
                checker.checkActionRule(parse_invalid,map,p2));
    }
}
