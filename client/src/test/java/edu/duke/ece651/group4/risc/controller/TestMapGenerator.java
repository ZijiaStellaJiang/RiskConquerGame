package edu.duke.ece651.group4.risc.controller;

import edu.duke.ece651.group4.risc.shared.*;

import java.util.ArrayList;
import java.util.Collections;

public class TestMapGenerator {

    public static Map<Character> generateTestMap() {
        Map<Character>testMap = new Map<Character>();
        Territory<Character> terriN = new Territory<Character>("Narnia", 5, 15, 10);
        Territory<Character> terriO = new Territory<Character>("Oz", 10, 20, 15);
        terriN.addNeigh(terriO);
        ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>(0)));
        terriN.addGroupUnit(nUnits);
        ArrayList<Unit<Character>> oUnits = new ArrayList<>(Collections.nCopies(3, new SimpleUnit<>(0)));
        terriO.addGroupUnit(oUnits);
        testMap.addTerritory(terriN);
        testMap.addTerritory(terriO);
        TextPlayer p1 = new TextPlayer("Green", 200, 200);
        TextPlayer p2 = new TextPlayer("Blue", 200, 200);
        p1.addToTerritory(terriN);
        p2.addToTerritory(terriO);
        testMap.addPlayer(p1);
        testMap.addPlayer(p2);
        return testMap;
    }
}
