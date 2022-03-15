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
        for (Unit<Character> u: nUnits){
            t1.addUnit(u);
        }
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
}
