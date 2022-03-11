package edu.duke.ece651.group4.risc.server;

import edu.duke.ece651.group4.risc.shared.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveActionTest {
    @Test
    public void test_do_move(){
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
        Action<Character> action = new MoveAction<>(parser1,map);
        action.doAction();
        assertEquals(5,t1.getUnitNumber());
        assertEquals(4,t2.getUnitNumber());
    }
}
