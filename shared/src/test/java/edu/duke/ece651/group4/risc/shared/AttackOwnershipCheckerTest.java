package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttackOwnershipCheckerTest {
    @Test
    public void test_attack_own_check(){
        Territory<Character> t1 = new Territory<>("t1");
        Territory<Character> t2 = new Territory<>("t2");
        Territory<Character> t3 = new Territory<>("t3");
        ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
        for (Unit<Character> u: nUnits){
            t1.addMyUnit(u);
        }
        ArrayList<Unit<Character>> nUnits2 = new ArrayList<>(Collections.nCopies(5, new SimpleUnit<>()));
        for (Unit<Character> u: nUnits2){
            t2.addMyUnit(u);
        }
        ArrayList<Unit<Character>> nUnits3 = new ArrayList<>(Collections.nCopies(2, new SimpleUnit<>()));
        for (Unit<Character> u: nUnits3){
            t3.addMyUnit(u);
        }
        TextPlayer p1 = new TextPlayer("A");
        TextPlayer p2 = new TextPlayer("B");
        p1.addToTerritory(t1);
        p1.addToTerritory(t2);
        p2.addToTerritory(t3);
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addTerritory(t3);
        ActionRuleChecker<Character> checker = new AttackOwnershipChecker<>(null);
        ActionParser parse1 = new ActionParser("attack t1 t3 3");
        assertEquals(null,checker.checkActionRule(parse1,map,p1));
        ActionParser parse_invalid_1 = new ActionParser("attack t2 t1 3");
        assertEquals("That action is invalid: do attack to your own territory",
                checker.checkActionRule(parse_invalid_1,map,p1));
        ActionParser parse_invalid_2 = new ActionParser("attack t1 t2 2");
        assertEquals("That action is invalid: enter a wrong name or do attack from other's territory",
                checker.checkActionRule(parse_invalid_2,map,p2));
    }
}
