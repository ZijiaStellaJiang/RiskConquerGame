package edu.duke.ece651.group4.risc.shared;

/**
 * This class is used to parse user input like move source destination #ofunits
 */
public class ActionParser implements java.io.Serializable{
  String type;// type refers to move or attack
  String source_name;
  String dest_name;
  int numofUnit;

  /**
   * Constructor
   * @param type move or attack
   * @param source source territory
   * @param dest destination territory
   * @param num number of unit
   */
  public ActionParser(String type, String source, String dest, int num) {
    String type_upper = type.toUpperCase();
    if(!type_upper.equals("MOVE") && !type_upper.equals("ATTACK")){
      throw new IllegalArgumentException("type needs to be move or attack");
    }
    this.type = type_upper;
    this.source_name = source.toUpperCase();
    this.dest_name = dest.toUpperCase();
    this.numofUnit = num;
  }

  /**
   * Constructor
   * @param input format type source destincation num
   */
  public ActionParser(String input) {
    String upperString = input.toUpperCase();
    String[] res = upperString.split("\\s+");// split input according to blank space
    if(res.length!=4){
      throw new IllegalArgumentException("incorrect input");
    }
    if(!res[0].equals("MOVE") && !res[0].equals("ATTACK")){
      throw new IllegalArgumentException("type needs to be move or attack");
    }
    this.type = res[0];
    this.source_name = res[1];
    this.dest_name = res[2];
    this.numofUnit = Integer.valueOf(res[3]).intValue();
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
}
