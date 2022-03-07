package edu.duke.ece651.group4.risc.server;
import edu.duke.ece651.group4.risc.shared.*;

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
