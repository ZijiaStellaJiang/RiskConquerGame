package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.IdentityHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TextPlayerTest {
    @Test
    public void test_player_constructor() {
        TextPlayer player = new TextPlayer("test_player");
        assertEquals("test_player", player.getName());
        Player<Character> p = new TextPlayer("p1",20,30);
        assertEquals(20,p.getFoodNum());
        assertEquals(30,p.getWoodNum());
    }

    @Test
    public void test_my_territory(){
        TextPlayer player = new TextPlayer("test_player");
        Territory<Character> territory = new Territory<Character>("test1");
        Territory<Character> territory1 = new Territory<Character>("test2");
        assertEquals(false, player.checkMyTerritory(territory));
        assertEquals(false, player.checkMyTerritory(territory1));
        player.addToTerritory(territory);
        player.removeFromTerritory(territory1);
        assertEquals(true, player.checkMyTerritory(territory));
        assertEquals(false, player.checkMyTerritory(territory1));
        //player.addToTerritory(territory1);
        player.removeFromTerritory(territory);
        assertEquals(false, player.checkMyTerritory(territory));
        assertEquals(false, player.checkMyTerritory(territory1));
    }

    @Test
    public void test_equals(){
        TextPlayer player = new TextPlayer("test_player");
        TextPlayer player1 = new TextPlayer("test_player1");
        assertEquals(false, player1.equals(player));
        assertEquals(false, player1.equals(1));
        assertEquals(false, player1.equals("test_player1"));
        assertEquals(true, player1.equals(player1));
        TextPlayer player2 = new TextPlayer("test_player1");
        assertEquals(true, player1.equals(player2));
    }

    @Test
    public void test_get_my_territory(){
        TextPlayer p1 = new TextPlayer("A");
        Territory<Character> t1 = new Territory<Character>("A1");
        Territory<Character> t2 = new Territory<Character>("A2");
        p1.addToTerritory(t1);
        p1.addToTerritory(t2);
        ArrayList<Territory<Character>> myTerri = new ArrayList<>();
        myTerri.add(t1);
        myTerri.add(t2);
        assertEquals(myTerri,p1.getMyTerritories());
    }

    @Test
    public void test_check_lose(){
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
        assertFalse(p1.checkLose());
        assertFalse(p2.checkLose());
        p1.removeFromTerritory(t1);
        assertFalse(p1.checkLose());
        p1.removeFromTerritory(t2);
        assertTrue(p1.checkLose());
    }
    @Test
    public void test_last_round_change(){
        Player<Character> p1 = new TextPlayer("A");
        ArrayList<String> lose = new ArrayList<>();
        ArrayList<String> win = new ArrayList<>();
        assertEquals(lose,p1.getLoseTerritories());
        assertEquals(win,p1.getWinTerritories());
        Territory<Character> t1 = new Territory<>("t1");
        Territory<Character> t2 = new Territory<>("t2");
        p1.addWinTerritory(t1.getName());
        p1.addLoseTerritory(t2.getName());
        win.add("t1");
        lose.add("t2");
        assertEquals(lose,p1.getLoseTerritories());
        assertEquals(win,p1.getWinTerritories());
        p1.resetLastRoundChange();
        assertEquals(new ArrayList<String>(),p1.getWinTerritories());
        assertEquals(new ArrayList<String>(),p1.getLoseTerritories());
    }
    @Test
    public void test_resource(){
        Player<Character> p = new TextPlayer("a");
        Territory<Character> t1 = new Territory<>("t1",2,4,3);
        Territory<Character> t2 = new Territory<>("t2",3,1,6);
        p.addToTerritory(t1);
        p.addToTerritory(t2);
        assertEquals(0,p.getFoodNum());
        assertEquals(0,p.getWoodNum());
        p.updateResource();
        assertEquals(5,p.getFoodNum());
        assertEquals(9,p.getWoodNum());
        p.consumeResource(new FoodResource<>(1));
        p.consumeResource(new WoodResource<>(4));
        assertEquals(4,p.getFoodNum());
        assertEquals(5,p.getWoodNum());
        p.removeFromTerritory(t1);
        assertEquals(4,p.getFoodNum());
        assertEquals(5,p.getWoodNum());
        p.updateResource();
        assertEquals(5,p.getFoodNum());
        assertEquals(11,p.getWoodNum());
    }
    @Test
    public void test_find_destinations(){
        Territory<Character> n = new Territory<>("N");
        Territory<Character> o = new Territory<>("O");
        Territory<Character> a = new Territory<>("A");
        Territory<Character> b = new Territory<>("B");
        Territory<Character> m = new Territory<>("M");
        n.addNeigh(o);
        o.addNeigh(b);
        o.addNeigh(m);
        a.addNeigh(m);
        Player<Character> green = new TextPlayer("g");
        Player<Character> blue = new TextPlayer("b");
        green.addToTerritory(n);
        green.addToTerritory(o);
        green.addToTerritory(b);
        blue.addToTerritory(a);
        blue.addToTerritory(m);
        ArrayList<Territory<Character>> move = green.findDestinations(o,true);
        assertTrue(move.contains(n));
        assertTrue(move.contains(b));
        assertFalse(move.contains(o));
        assertFalse(move.contains(m));
        assertFalse(move.contains(a));
        ArrayList<Territory<Character>> attack = blue.findDestinations(m,false);
        assertTrue(attack.contains(o));
        assertFalse(attack.contains(n));
        assertFalse(attack.contains(a));
    }

    @Test
    public void test_check_visibility() {
        Territory<Character> t1 = new Territory<>("a");
        Territory<Character> t2 = new Territory<>("b");
        Territory<Character> t3 = new Territory<>("c");
        Territory<Character> k1 = new Territory<>("d");
        t2.addNeigh(t1);
        t1.addNeigh(t3);
        t2.addNeigh(k1);
        Player<Character> p1 = new TextPlayer("p1");
        Player<Character> p2 = new TextPlayer("p2");
        p1.addToTerritory(t1);
        p1.addToTerritory(t2);
        p1.addToTerritory(t3);
        p2.addToTerritory(k1);
        p1.checkTerritoryVisibility(t2);
        assertTrue(p1.checkTerritoryVisibility(t2));
        assertFalse(p1.checkTerritoryVisibility(t1));
        assertThrows(IllegalArgumentException.class, ()->p2.checkTerritoryVisibility(t3));
    }
}
