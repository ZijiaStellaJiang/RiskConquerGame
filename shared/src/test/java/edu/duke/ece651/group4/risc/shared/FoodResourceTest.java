package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FoodResourceTest {
    @Test
    public void test_resource_num(){
        Resource<Character> f = new FoodResource<>(2);
        assertEquals(2,f.getNum());
        f.addResource(3);
        assertEquals(5,f.getNum());
        f.consumeResource(4);
        assertEquals(1,f.getNum());
    }
}
