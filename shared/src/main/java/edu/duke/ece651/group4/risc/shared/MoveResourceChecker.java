package edu.duke.ece651.group4.risc.shared;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MoveResourceChecker<T> extends ActionRuleChecker<T> {

    public MoveResourceChecker(ActionRuleChecker<T> next){
        super(next);
    }

    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p){
        MinCostFinder<T> finder = new MinCostFinder<>();
        for (Territory<T> source: p.getMyTerritories()){
            if(source.getName().toUpperCase().equals(parse.getSource())){
                for(Territory<T> dest:p.getMyTerritories()){
                    if(dest.getName().toUpperCase().equals(parse.getDest())){
                        //find the source and dest territories
                        int cost = finder.findMinCost(source,dest);
                        map.resetDistance();
                        if(p.getFoodNum()>=cost*parse.getUnit()) return null;
                        break;
                    }
                }
                break;
            }
        }
        return "That action is invalid: player doesn't have enough food to move.";
    }

//    private int findMinCost(Territory<T> source, Territory<T> dest){
//        //if the two territories are adjacent, minimum cost is the sum of their size
//        if(source.checkNeigh(dest)) return source.getSize()+dest.getSize();
//        //if now adjacent, perform bfs search
//        Queue<Territory<T>> pq = new PriorityQueue<>(cmp);
//        source.setDistance(source.getSize());
//        pq.add(source);
//        while (!pq.isEmpty()){
//            Territory<T> curr = pq.poll();
//            int curr_dis = curr.getDistance();
//            if(curr.getName().equals(dest.getName())) return curr_dis;
//            for (Territory<T> neigh: curr.getMyNeigh()){
//                if(neigh.getDistance()!=100) continue;
//                neigh.setDistance(neigh.getSize()+curr_dis);
//                pq.add(neigh);
//            }
//        }
//        //if it can not find a path, return -1
//        //but should not happen, because we check path first
//        return -1;
//    }
}
