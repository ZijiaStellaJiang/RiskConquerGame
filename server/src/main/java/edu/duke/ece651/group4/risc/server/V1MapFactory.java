package edu.duke.ece651.group4.risc.server;
import edu.duke.ece651.group4.risc.shared.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class V1MapFactory implements AbstractMapFactory<Character> {
    public Map<Character> generateMap() {
        Territory<Character> terriN = new Territory<Character>("Narnia");
        Territory<Character> terriO = new Territory<Character>("Oz");
        Territory<Character> terriM = new Territory<Character>("Mordor");
        Territory<Character> terriH = new Territory<Character>("Hogwarts");
        Territory<Character> terriG = new Territory<Character>("Gondor");
        Territory<Character> terriE = new Territory<Character>("Elantris");
        terriN.addNeigh(terriO);
        terriN.addNeigh(terriM);
        terriN.addNeigh(terriH);
        terriO.addNeigh(terriM);
        terriM.addNeigh(terriH);
        terriH.addNeigh(terriG);
        terriH.addNeigh(terriE);
        terriG.addNeigh(terriE);
        /**
         * add units in all the territories
         */
        ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>()));
        terriN.addGroupUnit(nUnits);
        ArrayList<Unit<Character>> oUnits = new ArrayList<>(Collections.nCopies(3, new SimpleUnit<>()));
        terriO.addGroupUnit(oUnits);
        ArrayList<Unit<Character>> mUnits = new ArrayList<>(Collections.nCopies(4, new SimpleUnit<>()));
        terriM.addGroupUnit(mUnits);
        ArrayList<Unit<Character>> hUnits = new ArrayList<>(Collections.nCopies(9, new SimpleUnit<>()));
        terriH.addGroupUnit(hUnits);
        ArrayList<Unit<Character>> gUnits = new ArrayList<>(Collections.nCopies(2, new SimpleUnit<>()));
        terriG.addGroupUnit(gUnits);
        ArrayList<Unit<Character>> eUnits = new ArrayList<>(Collections.nCopies(4, new SimpleUnit<>()));
        terriE.addGroupUnit(eUnits);
        Map<Character> map = new Map<Character>();
        map.addTerritory(terriN);
        map.addTerritory(terriO);
        map.addTerritory(terriM);
        map.addTerritory(terriH);
        map.addTerritory(terriG);
        map.addTerritory(terriE);
        TextPlayer p1 = new TextPlayer("Green");
        TextPlayer p2 = new TextPlayer("Blue");
        p1.addToTerritory(terriN);
        p1.addToTerritory(terriO);
        p1.addToTerritory(terriM);
        p2.addToTerritory(terriH);
        p2.addToTerritory(terriG);
        p2.addToTerritory(terriE);
        map.addPlayer(p1);
        map.addPlayer(p2);
        return map;
    }
}
