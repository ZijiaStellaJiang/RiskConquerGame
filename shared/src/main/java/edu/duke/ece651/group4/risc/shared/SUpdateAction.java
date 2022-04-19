package edu.duke.ece651.group4.risc.shared;

public class SUpdateAction<T> extends Action<T> {
    public SUpdateAction() {
        super(new SUpdateNumChecker<T>(new SUpdateResourceChecker<T>(null)));
    }


    /**
     * this function will do all the update spy action for ONE player
     * server should apply this function for each player inthe game
     * @param parser: the order which this update action is going to perform.
     * @param theMap: the whole map.
     * @param thePlayer: the player who issue this update action.
     * @return null if action success
     * @return error message if action is invalid
     * */
    @Override
    public String doAction(ActionParser parser, Map<T> map, Player<T> thePlayer) {
        String checkMyRule = ruleChecker.checkActionRule(parser, map, thePlayer);
        if (checkMyRule != null) return checkMyRule;
        // find the source territory
        for(Territory<T> source: thePlayer.getMyTerritories()){
            if(source.getName().toUpperCase().equals(parser.getSource())){
                int updateNum = parser.getUnit();
                updateUnits(source, thePlayer, updateNum, 0);
                break;
            }
          }
        return null;
    }

    private void updateUnits(Territory<T> territory, Player<T> player, int updateNum, int curLevel) {
        for (int i = 0; i < updateNum; i++) {
            territory.removeMyUnit(new SimpleUnit<T>(0));
            territory.addMySpy();
        }
        player.consumeResource(new WoodResource<>(updateNum * 20));
    }

}
