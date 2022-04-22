package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class AttackActionTest {
    @Test
    public void test_attack(){
        Territory<Character> t1 = new Territory<>("t1");
        Territory<Character> terriN = new Territory<Character>("Narnia");
        Territory<Character> terriO = new Territory<Character>("Oz");
        Territory<Character> terriM = new Territory<Character>("Mordor");
        terriN.addNeigh(terriO);
        terriO.addNeigh(terriM);
        ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(5, new SimpleUnit<>()));
        terriN.addGroupUnit(nUnits);
        ArrayList<Unit<Character>> oUnits = new ArrayList<>(Collections.nCopies(2, new SimpleUnit<>()));
        terriO.addGroupUnit(oUnits);
        ArrayList<Unit<Character>> mUnits = new ArrayList<>(Collections.nCopies(3, new SimpleUnit<>()));
        terriM.addGroupUnit(mUnits);
        Map<Character> map = new Map<Character>();
        map.addTerritory(t1);
        map.addTerritory(terriN);
        map.addTerritory(terriO);
        map.addTerritory(terriM);
        TextPlayer p1 = new TextPlayer("Green",100,100);
        TextPlayer p2 = new TextPlayer("Blue");
        p1.addToTerritory(t1);
        p1.addToTerritory(terriN);
        p2.addToTerritory(terriO);
        p1.addToTerritory(terriM);
        map.addPlayer(p1);
        map.addPlayer(p2);
        ActionParser parse1 = new ActionParser("attack narnia oz 4");
        ActionParser parse2 = new ActionParser("attack mordor oz 1");
        Action<Character> attack_move = new MoveAction<>(false);
        attack_move.doAction(parse1,map,p1);
        attack_move.doAction(parse2,map,p1);
        assertEquals(1,terriN.getUnitNumber());
        assertEquals(2,terriM.getUnitNumber());
        assertEquals(5,terriO.getEnemyUnitNum());
        Action<Character> attack = new AttackAction<>("10");
        //this seed generate attackRoll=13, defendRoll always 5 (attacker wins)
        attack.doAction(null,map,p2);
        assertFalse(p1.checkMyTerritory(terriO));
        assertTrue(p2.checkMyTerritory(terriO));
        attack.doAction(null,map,p1);
        assertTrue(p1.checkMyTerritory(terriO));
        assertFalse(p2.checkMyTerritory(terriO));
        assertEquals(5,terriO.getUnitNumber());
    }
    @Test
    public void test_attack_with_levels(){
        Territory<Character> terriN = new Territory<Character>("Narnia",0,5,5);
        Territory<Character> terriO = new Territory<Character>("Oz",0,7,7);
        terriN.addNeigh(terriO);
        terriN.addMyUnit(new SimpleUnit<>(4));
        terriN.addMyUnit(new SimpleUnit<>(4));
        terriN.addMyUnit(new SimpleUnit<>(2));
        terriN.addMyUnit(new SimpleUnit<>(1));
        terriO.addMyUnit(new SimpleUnit<>(6));
        terriO.addMyUnit(new SimpleUnit<>(4));
        terriO.addMyUnit(new SimpleUnit<>(1));
        terriO.addMyUnit(new SimpleUnit<>());
        Map<Character> map = new Map<>();
        map.addTerritory(terriN);
        map.addTerritory(terriO);
        Player<Character> p1 = new TextPlayer("Green",100,100);
        Player<Character> p2 = new TextPlayer("Blue",100,100);
        p1.addToTerritory(terriN);
        p2.addToTerritory(terriO);
        map.addPlayer(p1);
        map.addPlayer(p2);
        Action<Character> attack_move = new MoveAction<>(false);
        Action<Character> attack = new AttackAction<>("1");
        //this seed generate attackRoll=5, defendRoll always 5
        ActionParser parse1 = new ActionParser("attack oz narnia 1 4");
        ActionParser parse2 = new ActionParser("attack oz narnia 1 6");
        ActionParser parse3 = new ActionParser("attack oz narnia 1 1");
        ActionParser parse4 = new ActionParser("attack oz narnia 1 0");
        //assertEquals("food: 1",attack_move.actionCost(parse2,map));
        attack_move.doAction(parse1,map,p2);
        attack_move.doAction(parse2,map,p2);
        attack_move.doAction(parse3,map,p2);
        attack_move.doAction(parse4,map,p2);
        assertEquals(4,terriN.getUnitNumber());
        assertEquals(0,terriO.getUnitNumber());
        attack.doAction(null,map,p2);
        assertFalse(p1.checkMyTerritory(terriN));
        assertTrue(p2.checkMyTerritory(terriN));
        assertEquals(96,p2.getFoodNum());
        p1.updateResource();
        p2.updateResource();
        assertEquals(100,p1.getFoodNum());
        assertEquals(108,p2.getFoodNum());
        assertEquals(112,p2.getWoodNum());
    }
    @Test
    public void test_attack_with_spy() {
        Territory<Character> nar = new Territory<>("narnia",2,3,3);
        Territory<Character> hog = new Territory<>("hogwarts",4,2,2);
        nar.addNeigh(hog);
        Player<Character> p1 = new TextPlayer("p1",300,300);
        Player<Character> p2 = new TextPlayer("p2", 300,300);
        p1.addToTerritory(nar);
        p2.addToTerritory(hog);
        Map<Character> map = new Map<>();
        map.addTerritory(nar);
        map.addTerritory(hog);
        map.addPlayer(p1);
        map.addPlayer(p2);
        nar.addGroupUnit(new ArrayList<>(Collections.nCopies(4,new SimpleUnit<>(1))));
        hog.addMySpy();
        hog.addMySpy();
        hog.addEnemySpy();
        assertEquals(2,hog.getSpyNumber());
        assertEquals(1,hog.getEnemySpyNumber());

        Action<Character> rcloak = new ResearchCloakAction<>();
        ActionParser parser_r = new ActionParser("rcloak",null,null,0);
        assertNull(rcloak.doAction(parser_r,map,p2));
        Action<Character> cloak = new CloakAction<>();
        ActionParser parser_cloak = new ActionParser("cloak", hog.getName(), null, 0);
        assertNull(cloak.doAction(parser_cloak,map,p2));
        assertEquals(3,hog.cloakgetCount());

        Action<Character> attack_move = new MoveAction<>(false);
        Action<Character> attack = new AttackAction<>();
        ActionParser parser = new ActionParser("attack","narnia","hogwarts",3,1);
        attack_move.doAction(parser,map,p1);
        assertNull(attack.doAction(parser,map,p1));
        assertEquals(2,hog.getEnemySpyNumber());
        assertEquals(1,hog.getSpyNumber());
        assertEquals(0,hog.cloakgetCount());
    }
}
