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
        Territory<Character> t4 = new Territory<>("B2");
        t1.addNeigh(t2);
        t1.addNeigh(t3);
        t1.addNeigh(t4);
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
        p2.addToTerritory(t4);
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addTerritory(t3);
        map.addTerritory(t4);
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
        ActionParser parse1 = new ActionParser("attack A1 B1 1");
        ActionParser parse2 = new ActionParser("attack A1 B2 1");
        //ActionParser parse2 = new ActionParser("attack B1 A1 2");
        Action<Character> move = new MoveAction<>(false);
        Action<Character> attack = new AttackAction<>("10");
        move.doAction(parse1,map,p1);
        move.doAction(parse2,map,p1);
        //move.doAction(parse2,map,p2);
        attack.doAction(parse1,map,p1);
        //attack.doAction(null,map,p2);
        view.displayPlayerMsg(0);
        assertEquals("You are the A player.\nIn the last round,\n" +
                "You win 2 territories: B2, B1\n"/*+"What would you like to do?\n  Move <Source> <Destination> " +
                "<number>\n  Attack <Source> <Destination> <number>\n  Done\n\n"*/,bytes.toString());
        bytes.reset();
        view.displayPlayerMsg(1);
        assertEquals("You are the B player.\nIn the last round,\nYou lose 2 territories: B2, B1\n"/* +
                "What would you like to do?\n  Move <Source> <Destination> " +
                "<number>\n  Attack <Source> <Destination> <number>\n  Done\n\n"*/,bytes.toString());
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
        p2.addToTerritory(t2);
        p2.addToTerritory(t3);
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        MapTextView view0 = new MapTextView(map,ps0);
        assertThrows(IllegalArgumentException.class, ()-> view0.displayVictoryMsg(0));
        p1.removeFromTerritory(t1);
        view0.displayVictoryMsg(0);
        assertEquals("You lose!\nB is the winner.\nGood luck next time!\n",bytes0.toString());
        bytes0.reset();
        view0.displayVictoryMsg(1);
        assertEquals("You win!\nCongratulations!\n",bytes0.toString());
    }
    @Test
    public void test_display_territory_info(){
        ByteArrayOutputStream bytes0 = new ByteArrayOutputStream();
        PrintStream ps0 = new PrintStream(bytes0,true);
        Territory<Character> n = new Territory<>("Narnia",5,20,10);
        ArrayList<Unit<Character>> units0 = new ArrayList<>(Collections.nCopies(3,new SimpleUnit<>()));
        n.addGroupUnit(units0);
        ArrayList<Unit<Character>> units3 = new ArrayList<>(Collections.nCopies(5,new SimpleUnit<>(3)));
        n.addGroupUnit(units3);
        Map<Character> map = new Map<>();
        MapTextView view0 = new MapTextView(map,ps0);
        String expected = "Narnia\n--------------------\nSize : 5\nFood Ability : 20\nWood Ability : 10\n"+
                          "Units :\n   Level 0 : 3\n   Level 1 : 0\n   Level 2 : 0"+
                          "\n   Level 3 : 5\n   Level 4 : 0\n   Level 5 : 0\n   Level 6 : 0";
        assertEquals(expected,view0.displayTerritoryInfo(n));
    }
}
