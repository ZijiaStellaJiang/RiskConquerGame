package edu.duke.ece651.group4.risc.server;

import java.io.PrintStream;

import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.Player;
import edu.duke.ece651.group4.risc.shared.Territory;
import edu.duke.ece651.group4.risc.shared.TextPlayer;

public class MapTextView implements View, java.io.Serializable{
    private final Map<Character> toDisplay;
    private PrintStream out;

    public MapTextView(Map<Character> toDisplay, PrintStream out){
        this.toDisplay = toDisplay;
        this.out = out;
    }
    @Override
    public void displayOriginMap(){
        StringBuilder ans = new StringBuilder("The map is shown below:\n");
        for (Territory<Character> t: toDisplay.getMyTerritories()){
            ans.append(makeTerritoryInfo(t));
        }
        out.print(ans);
    }
    @Override
    public void displayCurrentMap() {
        StringBuilder ans = new StringBuilder("Now the map is described as below:\n");
        for (Player<Character> p: toDisplay.getMyPlayers()){
            ans.append(makePlayerInfo(p));
            ans.append("\n");
        }
        out.print(ans);
    }

    @Override
    public void displayPlayerMsg(int id) {
        String player_name = toDisplay.getPlayerName(id);
        if (player_name != null) {
            out.print("You are the " + player_name + " player, what would you like to do?\n");
            out.print("  Move <Source> <Destination> <number>\n  Attack <Source> <Destination> <number>\n  Done\n\n");
        }
    }

    protected String makePlayerInfo(Player<Character> p){
        StringBuilder sb = new StringBuilder(p.getName());
        sb.append(" player:\n");
        int splitLen = p.getName().length()+8;
        for (int i=0;i<splitLen;i++){
            sb.append("-");
        }
        sb.append("\n");
        for(Territory<Character> myTerri: p.getMyTerritories()){
            sb.append(makeTerritoryInfo(myTerri));
        }
        return sb.toString();
    }

    protected String makeTerritoryInfo(Territory<Character> t){
        StringBuilder sb = new StringBuilder(" ");
        sb.append(t.getUnitNumber());
        sb.append(" units in ");
        sb.append(t.getName());
        sb.append(" (next to: ");
        for (Territory<Character> neigh: t.getMyNeigh()){
            sb.append(neigh.getName());
            if(t.getMyNeigh().indexOf(neigh)!=t.getMyNeigh().size()-1){
                sb.append(", ");
            }
        }
        sb.append(")\n");
        return sb.toString();
    }

}
