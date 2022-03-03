package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {
    @Test
    public void test_constructor() {
        Territory<Character> territory = new Territory<Character>("test");
        Territory<Character> territory1 = new Territory<Character>("test_false");
        Map<Character> map = new Map<Character>(territory);
        assertEquals(true, map.checkTerritoryExists(territory));
        assertEquals(false, map.checkTerritoryExists(territory1));
    }

}
