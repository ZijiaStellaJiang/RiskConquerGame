package edu.duke.ece651.group4.risc.shared;

public class SMovePathChecker<T> extends ActionRuleChecker<T> {
    public SMovePathChecker(ActionRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p) {
        //todo: test
        Territory<T> source = map.findTerritory(parse.getSource());
        Territory<T> dest = map.findTerritory(parse.getDest());
        boolean sourceMine = p.checkMyTerritory(source);
        boolean destMine = p.checkMyTerritory(dest);
        //if move from own territory
        if(sourceMine) {
            if(destMine) {
                //if move between own territories, just like original movePathChecker
                ActionRuleChecker<T> movePathChecker = new MovePathChecker<>(null);
                return movePathChecker.checkActionRule(parse,map,p);
            }
            else {
                for(Territory<T> destNeigh: dest.getMyNeigh()) {
                    if(!p.checkMyTerritory(destNeigh)) continue;
                    ActionRuleChecker<T> movePathChecker = new MovePathChecker<>(null);
                    ActionParser tempParse = new ActionParser("MOVE",destNeigh.getName(),source.getName(),0);
                    if(movePathChecker.checkActionRule(tempParse,map,p)==null) return null;
                }
                return "That action is invalid: you can only move to an enemy's territory which is " +
                        "directly adjacent to yours.";
            }
        }
        //if move from enemy's territory
        //whether move to enemy's or not, can only move to adjacent
        else {
            if (p.getMoveSpyInEnemy()) {
                return "That action is invalid: you can only move spy one time in the enemy's territory";
            }
            if(!source.checkNeigh(dest)) {
                return "That action is invalid: you can only move one territory in the enemy's territory";
            }
            //action valid, deal with move times
            p.setMoveSpyInEnemy(true);
        }
        return null;
    }
}
