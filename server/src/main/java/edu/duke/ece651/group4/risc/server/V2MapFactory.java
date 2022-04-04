package edu.duke.ece651.group4.risc.server;

import edu.duke.ece651.group4.risc.shared.*;

import java.util.ArrayList;
import java.util.Collections;

public class V2MapFactory implements AbstractMapFactory<Character> {
    @Override
    public Map<Character> generateMap(){
        Territory<Character> terriN = new Territory<Character>("Narnia",5,15,10);
        Territory<Character> terriO = new Territory<Character>("Oz",10,20,15);
        Territory<Character> terriM = new Territory<Character>("Mordor",5,15,10);
        Territory<Character> terriH = new Territory<Character>("Hogwarts",8,20,13);
        Territory<Character> terriG = new Territory<Character>("Gondor",5,12,10);
        Territory<Character> terriE = new Territory<Character>("Elantris",7,18,12);
        terriN.addNeigh(terriO);
        terriN.addNeigh(terriM);
        terriN.addNeigh(terriH);
        terriO.addNeigh(terriM);
        terriM.addNeigh(terriH);
        terriM.addNeigh(terriE);
        terriM.addNeigh(terriG);
        terriH.addNeigh(terriE);
        terriE.addNeigh(terriG);
        ArrayList<Unit<Character>> nUnits = new ArrayList<>(Collections.nCopies(8, new SimpleUnit<>(0)));
        terriN.addGroupUnit(nUnits);
        ArrayList<Unit<Character>> oUnits = new ArrayList<>(Collections.nCopies(3, new SimpleUnit<>(0)));
        terriO.addGroupUnit(oUnits);
        ArrayList<Unit<Character>> mUnits = new ArrayList<>(Collections.nCopies(4, new SimpleUnit<>(0)));
        terriM.addGroupUnit(mUnits);
        ArrayList<Unit<Character>> hUnits = new ArrayList<>(Collections.nCopies(9, new SimpleUnit<>(0)));
        terriH.addGroupUnit(hUnits);
        ArrayList<Unit<Character>> gUnits = new ArrayList<>(Collections.nCopies(2, new SimpleUnit<>(0)));
        terriG.addGroupUnit(gUnits);
        ArrayList<Unit<Character>> eUnits = new ArrayList<>(Collections.nCopies(4, new SimpleUnit<>(0)));
        terriE.addGroupUnit(eUnits);
        Map<Character> map = new Map<Character>();
        map.addTerritory(terriN);
        map.addTerritory(terriO);
        map.addTerritory(terriM);
        map.addTerritory(terriH);
        map.addTerritory(terriG);
        map.addTerritory(terriE);
        TextPlayer p1 = new TextPlayer("Green",200,200);
        TextPlayer p2 = new TextPlayer("Blue",200,200);
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
