package edu.duke.ece651.group4.risc.server;

import edu.duke.ece651.group4.risc.shared.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class V2MapFactoryTest {
    @Test
    public void test_map(){
        V2MapFactory f = new V2MapFactory();
        Map<Character> map = f.generateMap();
        ActionParser parser1 = new ActionParser("move hogwarts gondor 3 0");
        assertEquals("food: 60",parser1.getCost(map));
        ActionParser parser2 = new ActionParser("move hogwarts gondor 4 0");
        assertEquals("food: 80",parser2.getCost(map));
    }
}
