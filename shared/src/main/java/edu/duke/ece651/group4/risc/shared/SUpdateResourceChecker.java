package edu.duke.ece651.group4.risc.shared;

public class SUpdateResourceChecker<T> extends ActionRuleChecker<T>  {
    public SUpdateResourceChecker(ActionRuleChecker<T> next) {super(next);}

    /**
     * This rule ensures the player has enough resources
     */
    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p) {
        int num = parse.getUnit();
        int consume = 20 * num;
        int resource = p.getWoodNum();
        if (consume > resource) {
            return "That action is invalid: The technology resources are not enough for updating spy.";
        }
        return null;
    }

}
