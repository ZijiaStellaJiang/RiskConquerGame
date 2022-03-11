package edu.duke.ece651.group4.risc.server;

import edu.duke.ece651.group4.risc.shared.ActionParser;
import edu.duke.ece651.group4.risc.shared.Map;
import edu.duke.ece651.group4.risc.shared.SimpleUnit;
import edu.duke.ece651.group4.risc.shared.Territory;

public class MoveAction<T> extends Action<T>{
    public MoveAction(ActionParser parser, Map<T> map){
        super(parser,map);
    }

    @Override
    public String doAction(){
        if(checkRule()!=null){
            return checkRule();
        }
        for(Territory<T> source: theMap.getMyTerritories()){
            if(source.getName().toUpperCase().equals(parser.getSource())){
                int toMove = parser.getUnit();
                for(Territory<T> dest: theMap.getMyTerritories()){
                    if(dest.getName().toUpperCase().equals(parser.getDest())){
                        /* find the source and destination to move */
                        for(int i=0; i<toMove; i++){
                            dest.addUnit(new SimpleUnit<>());
                            source.removeUnit(new SimpleUnit<>());
                        }
                        break;
                    }
                }
            }
        }
        return null;
    }
}
