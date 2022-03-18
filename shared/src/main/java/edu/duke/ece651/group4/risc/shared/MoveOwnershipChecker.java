package edu.duke.ece651.group4.risc.shared;

public class MoveOwnershipChecker<T> extends ActionRuleChecker<T>{
    public MoveOwnershipChecker(ActionRuleChecker<T> next){ super(next);}

    /**
     * This rule ensures move action applied on the player's own territories
     */
    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p){
        for(Territory<T> t: p.getMyTerritories()){
            if(t.getName().toUpperCase().equals(parse.getSource())){
                for(Territory<T> t2: p.getMyTerritories()){
                    if(t2.getName().toUpperCase().equals(parse.getDest())){
                        return null;
                    }
                }
            }
        }
        return "That action is invalid: enter a wrong name or do move on other's territories.";
    }
}
