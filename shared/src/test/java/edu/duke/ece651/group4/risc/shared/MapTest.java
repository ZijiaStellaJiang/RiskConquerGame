package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MapTest {
    @Test
    public void test_construct_and_add() {
        Territory<Character> t1 = new Territory<Character>("territory1");
        Territory<Character> t2 = new Territory<Character>("territory2");
        Map<Character> map = new Map<Character>();
        map.addTerritory(t1);
        assertEquals(true, map.checkTerritoryExists(t1));
        assertEquals(false, map.checkTerritoryExists(t2));
        map.addTerritory(t1);
        assertEquals(true, map.checkTerritoryExists(t1));
    }
    @Test
    public void test_add_player_and_get(){
        Player p1 = new Player("A");
        Player p2 = new Player("B");
        Player p3 = new Player("C");
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        map.addPlayer(p1);
        ArrayList<Player> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(p2);
        ArrayList<Player> falseAns = new ArrayList<>();
        falseAns.add(p3);
        assertEquals(expected,map.getMyPlayers());
        assertFalse(falseAns.equals(map.getMyPlayers()));
    }
//    @Test
//    public void test_get_my_players(){
//        Territory<Character> t1 = new Territory<Character>("territory1");
//        Territory<Character> t2 = new Territory<Character>("territory2");
//        Map<Character> map = new Map<Character>();
//        map.addTerritory(t1);
//        map.addTerritory(t2);
//        map.addTerritory(t1);
//        ArrayList<Territory<Character>> myTerri = new ArrayList<>();
//        myTerri.add(t1);
//        myTerri.add(t2);
//        assertEquals(myTerri,map.getMyTerritory());
//    }
}
