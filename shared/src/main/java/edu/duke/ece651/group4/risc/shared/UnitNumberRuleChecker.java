package edu.duke.ece651.group4.risc.shared;

import java.util.Locale;

public class UnitNumberRuleChecker<T> extends ActionRuleChecker<T>{
    public UnitNumberRuleChecker(ActionRuleChecker<T> next){
        super(next);
    }

    /**
     *check if the unit number to perform action is valid
     * action number should less than the unit number in a territory
     */
    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map){
        for(Territory<T> t: map.getMyTerritories()){
            if(t.getName().toUpperCase().equals(parse.getSource())){
                if(t.getUnitNumber()<parse.getUnit()){
                    return "That action is invalid: action number is larger than unit number in the territory.";
                }
                break;
            }
        }
        return null;
    }
}
