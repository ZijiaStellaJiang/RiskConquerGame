package edu.duke.ece651.group4.risc.shared;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MoveAction<T> extends Action<T>{
    //define the comparator of priority queue in minimum path
    Comparator<Territory<T>> cmp = new Comparator<Territory<T>>() {
        @Override
        public int compare(Territory<T> o1, Territory<T> o2) {
            return o1.getDistance()-o2.getDistance();
        }
    };
    boolean moveToSamePlayer;

    /**
     * @param samePlayer: indicate whether to move units into same player's territory
     *                  pass true if for real "move" order
     *                  pass false if this move is for attacker to move units into enemy's territory
     */
    public MoveAction(boolean samePlayer){
        super(new UnitNumberRuleChecker<>(new MoveOwnershipChecker<>(new MovePathChecker<>(null))));
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
                    new AttackPathChecker<>(null)));
        }
        String checkRule = ruleChecker.checkActionRule(parser,theMap,thePlayer);
        if(checkRule!=null){
            return checkRule;
        }
        for(Territory<T> source: thePlayer.getMyTerritories()){
            if(source.getName().toUpperCase().equals(parser.getSource())){
                int toMove = parser.getUnit();
                int unit_level = parser.getLevel();
                int cost;
                if(moveToSamePlayer){
                    cost = moveUnits(parser, source, thePlayer.getMyTerritories(), toMove, unit_level);
                }
                else {
                    cost = moveUnits(parser, source, theMap.getMyTerritories(), toMove, unit_level);
                }
                thePlayer.consumeResource(new FoodResource<>(cost));
                theMap.resetDistance();
                break;
            }
        }
        return null;
    }

    /**
     * helper function
     * @return the minimum cost of moving between to territories
     */
    private int findMinCost(Territory<T> source, Territory<T> dest){
        //if the two territories are adjacent, minimum cost is the sum of their size
        if(source.checkNeigh(dest)) return source.getSize()+dest.getSize();
        //if now adjacent, perform bfs search
        Queue<Territory<T>> pq = new PriorityQueue<>(cmp);
        source.setDistance(source.getSize());
        pq.add(source);
        while (!pq.isEmpty()){
            Territory<T> curr = pq.poll();
            int curr_dis = curr.getDistance();
            if(curr.getName().equals(dest.getName())) return curr_dis;
            for (Territory<T> neigh: curr.getMyNeigh()){
                if(neigh.getDistance()!=100) continue;
                neigh.setDistance(neigh.getSize()+curr_dis);
                pq.add(neigh);
            }
        }
        //if it can not find a path, return -1
        //but should not happen, because we check path first
        return -1;
    }

    /**
     * helper function to move units
     * @return the minimum cost if it is a move order
     * can not use the return value if it is for enemy move
     */
    private int moveUnits(ActionParser parser,Territory<T> source, Collection<Territory<T> > toFind,
                           int toMove, int level){
        for(Territory<T> dest: toFind){
            if(dest.getName().toUpperCase().equals(parser.getDest())){
                int cost = 1;
                if(moveToSamePlayer) cost = findMinCost(source,dest);
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
