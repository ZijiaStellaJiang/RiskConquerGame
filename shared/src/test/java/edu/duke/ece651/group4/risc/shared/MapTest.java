package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {
    @Test
    public void test_construct_and_add() {
        Territory<Character> t1 = new Territory<Character>("territory1");
        Territory<Character> t2 = new Territory<Character>("territory2");
        Map<Character> map = new Map<Character>();
        map.addTerritory(t1);
        assertEquals(true, map.checkTerritoryExists(t1));
        assertEquals(false, map.checkTerritoryExists(t2));
    }
//    @Test
//    public void test_get_territories_name() {
//        Territory<Character> t1 = new Territory<Character>("territory1");
//        Territory<Character> t2 = new Territory<Character>("territory2");
//        Map<Character> map = new Map<Character>();
//        map.addTerritory(t1);
//        map.addTerritory(t2);
//        assertEquals("territory1",map.getMyTerritoryName(t1));
//        assertEquals("territory2",map.getMyTerritoryName(t2));
//    }
    @Test
    public void test_get_my_territory(){
        Territory<Character> t1 = new Territory<Character>("territory1");
        Territory<Character> t2 = new Territory<Character>("territory2");
        Map<Character> map = new Map<Character>();
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addTerritory(t1); 
        ArrayList<Territory<Character>> myTerri = new ArrayList<>();
        myTerri.add(t1);
        myTerri.add(t2);
        assertEquals(myTerri,map.getMyTerritory());
    }
}
