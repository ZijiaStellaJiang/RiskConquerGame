package edu.duke.ece651.group4.risc.shared;

public class CloakAction<T> extends Action<T> {
    public CloakAction() {
        super(new CloakResourceChecker<T>(null));
    }


    /**
     * this function will do all the update spy action for ONE player
     * server should apply this function for each player inthe game
     * @param parser: the order which this update action is going to perform.
     * @param map: the whole map.
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
                source.cloakrenewCount(3);
                break;
            }
        }
        thePlayer.consumeResource(new WoodResource<>(20));
        return null;
    }
}
