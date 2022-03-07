package edu.duke.ece651.group4.risc.client;

public class ActionParser {
  String type;// type refers to move or attack
  String source_name;
  String dest_name;
  int numofUnit;

  public ActionParser(String type, String source, String dest, int num) {
    this.type = type;
    this.source_name = source;
    this.dest_name = dest;
    this.numofUnit = num;
  }
  //TODO: add error check
  public ActionParser(String input) {
    String upperString = input.toUpperCase();
    String[] res = upperString.split("\\s+");// split input according to blank space
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
