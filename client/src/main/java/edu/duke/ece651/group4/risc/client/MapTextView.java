package edu.duke.ece651.group4.risc.client;

import java.io.PrintStream;
import java.util.ArrayList;

import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.Player;
import edu.duke.ece651.group4.risc.shared.Territory;
import edu.duke.ece651.group4.risc.shared.TextPlayer;

public class MapTextView implements View{
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
        if(player_name==null){
            throw new IllegalArgumentException("This is an invalid player id!");
        }
        String head = "You are the "+player_name + " player.\n";
        String msg = head + getRoundPlayerMsg(id);
        if(toDisplay.getLoserId()==null){
            msg = msg+"What would you like to do?\n";
            msg = msg+"  Move <Source> <Destination> <number>\n  Attack <Source> <Destination> <number>\n  Done\n\n";
        }
        out.print(msg);
    }

    @Override
    public void displayVictoryMsg(int id){
        out.print(getVictoryMsg(id));
    }

    public String displayTerritoryInfo(Territory<Character> toDisplay){
        StringBuilder sb = new StringBuilder(toDisplay.getName());
        sb.append("\n--------------------\nSize : ");
        sb.append(toDisplay.getSize());
        sb.append("\nFood Ability : ");
        sb.append(toDisplay.getFoodAbility());
        sb.append("\nWood Ability : ");
        sb.append(toDisplay.getWoodAbility());
        sb.append("\nUnits :\n   Level 0 : ");
        sb.append(toDisplay.getLevelUnitNum(0));
        sb.append("\n   Level 1 : ");
        sb.append(toDisplay.getLevelUnitNum(1));
        sb.append("\n   Level 2 : ");
        sb.append(toDisplay.getLevelUnitNum(2));
        sb.append("\n   Level 3 : ");
        sb.append(toDisplay.getLevelUnitNum(3));
        sb.append("\n   Level 4 : ");
        sb.append(toDisplay.getLevelUnitNum(4));
        sb.append("\n   Level 5 : ");
        sb.append(toDisplay.getLevelUnitNum(5));
        sb.append("\n   Level 6 : ");
        sb.append(toDisplay.getLevelUnitNum(6));
        return sb.toString();
    }

    public String getRoundPlayerMsg(int id){
        String msg = "";
        Player<Character> thisPlayer = toDisplay.getPlayer(id);
        ArrayList<String> lose = thisPlayer.getLoseTerritories();
        ArrayList<String> win = thisPlayer.getWinTerritories();
        if(lose.size()!=0 || win.size()!=0){
            msg = msg+"In the last round,\n";
            if(lose.size()!=0){
                msg = msg+"You lose "+makeResultInfo(lose);
            }
            if(win.size()!=0){
                msg = msg+"You win "+makeResultInfo(win);
            }
        }
        return msg;
    }

    public String getVictoryMsg(int id){
        if(toDisplay.getLoserId()==null){
            throw new IllegalArgumentException("No one wins yet! Can not use this function here!");
        }
        // if this is the loser id
        if(toDisplay.getLoserId().equals(id)){
            String winnerName = "";
            for (int i=0; i<toDisplay.getMyPlayers().size(); i++){
                if(id!=i){
                    //find the winner's id
                    winnerName = toDisplay.getPlayer(i).getName();
                    break;
                }
            }
            return "You lose!\n"+winnerName+" is the winner.\nGood luck next time!\n";
        }
        //else this is the winner id
        else {
            return "You win!\nCongratulations!\n";
        }
    }

    protected String makePlayerInfo(Player<Character> p){
        StringBuilder sb = new StringBuilder(p.getName());
        sb.append(" player:\n");
        int splitLen = p.getName().length()+8;
        sb.append("-".repeat(Math.max(0, splitLen)));
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

    protected String makeResultInfo(ArrayList<String> toPrint){
        int num = toPrint.size();
        StringBuilder sb = new StringBuilder(String.valueOf(num));
        sb.append(" territories: ");
        for (int i=0; i<toPrint.size(); i++){
            sb.append(toPrint.get(i));
            if(i!=toPrint.size()-1){
                sb.append(", ");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

}
