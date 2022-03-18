package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TextPlayerTest {
    @Test
    public void test_player_constructor() {
        TextPlayer player = new TextPlayer("test_player");
        assertEquals("test_player", player.getName());
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
        //TODO: add test cases
    }
}
