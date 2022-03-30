package edu.duke.ece651.group4.risc.shared;

public class UpdateNumChecker<T> extends ActionRuleChecker<T>  {
    public UpdateNumChecker(ActionRuleChecker<T> next) {super(next);}

    /**
     * This rule ensures there are enough unit number in given level
     */
    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p){
        int level = parse.getLevel();
        int num = parse.getLevel();
        Unit<T> unit = new SimpleUnit<T>(level);
        int consume = unit.updateCost() * num;
        int resource = p.getFoodNum();
        if (consume > resource) {
            return "That action is invalid: the number of certain level's unit is not enough";
        }
        return null;
    }
}
