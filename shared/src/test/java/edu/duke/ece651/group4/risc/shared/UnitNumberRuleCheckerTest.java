package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitNumberRuleCheckerTest {
    @Test
    public void test_unitNumber_checker(){
        ActionRuleChecker<Character> numberChecker1 = new UnitNumberRuleChecker<>(null);
        Territory<Character> t1 = new Territory<>("t1");
        Territory<Character> t2 = new Territory<>("t2");
        ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
        for (Unit<Character> u: nUnits){
            t1.addUnit(u);
        }
        Unit<Character> u2 = new SimpleUnit<>();
        t2.addUnit(u2);
        Map<Character> map = new Map<>();
        map.addTerritory(t1);
        map.addTerritory(t2);
        ActionParser parser1 = new ActionParser("move", "t1", "t2", 3);
        assertEquals(null,numberChecker1.checkActionRule(parser1,map));
        ActionParser parser2 = new ActionParser("move","t2","t1",4);
        assertEquals("That action is invalid: action number is larger than unit number in the territory."
        ,numberChecker1.checkActionRule(parser2,map));
    }
}
