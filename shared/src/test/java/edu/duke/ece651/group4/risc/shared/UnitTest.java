package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {
    @Test
    public void test_get_level(){
        Unit<Character> u1 = new SimpleUnit<>(0);
        Unit<Character> u2 = new SimpleUnit<>(3);
        assertEquals(0,u1.getLevel());
        assertEquals(3,u2.getLevel());
    }
    @Test
    public void test_equals(){
        Unit<Character> u1 = new SimpleUnit<>(0);
        Unit<Character> u2 = new SimpleUnit<>(3);
        Unit<Character> u3 = new SimpleUnit<>();
        assertEquals(u1,u3);
        assertNotEquals(u1,u2);
        assertEquals(false,u1.equals("u1"));
    }
}
