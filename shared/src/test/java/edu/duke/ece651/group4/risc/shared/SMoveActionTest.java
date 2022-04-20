package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMoveActionTest {
    @Test
    public void test_spy_move() {
        Territory<Character> a1 = new Territory<>("a1",5,3,3);
        Territory<Character> a2 = new Territory<>("a2",2,6,6);
        Territory<Character> a3 = new Territory<>("a3",9,4,4);
        Territory<Character> b1 = new Territory<>("b1",4,3,3);
        Territory<Character> b2 = new Territory<>("b2",5,6,6);
        Territory<Character> b3 = new Territory<>("b3",7,4,4);
        a1.addNeigh(a2);
        a1.addNeigh(a3);
        a1.addNeigh(b1);
        a1.addNeigh(b3);
        a2.addNeigh(a3);
        a2.addNeigh(b3);
        b1.addNeigh(b3);
        b2.addNeigh(b3);
        Player<Character> p1 = new TextPlayer("a",300,300);
        Player<Character> p2 = new TextPlayer("b",300,300);
        p1.addToTerritory(a1);
        p1.addToTerritory(a2);
        p1.addToTerritory(a3);
        p2.addToTerritory(b1);
        p2.addToTerritory(b2);
        p2.addToTerritory(b3);
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        map.addTerritory(a1);
        map.addTerritory(a2);
        map.addTerritory(a3);
        map.addTerritory(b1);
        map.addTerritory(b2);
        map.addTerritory(b3);

        a1.addMySpy();

        Action<Character> sMove = new SMoveAction<>();
        ActionParser parser_invalid = new ActionParser("smove","a1","a3",2);
        ActionParser parser = new ActionParser("smove","a1","a3",1);
        assertEquals("That action is invalid: this territory doesn't have enough spy to move.",
                sMove.doAction(parser_invalid,map,p1));
    }
}