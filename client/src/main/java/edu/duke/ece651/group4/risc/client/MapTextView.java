package edu.duke.ece651.group4.risc.client;

import java.io.PrintStream;

import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.Territory;

public class MapTextView implements View{
    private final Map<Character> toDisplay;
    private PrintStream out;

    public MapTextView(Map<Character> toDisplay, PrintStream out){
        this.toDisplay = toDisplay;
        this.out = out;
    }
    @Override
    public void displayMyMap() {
        StringBuilder ans = new StringBuilder("Now the map is described as below:/n");
        for (Territory<Character> t: toDisplay.getMyTerritory()){
            ans.append(territoryInfo(t));
        }
        out.print(ans);
    }
    protected String territoryInfo(Territory<Character> t){
        StringBuilder sb = new StringBuilder(t.getName());
        sb.append(" (next to: ");
        for (Territory<Character> neigh: t.getMyNeigh()){
            sb.append(neigh.getName());
            if(t.getMyNeigh().indexOf(neigh)!=t.getMyNeigh().size()-1){
                sb.append(", ");
            }
        }
        sb.append(")/n");
        return sb.toString();
    }
}