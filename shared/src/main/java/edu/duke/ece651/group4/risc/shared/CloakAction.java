package edu.duke.ece651.group4.risc.shared;

public class CloakAction<T> extends Action<T> {
    public CloakAction() {
        super(new UpdateMaxLevelChecker<T>(new UpdateNumChecker<T>(new UpdateResourceChecker<T>(null))));
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
    public String doAction(ActionParser parser, Map<T> map, Player<T> player) {
        // TODO Auto-generated method stub
        return null;
    }
}
