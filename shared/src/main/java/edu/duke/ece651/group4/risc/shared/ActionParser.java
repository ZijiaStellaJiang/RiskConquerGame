package edu.duke.ece651.group4.risc.shared;

/**
 * This class is used to parse user input like move source destination #ofunits
 */
public class ActionParser implements java.io.Serializable{
  String type;// type refers to move or attack or update
  String source_name;
  String dest_name;
  int numofUnit;
  int levelofUnit;
  int levelUp;

  /**
   * Constructor
   * @param type move or attack
   * @param source source territory
   * @param dest destination territory
   * @param num number of unit
   */
  public ActionParser(String type, String source, String dest, int num) {
    this(type, source, dest, num, 0, 0);
  }

  /**
   * Used for move/attack in project 2
   * @param type
   * @param source
   * @param dest
   * @param num
   * @param level
   */
  public ActionParser(String type, String source, String dest, int num, int level) {
    this(type, source, dest, num, level, 1);
  }

  /**
   * Used for upgrade in project 2
   * @param type
   * @param source
   * @param dest
   * @param num
   * @param level
   * @param levelUp
   */
  public ActionParser(String type, String source, String dest, int num, int level, int levelUp) {
    String upper = type.toUpperCase();
    if(!upper.equals("MOVE") && !upper.equals("ATTACK") && !upper.equals("UPDATE") 
      && !upper.equals("SUPDATE") && !upper.equals("SMOVE") && !upper.equals("CLOAK")
      && !upper.equals("RCLOAK")){
      throw new IllegalArgumentException("type needs to be move or attack or update");
    }
    this.type = upper;
    this.source_name = upper.equals("RCLOAK") ? null : source.toUpperCase();
    this.dest_name = dest == null ? null : dest.toUpperCase();
    this.numofUnit = num;
    this.levelofUnit = level;
    this.levelUp = levelUp;
  }
  /**
   * Constructor
   * @param input format type source destination num
   */
  public ActionParser(String input) {
    String upperString = input.toUpperCase();
    String[] res = upperString.split("\\s+");// split input according to blank space
    if(res.length!=4 && res.length!=5){
      throw new IllegalArgumentException("incorrect input");
    }
    if(!res[0].equals("MOVE") && !res[0].equals("ATTACK") && !res[0].equals("UPDATE")){
      throw new IllegalArgumentException("type needs to be move or attack");
    }
    this.type = res[0];
    this.source_name = res[1];
    this.dest_name = res[2];
    this.numofUnit = Integer.valueOf(res[3]).intValue();
    this.levelofUnit = res.length == 4 ? 0 : Integer.valueOf(res[4]).intValue();
    this.levelUp = 1;
  }

  /**
   * get type of one action
   * @return type
   */
  public String getType() {
    return this.type;
  }

  /**
   * get source of an action
   * @return source territory
   */
  public String getSource() {
    return source_name;
  }

  /**
   * get destination of an action
   * @return destination of an action
   */
  public String getDest() {
    return dest_name;
  }

  /**
   * get number of unit of one territory
   * @return get number of unit
   */
  public int getUnit() {
    return numofUnit;
  }

  /**
   * get number of unit of one territory
   * @return get number of unit
   */
  public int getLevel() {
    return levelofUnit;
  }

  /**
   * get number of unit of one territory
   * @return get number of unit
   */
  public int getLevelUp() {
    return levelUp;
  }

  /**
   * get the cost of this parser
   */
  public String getCost(Map<Character> theMap){
    Territory<Character> source = theMap.getTerritory(source_name);
    Territory<Character> dest = theMap.getTerritory(dest_name);
    Player<Character> player = theMap.findPlayer(source);
    if (type.equals("MOVE")) {
      MinCostFinder<Character> finder = new MinCostFinder<>();
      int cost = finder.findMinCost(source, dest, player);
      theMap.resetDistance();
      return "food: " + cost * numofUnit;
    }
    else if (type.equals("ATTACK")) {
      int cost = 1;
      return "food: " + cost * numofUnit;
    }
    else if (type.equals("UPDATE")) {
      int cost = 0;
      for (int i = levelofUnit; i < levelofUnit + levelUp; i++) {
        Unit<Character> unit = new SimpleUnit<>(i);
        cost = cost + unit.updateCost();
      }
      return "wood: " + cost * numofUnit;
    }
    else if (type.equals("SUPDATE")) {
      int cost = 20;
      return "wood: " + cost * numofUnit;
    }
    else if (type.equals("SMOVE")) {
      boolean moveToSame = player.checkMyTerritory(source) && player.checkMyTerritory(dest);
      int cost;
      MinCostFinder<Character> finder = new MinCostFinder<>();
      if (source.checkNeigh(dest)) cost = source.getSize() + dest.getSize();
      else if (moveToSame) cost = finder.findMinCost(source, dest, player);
      else {
        //from mine to enemy
        cost = finder.costForMoveBetweenDifferentPlayer(dest, source, player);
      }
      theMap.resetDistance();
      if(cost==-1) return "food: "+cost;
      return "food: " + cost * numofUnit;
    }
    else if (type.equals("CLOAK")) {
      int cost = 20;
      return "wood: " + cost;
    }
    else {
      int cost = 100;
      return "wood: " + cost;
    }
  }
}
