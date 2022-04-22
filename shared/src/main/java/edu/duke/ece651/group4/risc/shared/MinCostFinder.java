package edu.duke.ece651.group4.risc.shared;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MinCostFinder<T> {
    //define the comparator of priority queue in minimum path
    Comparator<Territory<T>> cmp = new Comparator<Territory<T>>() {
        @Override
        public int compare(Territory<T> o1, Territory<T> o2) {
            return o1.getDistance()-o2.getDistance();
        }
    };

    /**
     * @return the minimum cost of moving between two territories
     */
    public int findMinCost(Territory<T> source, Territory<T> dest, Player<T> player){
        //if the two territories are adjacent, minimum cost is the sum of their size
        if(source.checkNeigh(dest)) return source.getSize()+dest.getSize();
        //if not adjacent, perform bfs search
        Queue<Territory<T>> pq = new PriorityQueue<>(cmp);
        source.setDistance(source.getSize());
        pq.add(source);
        while (!pq.isEmpty()){
            Territory<T> curr = pq.poll();
            int curr_dis = curr.getDistance();
            if(curr.getName().equals(dest.getName())) return curr_dis;
            for (Territory<T> neigh: curr.getMyNeigh()){
                if(!player.checkMyTerritory(neigh)) continue;
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
     * helper function for move cost
     * @param findAdjacent: find all direct adjacent territories which is not the same player as
     * @param endPoint: calculate distance between endPoint and neigh of findAdjacent
     * @param player: the player
     * @return the minCost
     */
    public int costForMoveBetweenDifferentPlayer (Territory<T> findAdjacent, Territory<T> endPoint,
                                                   Player<T> player) {
        int minCost = 300;
        MinCostFinder<T> finder = new MinCostFinder<>();
        for(Territory<T> neigh: findAdjacent.getMyNeigh()) {
            if(!player.checkMyTerritory(neigh)) continue;
            int cost = finder.findMinCost(neigh,endPoint,player);
            if(cost<minCost) minCost = cost;
        }
        if(minCost==300) return -1;
        return minCost + findAdjacent.getSize();
    }
}
