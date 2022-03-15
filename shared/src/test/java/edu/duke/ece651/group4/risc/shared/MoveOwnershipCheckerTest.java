package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveOwnershipCheckerTest {
    @Test
    public void test_move_own_check(){
        Territory<Character> t1 = new Territory<>("t1");
        Territory<Character> t2 = new Territory<>("t2");
        Territory<Character> t3 = new Territory<>("t3");
        ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
        for (Unit<Character> u: nUnits){
            t1.addUnit(u);
        }
        ArrayList<Unit<Character>> nUnits2 = new ArrayList<>(Collections.nCopies(5, new SimpleUnit<>()));
        for (Unit<Character> u: nUnits2){
            t2.addUnit(u);
        }
        ArrayList<Unit<Character>> nUnits3 = new ArrayList<>(Collections.nCopies(2, new SimpleUnit<>()));
        for (Unit<Character> u: nUnits3){
            t3.addUnit(u);
        }
        TextPlayer p1 = new TextPlayer("A");
        TextPlayer p2 = new TextPlayer("B");
        p1.addToTerritory(t1);
        p1.addToTerritory(t2);
        p2.addToTerritory(t3);
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        ActionRuleChecker<Character> ownChecker = new MoveOwnershipChecker<>(null);
        ActionParser parser1 = new ActionParser("move t1 t2 3");
        assertEquals(null,ownChecker.checkActionRule(parser1,map,p1));
        ActionParser parser2 = new ActionParser("move t3 t1 1");
        assertEquals("That action is invalid: enter a wrong name or do move on other's territories.",
                ownChecker.checkActionRule(parser2,map,p2));
        assertEquals("That action is invalid: enter a wrong name or do move on other's territories.",
                ownChecker.checkActionRule(parser2,map,p1));
    }
}
