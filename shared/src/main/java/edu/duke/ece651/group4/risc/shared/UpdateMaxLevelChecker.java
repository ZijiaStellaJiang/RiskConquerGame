package edu.duke.ece651.group4.risc.shared;

public class UpdateMaxLevelChecker<T> extends ActionRuleChecker<T> {
    public UpdateMaxLevelChecker(ActionRuleChecker<T> next) {super(next);}

    /**
     * This rule ensures the unit can be update
     */
    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p){
        int level = parse.getLevel();
        Unit<T> unit = new SimpleUnit<T>(0);
        int maxLevel = unit.getMaxLevel();
        int levelUp = unit.getLevelUp();
        if (level + levelUp > maxLevel) {
            return "That action is invalid: the unit already achieved max level.";
        }
        return null;
    }
}
