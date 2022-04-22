package edu.duke.ece651.group4.risc.shared;

public class SMoveResourceChecker<T> extends ActionRuleChecker<T> {
    public SMoveResourceChecker(ActionRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p) {
        Territory<T> source = map.findTerritory(parse.getSource());
        Territory<T> dest = map.findTerritory(parse.getDest());
        boolean moveFromMy = p.checkMyTerritory(source);
        boolean moveToMy = p.checkMyTerritory(dest);
        int toMove = parse.getUnit();
        int cost;
        MinCostFinder<T> finder = new MinCostFinder<>();
        if(source.checkNeigh(dest)) cost = source.getSize()+dest.getSize();
        else if(moveFromMy==moveToMy) cost = finder.findMinCost(source,dest,p);
        else {
            //from mine to enemy
            cost = finder.costForMoveBetweenDifferentPlayer(dest,source,p);
        }
        map.resetDistance();
        if(cost*toMove > p.getFoodNum()) {
            return "That action is invalid: you don't have enough food to move the spy.";
        }
        return null;
    }
}
