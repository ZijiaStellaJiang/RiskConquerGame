package edu.duke.ece651.group4.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
        ArrayList<Unit<Character>> n1 = new ArrayList<>(Collections.nCopies(3,new SimpleUnit<>()));
        t1.addGroupUnit(n1);
        ArrayList<Unit<Character>> n2 = new ArrayList<>(Collections.nCopies(2,new SimpleUnit<>()));
        t2.addGroupUnit(n2);
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
    }
    @Test
    public void test_player_msg(){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bytes,true);
        Territory<Character> t1 = new Territory<Character>("A1");
        Territory<Character> t2 = new Territory<Character>("A2");
        Territory<Character> t3 = new Territory<Character>("B1");
        t1.addNeigh(t2);
        t1.addNeigh(t3);
        ArrayList<Unit<Character>> n1 = new ArrayList<>(Collections.nCopies(3,new SimpleUnit<>()));
        t1.addGroupUnit(n1);
        ArrayList<Unit<Character>> n2 = new ArrayList<>(Collections.nCopies(2,new SimpleUnit<>()));
        t2.addGroupUnit(n2);
        ArrayList<Unit<Character>> n3 = new ArrayList<>(Collections.nCopies(2,new SimpleUnit<>()));
        t3.addGroupUnit(n3);
        TextPlayer p1 = new TextPlayer("A");
        TextPlayer p2 = new TextPlayer("B");
        p1.addToTerritory(t1);
        p1.addToTerritory(t2);
        p2.addToTerritory(t3);
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addTerritory(t3);
        MapTextView view = new MapTextView(map,ps);
        view.displayPlayerMsg(0);
        String expected_1 = "You are the A player.\nWhat would you like to do?\n  " +
                "Move <Source> <Destination> <number>\n  Attack <Source> <Destination> <number>\n  Done\n\n";
        assertEquals(expected_1, bytes.toString());
        //invalid player
        assertThrows(IllegalArgumentException.class, ()->view.displayPlayerMsg(100));
        bytes.reset();
        view.displayPlayerMsg(1);
        assertEquals("You are the B player.\nWhat would you like to do?\n  " +
                "Move <Source> <Destination> <number>\n  Attack <Source> <Destination> <number>\n  Done\n\n",
                bytes.toString());
        bytes.reset();
        ActionParser parse1 = new ActionParser("attack A1 B1 3");
        ActionParser parse2 = new ActionParser("attack B1 A1 2");
        Action<Character> move = new MoveAction<>(false);
        Action<Character> attack = new AttackAction<>();
        move.doAction(parse1,map,p1);
        move.doAction(parse2,map,p2);
        attack.doAction(null,map,p1);
        attack.doAction(null,map,p2);
        view.displayPlayerMsg(0);
        assertEquals("You are the A player.\nIn the last round,\nYou lose 1 territories: A1\n" +
                "You win 1 territories: B1\nWhat would you like to do?\n  Move <Source> <Destination> " +
                "<number>\n  Attack <Source> <Destination> <number>\n  Done\n\n",bytes.toString());
        bytes.reset();
        view.displayPlayerMsg(1);
        assertEquals("You are the B player.\nIn the last round,\nYou lose 1 territories: B1\n" +
                "You win 1 territories: A1\nWhat would you like to do?\n  Move <Source> <Destination> " +
                "<number>\n  Attack <Source> <Destination> <number>\n  Done\n\n",bytes.toString());
    }
    @Test
    public void test_display_victory(){
        ByteArrayOutputStream bytes0 = new ByteArrayOutputStream();
        PrintStream ps0 = new PrintStream(bytes0,true);
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
        assertThrows(IllegalArgumentException.class, ()-> view0.displayVictoryMsg(0));
        p2.removeFromTerritory(t3);
        view0.displayVictoryMsg(0);
        assertEquals("You win!\nCongratulations!\n",bytes0.toString());
        bytes0.reset();
        view0.displayVictoryMsg(1);
        assertEquals("You lose!\nA is the winner.\nGood luck next time!\n",bytes0.toString());
    }

}
