package edu.duke.ece651.group4.risc.shared;

public class AttackPathChecker<T> extends ActionRuleChecker<T> {
    public AttackPathChecker(ActionRuleChecker<T> next) {super(next);}

    /**
     * This rule ensures attack a directly neighbor
     */
    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p){
        for( Territory<T> source : p.getMyTerritories()){
            if(source.getName().toUpperCase().equals(parse.getSource())){
                for (Territory<T> neigh: source.getMyNeigh()){
                    if(neigh.getName().toUpperCase().equals(parse.getDest())){
                        return null;
                    }
                }
                break;
            }
        }
        return "That action is invalid: you can only attack directly adjacent territories.";
    }
}
