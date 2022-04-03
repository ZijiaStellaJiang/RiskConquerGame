package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceCheckerTest {
    @Test
    public void test_move_resource_checker(){
        Territory<Character> a = new Territory<>("A",3,4,4);
        Territory<Character> b = new Territory<>("B",5,3,3);
        Territory<Character> c = new Territory<>("C",1,3,3);
        Territory<Character> d = new Territory<>("D",2,2,2);
        a.addNeigh(b);
        a.addNeigh(d);
        b.addNeigh(c);
        c.addNeigh(d);
        ArrayList<Unit<Character>> aUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
        a.addGroupUnit(aUnits);
        Player<Character> p = new TextPlayer("p",40,40);
        Map<Character> map = new Map<>();
        map.addPlayer(p);
        map.addTerritory(a);
        map.addTerritory(b);
        map.addTerritory(c);
        map.addTerritory(d);
        p.addToTerritory(a);
        p.addToTerritory(b);
        p.addToTerritory(c);
        p.addToTerritory(d);
        ActionRuleChecker<Character> resource_c = new MoveResourceChecker<>(null);
        ActionParser parse1 = new ActionParser("move a c 5 0");
        assertNull(resource_c.checkActionRule(parse1,map,p));
        ActionParser parse2 = new ActionParser("move a c 8 0");
        resource_c.checkActionRule(parse2,map,p);
        assertEquals("That action is invalid: player doesn't have enough food to move.",
                resource_c.checkActionRule(parse2,map,p));
    }
    @Test
    public void test_attack_resource_checker(){
        Territory<Character> a = new Territory<>("A",3,4,4);
        Territory<Character> b = new Territory<>("B",5,3,3);
        a.addNeigh(b);
        ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(6,new SimpleUnit<>()));
        a.addGroupUnit(nUnits);
        Player<Character> p1 = new TextPlayer("p1",4,4);
        Player<Character> p2 = new TextPlayer("p2",4,4);
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        map.addTerritory(a);
        map.addTerritory(b);
        p1.addToTerritory(a);
        p2.addToTerritory(b);
        ActionRuleChecker<Character> resource_c = new AttackResourceChecker<>(null);
        ActionParser parser1 = new ActionParser("attack a b 4 0");
        assertNull(resource_c.checkActionRule(parser1,map,p1));
        ActionParser parser2 = new ActionParser("attack a b 5 0");
        assertEquals("That action is invalid: player doesn't have enough food to attack.",
                resource_c.checkActionRule(parser2,map,p1));
    }
}
