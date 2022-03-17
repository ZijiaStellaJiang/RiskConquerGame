package edu.duke.ece651.group4.risc.shared;

public abstract class Action<T> implements java.io.Serializable {
    protected ActionRuleChecker<T> ruleChecker;

    public Action(ActionRuleChecker<T> ruleChecker){
        this.ruleChecker = ruleChecker;
    }

    public abstract String doAction(ActionParser parser,Map<T> map, Player<T> player);
}
