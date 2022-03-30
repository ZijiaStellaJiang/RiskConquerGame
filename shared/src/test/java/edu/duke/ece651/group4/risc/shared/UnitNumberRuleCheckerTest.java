package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class UnitNumberRuleCheckerTest {
    @Test
    public void test_unitNumber_checker(){
        ActionRuleChecker<Character> numberChecker1 = new UnitNumberRuleChecker<>(null);
        Territory<Character> t1 = new Territory<>("t1");
        Territory<Character> t2 = new Territory<>("t2");
        ArrayList<Unit<Character>> nUnits0 = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
        t1.addGroupUnit(nUnits0);
        Unit<Character> u2 = new SimpleUnit<>();
        t2.addMyUnit(u2);
        Player<Character> p = new TextPlayer("p1");
        Map<Character> map = new Map<>();
        map.addTerritory(t1);
        map.addTerritory(t2);
        ActionParser parser1 = new ActionParser("move", "t1", "t2", 3);
        assertNull(numberChecker1.checkActionRule(parser1, map, p));
        ActionParser parser2 = new ActionParser("move","t2","t1",4);
        assertEquals("That action is invalid: this territory doesn't have enough units for this level."
        ,numberChecker1.checkActionRule(parser2,map,p));
        ArrayList<Unit<Character>> nUnits2 = new ArrayList<>(Collections.nCopies(4, new SimpleUnit<>(2)));
        t1.addGroupUnit(nUnits2);
        ActionParser parser3 = new ActionParser("move t1 t2 3 2");
        assertNull(numberChecker1.checkActionRule(parser3,map,p));
        ActionParser parser4 = new ActionParser("move t1 t2 5 2");
        assertEquals("That action is invalid: this territory doesn't have enough units for this level."
                ,numberChecker1.checkActionRule(parser4,map,p));
    }
}
