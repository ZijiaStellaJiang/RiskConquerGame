package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TerritoryTest {
    @Test
    public void test_territory_constructor() {
        Territory<Character> territory = new Territory<Character>("test");
        assertEquals("test", territory.getName());
    }

}
