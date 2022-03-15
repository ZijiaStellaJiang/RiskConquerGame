package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
    public void test_add_player_and_get_find(){
        TextPlayer p1 = new TextPlayer("A");
        TextPlayer p2 = new TextPlayer("B");
        TextPlayer p3 = new TextPlayer("C");
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        Territory<Character> t = new Territory<>("t");
        Territory<Character> none = new Territory<>("noOwner");
        p2.addToTerritory(t);
        ArrayList<TextPlayer> expected = new ArrayList<>();
        expected.add(p1);
        expected.add(p2);
        ArrayList<TextPlayer> falseAns = new ArrayList<>();
        falseAns.add(p3);
        assertEquals(expected,map.getMyPlayers());
        assertNotEquals(falseAns, map.getMyPlayers());
        assertEquals(p2,map.findPlayer(t));
        assertEquals(null,map.findPlayer(none));
    }
    @Test
    public void test_get_my_territories(){
        Territory<Character> t1 = new Territory<Character>("territory1");
        Territory<Character> t2 = new Territory<Character>("territory2");
        Map<Character> map = new Map<Character>();
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addTerritory(t1);
        ArrayList<Territory<Character>> myTerri = new ArrayList<>();
        myTerri.add(t1);
        myTerri.add(t2);
        assertEquals(myTerri,map.getMyTerritories());
    }
    @Test
    public void test_player_id(){
        Player<Character> p1 = new TextPlayer("A");
        Player<Character> p2 = new TextPlayer("B");
        Player<Character> p3 = new TextPlayer("C");
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        map.addPlayer(p3);
        assertEquals(null,map.getPlayer(3));
        assertEquals(null,map.getPlayerName(4));
        assertEquals(p2,map.getPlayer(1));
        assertEquals("A",map.getPlayerName(0));
    }
}
