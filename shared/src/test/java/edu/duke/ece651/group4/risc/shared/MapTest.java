package edu.duke.ece651.group4.risc.shared;

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
        map.addPlayer(p1);
        assertEquals(null,map.getPlayer(3));
        assertEquals(null,map.getPlayerName(4));
        assertEquals(p2,map.getPlayer(1));
        assertEquals("A",map.getPlayerName(0));
    }

    @Test
    public void test_receive_new_units(){
        Territory<Character> t1 = new Territory<Character>("territory1");
        Territory<Character> t2 = new Territory<Character>("territory2");
        Map<Character> map = new Map<>();
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.receive_new_units();
        assertEquals(1,t1.getUnitNumber());
        assertEquals(1,t2.getUnitNumber());
    }

    @Test
    public void test_get_loser(){
        TextPlayer p1 = new TextPlayer("A");
        Territory<Character> t1 = new Territory<Character>("A1");
        Territory<Character> t2 = new Territory<Character>("A2");
        p1.addToTerritory(t1);
        p1.addToTerritory(t2);
        TextPlayer p2 = new TextPlayer("B");
        Territory<Character> t3 = new Territory<Character>("B1");
        Territory<Character> t4 = new Territory<Character>("B2");
        p2.addToTerritory(t3);
        p2.addToTerritory(t4);
        Map<Character> map = new Map<>();
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addTerritory(t3);
        map.addTerritory(t4);
        map.addPlayer(p1);
        map.addPlayer(p2);
        assertNull(map.getLoserId());
        p2.removeFromTerritory(t4);
        p2.removeFromTerritory(t3);
        assertEquals(1,map.getLoserId());
    }

    @Test
    public void test_map_reset_last_round(){
        Player<Character> p1 = new TextPlayer("A");
        ArrayList<String> lose = new ArrayList<>();
        ArrayList<String> win = new ArrayList<>();
        Territory<Character> t1 = new Territory<>("t1");
        Territory<Character> t2 = new Territory<>("t2");
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        p1.addWinTerritory(t1.getName());
        p1.addLoseTerritory(t2.getName());
        map.resetLastRound();
        assertEquals(lose,p1.getLoseTerritories());
        assertEquals(win,p1.getWinTerritories());
    }
    @Test
    public void test_reset_distance(){
        Territory<Character> t1 = new Territory<>("a");
        Territory<Character> t2 = new Territory<>("b");
        Territory<Character> t3 = new Territory<>("c");
        Map<Character> map = new Map<>();
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addTerritory(t3);
        t2.setDistance(20);
        t1.setDistance(10);
        map.resetDistance();
        assertEquals(100,t1.getDistance());
        assertEquals(100,t2.getDistance());
    }
}
