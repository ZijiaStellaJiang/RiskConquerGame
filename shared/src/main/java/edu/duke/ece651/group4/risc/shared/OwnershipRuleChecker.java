package edu.duke.ece651.group4.risc.shared;

public class OwnershipRuleChecker<T> extends ActionRuleChecker<T>{
    public OwnershipRuleChecker(ActionRuleChecker<T> next){ super(next);}

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
        return "That action is invalid: do action on other's territories.";
    }
}
