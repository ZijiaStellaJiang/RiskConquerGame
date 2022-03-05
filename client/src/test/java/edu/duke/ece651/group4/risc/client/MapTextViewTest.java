package edu.duke.ece651.group4.risc.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import edu.duke.ece651.group4.risc.shared.Player;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.group4.risc.shared.Territory;
import edu.duke.ece651.group4.risc.shared.Map;


public class MapTextViewTest {
    @Test
    public void test_display_my_map() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bytes,true);
        Territory<Character> t1 = new Territory<Character>("A1");
        Territory<Character> t2 = new Territory<Character>("A2");
        Territory<Character> t3 = new Territory<Character>("B1");
        t1.addNeigh(t2);
        t2.addNeigh(t1);
        t1.addNeigh(t3);
        t3.addNeigh(t1);
        Player p1 = new Player("A");
        Player p2 = new Player("B");
        p1.addToTerritory(t1);
        p1.addToTerritory(t2);
        p2.addToTerritory(t3);
        Map<Character> map = new Map<>();
        map.addPlayer(p1);
        map.addPlayer(p2);
        MapTextView view = new MapTextView(map,ps);
        view.displayMyMap();
        String expected = "Now the map is described as below:/n"+
                "A player:/n---------/n A1 (next to: A2, B1)/n A2 (next to: A1)/n/n"+
                "B player:/n---------/n B1 (next to: A1)/n/n";
        assertEquals(expected, bytes.toString());
    }

}
