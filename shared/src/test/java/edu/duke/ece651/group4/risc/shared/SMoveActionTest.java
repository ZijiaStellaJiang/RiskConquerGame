package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMoveActionTest {
    @Test
    public void test_spy_move() {
        Territory<Character> a1 = new Territory<>("a1",5,3,3);
        Territory<Character> a2 = new Territory<>("a2",2,6,6);
        Territory<Character> a3 = new Territory<>("a3",9,4,4);
        Territory<Character> a4 = new Territory<>("a4",10,2,2);
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
        p1.addToTerritory(a4);
        p2.addToTerritory(b1);
        p2.addToTerritory(b2);
        p2.addToTerritory(b3);
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        map.addTerritory(a1);
        map.addTerritory(a2);
        map.addTerritory(a3);
        map.addTerritory(a4);
        map.addTerritory(b1);
        map.addTerritory(b2);
        map.addTerritory(b3);

        a1.addMySpy();

        Action<Character> sMove = new SMoveAction<>();
        ActionParser parser_invalid = new ActionParser("smove","a1","a3",2);
        ActionParser parser = new ActionParser("smove","a1","a3",1);
        assertEquals("That action is invalid: this territory doesn't have enough spy to move.",
                sMove.doAction(parser_invalid,map,p1));
        assertNull(sMove.doAction(parser, map, p1));
        assertEquals(1,a3.getSpyNumber());
        assertEquals(0,a1.getSpyNumber());
        assertEquals(286,p1.getFoodNum());
        parser_invalid = new ActionParser("smove","a3","a4",1);
        assertEquals("That action is invalid: can not form a path from source to destination.",
                sMove.doAction(parser_invalid,map,p1));
        parser_invalid = new ActionParser("smove","a3","b2",1);
        assertEquals("That action is invalid: you can only move to an enemy's territory which is " +
                        "directly adjacent to yours.", sMove.doAction(parser_invalid,map,p1));
        parser = new ActionParser("smove","a3","b1",1);
        assertNull(sMove.doAction(parser,map,p1));
        assertEquals(1,b1.getEnemySpyNumber());
        assertEquals(268,p1.getFoodNum());
        parser_invalid = new ActionParser("smove","b1","b2",1);
        assertEquals("That action is invalid: you can only move one territory in the enemy's territory",
                sMove.doAction(parser_invalid,map,p1));
        parser_invalid = new ActionParser("smove","b1","b3",2);
        assertEquals("That action is invalid: you don't have enough spies in this territory.",
                sMove.doAction(parser_invalid,map,p1));
        parser = new ActionParser("smove","b1","b3",1);
        assertNull(sMove.doAction(parser,map,p1));
        parser_invalid = new ActionParser("smove","b3","b1",1);
        assertEquals("That action is invalid: you can only move spy one time in the enemy's territory",
                sMove.doAction(parser_invalid,map,p1));
        p1.setMoveSpyInEnemy(false);
        parser = new ActionParser("smove","b3","b1",1);
        assertNull(sMove.doAction(parser,map,p1));
        assertEquals(246,p1.getFoodNum());
        p1.setMoveSpyInEnemy(false);
        parser = new ActionParser("smove","b1","a1",1);
        assertNull(sMove.doAction(parser,map,p1));
        p1.consumeResource(new FoodResource<>(235));
        parser_invalid = new ActionParser("smove","a1","a3",1);
        assertEquals("That action is invalid: you don't have enough food to move the spy.",
                sMove.doAction(parser_invalid,map,p1));
    }
}