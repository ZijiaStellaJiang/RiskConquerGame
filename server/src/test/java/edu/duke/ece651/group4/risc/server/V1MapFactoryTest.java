package edu.duke.ece651.group4.risc.server;

import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.Territory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class V1MapFactoryTest {
    @Test
    public void test_generate_map(){
        V1MapFactory f = new V1MapFactory();
        Map<Character> map = f.generateMap();
        Territory<Character> t = new Territory<>("Oz");
        assertTrue(map.checkTerritoryExists(t));
        ArrayList<Territory<Character>> myTerri = map.getMyTerritories();
        Territory<Character> tn = myTerri.get(0);
        assertEquals(8,tn.getUnitNumber());
    }
}
