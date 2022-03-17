package edu.duke.ece651.group4.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import edu.duke.ece651.group4.risc.shared.*;
import org.junit.jupiter.api.Test;


public class MapTextViewTest {
    @Test
    public void test_display_origin_map(){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bytes,true);
        Territory<Character> t1 = new Territory<Character>("A1");
        Territory<Character> t2 = new Territory<Character>("A2");
        Territory<Character> t3 = new Territory<Character>("B1");
        t1.addNeigh(t2);
        t1.addNeigh(t3);
        Map<Character> map = new Map<>();
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addTerritory(t3);
        MapTextView view = new MapTextView(map,ps);
        view.displayOriginMap();
        String expected = "The map is shown below:\n"+
                " 0 units in A1 (next to: A2, B1)\n 0 units in A2 (next to: A1)\n 0 units in B1 (next to: A1)\n";
        assertEquals(expected,bytes.toString());
    }
    @Test
    public void test_display_current_map() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bytes,true);
        Territory<Character> t1 = new Territory<Character>("A1");
        Territory<Character> t2 = new Territory<Character>("A2");
        Territory<Character> t3 = new Territory<Character>("B1");
        t1.addNeigh(t2);
        t1.addNeigh(t3);
        Unit<Character> u1 = new SimpleUnit<>();
        Unit<Character> u2 = new SimpleUnit<>();
        Unit<Character> u3 = new SimpleUnit<>();
        Unit<Character> u4 = new SimpleUnit<>();
        Unit<Character> u5 = new SimpleUnit<>();
        t1.addUnit(u1);
        t1.addUnit(u2);
        t1.addUnit(u3);
        t2.addUnit(u4);
        t2.addUnit(u5);
        TextPlayer p1 = new TextPlayer("A");
        TextPlayer p2 = new TextPlayer("B");
        p1.addToTerritory(t1);
        p1.addToTerritory(t2);
        p2.addToTerritory(t3);
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        MapTextView view = new MapTextView(map,ps);
        view.displayCurrentMap();
        String expected = "Now the map is described as below:\n"+
                "A player:\n---------\n 3 units in A1 (next to: A2, B1)\n 2 units in A2 (next to: A1)\n\n"+
                "B player:\n---------\n 0 units in B1 (next to: A1)\n\n";
        assertEquals(expected, bytes.toString());


        //test player prompt function
        bytes.reset();
        view.displayPlayerMsg(0);
        String expected_1 = "You are the A player, what would you like to do?\n  Move <Source> <Destination> <number>\n  Attack <Source> <Destination> <number>\n  Done\n\n";
        assertEquals(expected_1, bytes.toString());
        //invalid player
        assertThrows(IllegalArgumentException.class, ()->view.displayPlayerMsg(100));
    }
    @Test
    public void test_display_victory(){
        ByteArrayOutputStream bytes0 = new ByteArrayOutputStream();
        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
        PrintStream ps0 = new PrintStream(bytes0,true);
        PrintStream ps1 = new PrintStream(bytes1,true);
        Territory<Character> t1 = new Territory<Character>("A1");
        Territory<Character> t2 = new Territory<Character>("A2");
        Territory<Character> t3 = new Territory<Character>("B1");
        TextPlayer p1 = new TextPlayer("A");
        TextPlayer p2 = new TextPlayer("B");
        p1.addToTerritory(t1);
        p1.addToTerritory(t2);
        p2.addToTerritory(t3);
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        MapTextView view0 = new MapTextView(map,ps0);
        MapTextView view1 = new MapTextView(map,ps1);
        //view.displayVictoryMsg(0);
        assertThrows(IllegalArgumentException.class, ()-> view0.displayVictoryMsg(0));
        p2.removeFromTerritory(t3);
        view0.displayVictoryMsg(0);
        assertEquals("You win!\nCongratulations!\n",bytes0.toString());
        view1.displayVictoryMsg(1);
        assertEquals("You lose!\nA is the winner.\nGood luck next time!\n",bytes1.toString());
    }

}
