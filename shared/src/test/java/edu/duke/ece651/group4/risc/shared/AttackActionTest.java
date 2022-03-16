package edu.duke.ece651.group4.risc.shared;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        TextPlayer p1 = new TextPlayer("Green");
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
        attack.doAction(null,map,p2);
        assertEquals(false,p1.checkMyTerritory(terriO));
        assertEquals(true,p2.checkMyTerritory(terriO));
        attack.doAction(null,map,p1);
        assertEquals(true,p1.checkMyTerritory(terriO));
        assertEquals(false,p2.checkMyTerritory(terriO));
//        assertEquals(4,terriO.getEnemyUnitNum());
//        Action<Character> attack = new AttackAction<>("10");
//        //this seed generate attackRoll=13, defendRoll always 5 (attacker wins)
//        attack.doAction(parse,map,p1);
//        assertEquals(true,p1.checkMyTerritory(terriO));
//        assertEquals(false,p2.checkMyTerritory(terriO));
//        assertEquals(4,terriO.getUnitNumber());
//        assertEquals(0,terriO.getEnemyUnitNum());
//        ActionParser parse2 = new ActionParser("attack mordor oz 1");
//        Action<Character> attack_move2 = new MoveAction<>(false);
//        attack_move2.doAction(parse2,map,p2);
//        Action<Character> attack2 = new AttackAction<>("1");
//        //this seed generate attackRoll=5, defendRoll always 5 (defender wins)
//        attack2.doAction(parse2,map,p2);
//        assertEquals(true,p1.checkMyTerritory(terriO));
//        assertEquals(false,p2.checkMyTerritory(terriO));
//        assertEquals(4,terriO.getUnitNumber());
//        assertEquals(0,terriO.getEnemyUnitNum());
//        assertEquals(2,terriM.getUnitNumber());
    }
}
