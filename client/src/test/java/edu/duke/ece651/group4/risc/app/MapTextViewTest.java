package edu.duke.ece651.group4.risc.app;

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
        Territory<Character> t1 = new Territory<>("territory1");
        Map<Character> map = new Map<>(t1);
        MapTextView view = new MapTextView(map,ps);
        view.displayMyMap();
        String expected = "A simple map with one territory:/nThe map contains following territory: territory1";
        assertEquals(expected, bytes.toString());
    }

}