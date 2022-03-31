package edu.duke.ece651.group4.risc.shared;

public class SimpleUnit<T> extends Unit<T>{
    private final static int max_level = 6;
    private final static int[] bonuses = new int[]{0, 1, 3, 5, 8, 11, 15};
    private final static int[] cost = new int[]{3, 8, 19, 25, 35, 50};
    public SimpleUnit (){
        super(0, 0);
    }

    public SimpleUnit(int level){
      super(level, bonuses[level]);
    }

    public int getMaxLevel() {
        return max_level;
    }
  
    /**
     * return cost if 0 <= level < 6
     * else return -1
     */
    public int updateCost() {
      return cost[level];
    }

    //TODO: update more than one level
    public boolean canUpdate() {
      if (level < max_level)
        return true;
      return false;
    }
  
    public boolean update() {
      if (canUpdate()) {
        level += 1;
        bonus = bonuses[level];
        return true;
      }
      return false;
    }
}
