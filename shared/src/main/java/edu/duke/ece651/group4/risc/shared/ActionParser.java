package edu.duke.ece651.group4.risc.shared;
/**
 * This class is used to parse user input like move source destination #ofunits
 */
public class ActionParser implements java.io.Serializable{
  String type;// type refers to move or attack
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

  public ActionParser(String type, String source, String dest, int num, int level) {
    this(type, source, dest, num, level, 1);
  }


  public ActionParser(String type, String source, String dest, int num, int level, int levelUp) {
    String type_upper = type.toUpperCase();
    if(!type_upper.equals("MOVE") && !type_upper.equals("ATTACK") && !type_upper.equals("UPDATE")){
      throw new IllegalArgumentException("type needs to be move or attack or update");
    }
    this.type = type_upper;
    this.source_name = source.toUpperCase();
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
}
