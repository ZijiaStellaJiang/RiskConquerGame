package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AttackActionTest {
    @Test
    public void test_attack(){
        Territory<Character> t1 = new Territory<>("t1");

        ArrayList<Unit<Character>> Units = new ArrayList<>(Collections.nCopies(5, new SimpleUnit<>()));
        t1.addGroupUnit(Units);
        Map<Character> map = new Map<Character>();
        map.addTerritory(t1);
        TextPlayer p1 = new TextPlayer("Green", 1000, 1000);
        p1.addToTerritory(t1);
        map.addPlayer(p1);
        ActionParser parse1 = new ActionParser("update t1 null 6 0");
        ActionParser parse2 = new ActionParser("update t1 null 5 0");
        Action<Character> update = new UpdateAction<>();
        attack_move.doAction(parse1,map,p1);
        attack_move.doAction(parse2,map,p1);
        assertEquals(1,terriN.getUnitNumber());
        assertEquals(2,terriM.getUnitNumber());
        assertEquals(5,terriO.getEnemyUnitNum());
        Action<Character> attack = new AttackAction<>("10");
        //this seed generate attackRoll=13, defendRoll always 5 (attacker wins)
        attack.doAction(null,map,p2);
        assertEquals(false,p1.checkMyTerritory(terriO));
        assertEquals(true,p2.checkMyTerritory(terriO));
        attack.doAction(null,map,p1);
        assertEquals(true,p1.checkMyTerritory(terriO));
        assertEquals(false,p2.checkMyTerritory(terriO));
        assertEquals(5,terriO.getUnitNumber());
    }
}
