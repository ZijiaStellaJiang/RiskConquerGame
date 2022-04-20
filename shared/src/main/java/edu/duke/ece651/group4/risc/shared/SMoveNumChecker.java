package edu.duke.ece651.group4.risc.shared;

public class SMoveNumChecker<T> extends ActionRuleChecker<T> {
    public SMoveNumChecker(ActionRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p) {
        //todo: test
        Territory<T> source = map.findTerritory(parse.getSource());
        int moveNum = parse.getUnit();
        //move spy from a player's own territory
        if(p.checkMyTerritory(source)) {
            if(moveNum>source.getSpyNumber()) {
                return "That action is invalid: this territory doesn't have enough spy to move.";
            }
        }
        //move spy from enemy territory
        else {
            if(moveNum>source.getEnemySpyNumber()) {
                return "That action is invalid: you don't have enough spies in this territory.";
            }
        }
        return null;
    }
}
