package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TerritoryDisplayInfoTest {
    @Test
    public void test_constructor() {
        TerritoryDisplayInfo<Character> i1 = new TerritoryDisplayInfo<>();
        assertEquals(7, i1.getUnitNumList().size());
        assertEquals(0,i1.getUnitNumList().get(4));
    }

    @Test
    public void test_update() {
        TerritoryDisplayInfo<Character> i1 = new TerritoryDisplayInfo<>();
        i1.updateLevelInfo(3,2);
        assertEquals(2,i1.getUnitNumList().get(3));
    }

    @Test
    public void test_latest() {
        TerritoryDisplayInfo<Character> i1 = new TerritoryDisplayInfo<>();
        assertTrue(i1.checkLatest());
        i1.setIsLatest(false);
        assertFalse(i1.checkLatest());
    }
}
