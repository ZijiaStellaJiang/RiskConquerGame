package edu.duke.ece651.group4.risc.shared;

public class ActionParser {
  String type;// type refers to move or attack
  String source_name;
  String dest_name;
  int numofUnit;

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
  //TODO: add error check
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

  String getType() {
    return this.type;
  }

  String getSource() {
    return source_name;
  }

  String getDest() {
    return dest_name;
  }

  int getUnit() {
    return numofUnit;
  }
}
