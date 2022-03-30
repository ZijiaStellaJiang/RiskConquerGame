package edu.duke.ece651.group4.risc.shared;

public class UnitNumberRuleChecker<T> extends ActionRuleChecker<T>{
    public UnitNumberRuleChecker(ActionRuleChecker<T> next){
        super(next);
    }

    /**
     *check if the unit number to perform action is valid
     * action number should less than the unit number in a territory
     */
    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p){
        for(Territory<T> t: map.getMyTerritories()){
            if(t.getName().toUpperCase().equals(parse.getSource())){
                //find the source territory
                int level = parse.getLevel();
                if(t.getLevelUnitNum(level)<parse.getUnit()){
                    return "That action is invalid: this territory doesn't have enough units for this level.";
                }
                break;
            }
        }
        return null;
    }
}
