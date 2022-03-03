package edu.duke.ece651.group4.risc.client;

import edu.duke.ece651.group4.risc.shared.Map;

public class MapTextView implements View{
    private final Map<Character> toDisplay;
    public MapTextView(Map<Character> toDisplay){
        this.toDisplay = toDisplay;
    }
    @Override
    public void displayMyMap() {
        StringBuilder ans = new StringBuilder("A simple map with one territory:/n");
        ans.append("The map contains one territory: ");
        System.out.print(ans);
    }

}
