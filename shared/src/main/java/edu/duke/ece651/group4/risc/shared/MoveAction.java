package edu.duke.ece651.group4.risc.shared;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MoveAction<T> extends Action<T>{
//    //define the comparator of priority queue in minimum path
//    Comparator<Territory<T>> cmp = new Comparator<Territory<T>>() {
//        @Override
//        public int compare(Territory<T> o1, Territory<T> o2) {
//            return o1.getDistance()-o2.getDistance();
//        }
//    };
    boolean moveToSamePlayer;

    /**
     * @param samePlayer: indicate whether to move units into same player's territory
     *                  pass true if for real "move" order
     *                  pass false if this move is for attacker to move units into enemy's territory
     */
    public MoveAction(boolean samePlayer){
        super(new UnitNumberRuleChecker<>(new MoveOwnershipChecker<>(new MovePathChecker<>(
                new MoveResourceChecker<>(null)))));
        this.moveToSamePlayer = samePlayer;
    }

    /** for test constructor only, DO NOT use in server and client */
    public MoveAction(ActionRuleChecker<T> ruleChecker, boolean samePlayer){
        super(ruleChecker);
        this.moveToSamePlayer = samePlayer;
    }

    @Override
    /**
     * this function do move for each move order (or attack move phase)
     * @param parser: the order which this move action is going to perform.
     * @param theMap: the whole map.
     * @param thePlayer: the player who issue this move action.
     * @return null if action success
     * @return error message if action is invalid
     */
    public String doAction(ActionParser parser,Map<T> theMap, Player<T> thePlayer){
        //if this move is used for attack, the rule checker should change
        if(!moveToSamePlayer){
            ruleChecker = new UnitNumberRuleChecker<>(new AttackOwnershipChecker<>(
                    new AttackPathChecker<>(new AttackResourceChecker<>(null))));
        }
        String checkRule = ruleChecker.checkActionRule(parser,theMap,thePlayer);
        if(checkRule!=null){
            return checkRule;
        }
        Territory<T> source = theMap.findTerritory(parser.getSource());
        Territory<T> dest = theMap.findTerritory(parser.getDest());
        int toMove = parser.getUnit();
        int unit_level = parser.getLevel();
        int cost;
        if(moveToSamePlayer){
            cost = moveUnits(parser, source, thePlayer.getMyTerritories(), toMove, unit_level, thePlayer);
            dest.updateInfo(true);
        }
        else {
            cost = moveUnits(parser, source, theMap.getMyTerritories(), toMove, unit_level, thePlayer);
        }
        source.updateInfo(true);
        thePlayer.consumeResource(new FoodResource<>(cost));
        theMap.resetDistance();
        return null;

    }

    /**
     * helper function to move units
     * @return the minimum cost if it is a move order
     * can not use the return value if it is for enemy move
     */
    private int moveUnits(ActionParser parser,Territory<T> source, Collection<Territory<T> > toFind,
                           int toMove, int level, Player<T> player){
        MinCostFinder<T> finder = new MinCostFinder<>();
        for(Territory<T> dest: toFind){
            if(dest.getName().toUpperCase().equals(parser.getDest())){
                int cost = 1;
                if(moveToSamePlayer) cost = finder.findMinCost(source,dest,player);
                //int minCost = findMinCost(source,dest);
                for(int i=0; i<toMove; i++){
                    if(moveToSamePlayer){
                        dest.addMyUnit(new SimpleUnit<>(level));
                    }
                    else {
                        dest.addEnemyUnit(new SimpleUnit<>(level));
                    }
                    source.removeMyUnit(new SimpleUnit<>(level));
                }
                return cost * toMove;
            }
        }
        return 0;
    }
}
