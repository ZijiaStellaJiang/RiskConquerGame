package edu.duke.ece651.group4.risc.shared;

public class UpdateResourceChecker<T> extends ActionRuleChecker<T>  {
    public UpdateResourceChecker(ActionRuleChecker<T> next) {super(next);}

    /**
     * This rule ensures the player has enough resources
     */
    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p){
        int level = parse.getLevel();
        int levelUp = parse.getLevelUp();
        int num = parse.getUnit();
        Unit<T> unit = new SimpleUnit<T>(level);
        int consume = 0;
        for (int i = 0; i < levelUp; i++) {
            consume += unit.updateCost() * num;
            unit.update();
        }
        int resource = p.getWoodNum();
        if (consume > resource) {
            return "That action is invalid: The technology resources are not enough for this updating action.";
        }
        return null;
    }
}