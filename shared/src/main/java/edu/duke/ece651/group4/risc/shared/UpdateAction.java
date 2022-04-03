package edu.duke.ece651.group4.risc.shared;

public class UpdateAction<T> extends Action<T>{
  public UpdateAction() {
    super(new UpdateMaxLevelChecker<T>(new UpdateNumChecker<T>(new UpdateResourceChecker<T>(null))));
  }



  /**
   * this function will do all the update action for ONE player
   * server should apply this function for each player inthe game
   * @param parser: the order which this update action is going to perform.
   * @param theMap: the whole map.
   * @param thePlayer: the player who issue this update action.
   * @return null if action success
   * @return error message if action is invalid
   * */

  @Override
  public String doAction(ActionParser parser, Map<T> theMap, Player<T> thePlayer) {
    // TODO Auto-generated method stub
    // chceck Rule First
    String checkMyRule = ruleChecker.checkActionRule(parser, theMap, thePlayer);
    if (checkMyRule != null) return checkMyRule;
    // find the Sorcee territory
    for(Territory<T> source: thePlayer.getMyTerritories()){
      if(source.getName().toUpperCase().equals(parser.getSource())){
          int updateNum = parser.getUnit();
          int curLevel = parser.getLevel();
          int levelUp = parser.getLevelUp();
          for (int i = curLevel; i < curLevel + levelUp; i++) {
            updateUnits(source, thePlayer, updateNum, i);
          }
          break;
      }
    }
    return null;
  }

  /**
   * helper function to update units
   */
  private void updateUnits(Territory<T> territory, Player<T> player, int updateNum, int curLevel) {
    for (int i = 0; i < updateNum; i++) {
      territory.removeMyUnit(new SimpleUnit<T>(curLevel));
      territory.addMyUnit(new SimpleUnit<T>(curLevel + 1));
      player.consumeResource(new WoodResource<T>(1));
    }
  }
}



