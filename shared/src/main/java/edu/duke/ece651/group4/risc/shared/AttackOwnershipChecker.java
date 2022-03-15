package edu.duke.ece651.group4.risc.shared;

public class AttackOwnershipChecker<T> extends ActionRuleChecker<T> {
    public AttackOwnershipChecker(ActionRuleChecker<T> next){super(next);}

    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p) {
        for (Territory<T> t: p.getMyTerritories()){
            if(t.getName().toUpperCase().equals(parse.getSource())){
                for(Territory<T> dest: map.getMyTerritories()){
                    if(dest.getName().toUpperCase().equals(parse.getDest())){
                        if(!p.checkMyTerritory(dest)){
                            return null;
                        }
                        else{
                            return "That action is invalid: do attack to your own territory";
                        }
                    }
                }
            }
        }
        return "That action is invalid: enter a wrong name or do attack from other's territory";
    }
}
