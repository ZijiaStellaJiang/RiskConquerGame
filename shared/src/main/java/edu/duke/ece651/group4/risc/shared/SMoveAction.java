package edu.duke.ece651.group4.risc.shared;

public class SMoveAction<T> extends Action<T> {

    public SMoveAction() {
        super(new SMoveNumChecker<>(new SMovePathChecker<>(new SMoveResourceChecker<>(null))));
    }

    @Override
    public String doAction(ActionParser parser, Map<T> map, Player<T> player) {
        String checkRule = ruleChecker.checkActionRule(parser,map,player);
        if(checkRule!=null){
            return checkRule;
        }
        Territory<T> source = map.findTerritory(parser.getSource());
        Territory<T> dest = map.findTerritory(parser.getDest());
        boolean moveFromMy = player.checkMyTerritory(source);
        boolean moveToMy = player.checkMyTerritory(dest);
        int toMove = parser.getUnit();

        //handle spy numbers in source and dest
        for (int i=0; i<toMove; i++) {
            if(moveFromMy) source.removeMySpy();
            else source.removeEnemySpy();

            if(moveToMy) dest.addMySpy();
            else dest.addEnemySpy();
        }

        //calculate cost
        int cost;
        MinCostFinder<T> finder = new MinCostFinder<>();
        if(source.checkNeigh(dest)) cost = source.getSize()+dest.getSize();
        else if(moveFromMy==moveToMy) cost = finder.findMinCost(source,dest,player);
        else {
            //from mine to enemy
            cost = costForMoveBetweenDifferentPlayer(dest,source,player);
        }
        player.consumeResource(new FoodResource<>(cost*toMove));
        map.resetDistance();
        return null;
    }

    /**
     * helper function for move cost
     * @param findAdjacent: find all direct adjacent territories which is not the same player as
     * @param endPoint: calculate distance between endPoint and neigh of findAdjacent
     * @param player: the player
     * @return the minCost
     */
    private int costForMoveBetweenDifferentPlayer (Territory<T> findAdjacent, Territory<T> endPoint,
                                                   Player<T> player) {
        int minCost = 300;
        MinCostFinder<T> finder = new MinCostFinder<>();
        for(Territory<T> neigh: findAdjacent.getMyNeigh()) {
            if(!player.checkMyTerritory(neigh)) continue;
            int cost = finder.findMinCost(neigh,endPoint,player);
            if(cost<minCost) minCost = cost;
        }
        return minCost + findAdjacent.getSize();
    }
}
