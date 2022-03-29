package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest {
    @Test
    public void test_resource_num(){
        Resource<Character> f = new FoodResource<>(2);
        assertEquals(2,f.getNum());
        f.addResource(3);
        assertEquals(5,f.getNum());
        f.consumeResource(4);
        assertEquals(1,f.getNum());
        assertThrows(IllegalArgumentException.class, ()->f.consumeResource(2));
    }

    @Test
    public void test_equals(){
        Resource<Character> f1 = new FoodResource<>(1);
        Resource<Character> f2 = new FoodResource<>(3);
        Resource<Character> w1 = new WoodResource<>(2);
        assertEquals(f1, f2);
        assertNotEquals(f1,w1);
    }
}
