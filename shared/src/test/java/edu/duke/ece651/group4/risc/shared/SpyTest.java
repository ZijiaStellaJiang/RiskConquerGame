package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpyTest {
    @Test
    public void test_construct_and_equals() {
        Spy<Character> s1 = new Spy<>();
        Spy<Character> s2 = new Spy<>();
        Unit<Character> u = new SimpleUnit<>();
        assertEquals(s1,s2);
        assertNotEquals(s1, u);
    }

}