package edu.duke.ece651.group4.risc.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import edu.duke.ece651.group4.risc.client.MapTextView;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.group4.risc.shared.Territory;
import edu.duke.ece651.group4.risc.shared.Map;


public class MapTextViewTest {
    @Test
    public void test_display_my_map() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bytes,true);
        Territory<Character> t1 = new Territory<Character>("A");
        Territory<Character> t2 = new Territory<Character>("B");
        Territory<Character> t3 = new Territory<Character>("C");
        t1.addNeigh(t2);
        t1.addNeigh(t3);
        Map<Character> map = new Map<>();
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addTerritory(t3);
        MapTextView view = new MapTextView(map,ps);
        view.displayMyMap();
        String expected = "Now the map is described as below:/n"+
                "A (next to: B, C)/n"+"B (next to: )/n"+"C (next to: )/n";
        assertEquals(expected, bytes.toString());
    }

}
