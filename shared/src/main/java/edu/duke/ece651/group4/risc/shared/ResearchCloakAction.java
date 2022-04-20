package edu.duke.ece651.group4.risc.shared;

public class ResearchCloakAction<T> extends Action<T> {
    public ResearchCloakAction() {
        super(new ResearchCloakResourceChecker<T>(null));
    }
    /**
     * this function will research cloaking tech for ONE player
     * server should apply this function for each player in the game
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
        thePlayer.consumeResource(new WoodResource<>(100));
        thePlayer.researchClock();
        return null;
    }
}
