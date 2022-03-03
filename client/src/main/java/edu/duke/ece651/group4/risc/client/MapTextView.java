package edu.duke.ece651.group4.risc.client;

import java.io.PrintStream;

import edu.duke.ece651.group4.risc.shared.Map;

public class MapTextView implements View{
    private final Map<Character> toDisplay;
    private PrintStream out;

    public MapTextView(Map<Character> toDisplay, PrintStream out){
        this.toDisplay = toDisplay;
        this.out = out;
    }
    @Override
    public void displayMyMap() {
        StringBuilder ans = new StringBuilder("A simple map with one territory:/n");
        ans.append("The map contains following territory: ");
        ans.append(toDisplay.getMyTerritoriesName());
        out.print(ans.toString());
    }

}