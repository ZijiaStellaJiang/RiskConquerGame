package edu.duke.ece651.group4.risc.server;

import edu.duke.ece651.group4.risc.shared.*;

public abstract class Action<T> {
    protected final ActionParser parser;
    protected final Map<T> theMap;
    protected ActionRuleChecker<T> ruleChecker;

    public Action(ActionParser parser, Map<T> map, ActionRuleChecker<T> ruleChecker){
        this.parser = parser;
        this.theMap = map;
        this.ruleChecker = ruleChecker;
    }
    public Action(ActionParser parser, Map<T> map){
        this(parser,map,new UnitNumberRuleChecker<>(null));
    }
    public String checkRule(){
        return ruleChecker.checkActionRule(parser,theMap);
    }
    public abstract String doAction();
}
