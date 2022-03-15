package edu.duke.ece651.group4.risc.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class V1SimpleResolutionTest {
    @Test
    public void test_V1_resolution(){
        Territory<Character> attack = new Territory<>("a");
        Territory<Character> defend = new Territory<>("b");
        ArrayList<Unit<Character>> toAdd = new ArrayList<>(Collections.nCopies(15,new SimpleUnit<>()));
        defend.addGroupUnit(toAdd);
        for(int i=0; i<5; i++){
            defend.addEnemyUnit(new SimpleUnit<>());
        }
        CombatResolution<Character> resolution = new V1SimpleResolution<>(attack,defend);
        assertEquals(false,resolution.resolveCombat());
    }
}
