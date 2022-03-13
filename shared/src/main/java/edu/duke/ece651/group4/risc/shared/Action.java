package edu.duke.ece651.group4.risc.shared;

public abstract class Action<T> {
    protected final ActionParser parser;
    protected final Map<T> theMap;
    protected final Player<T> thePlayer;
    protected ActionRuleChecker<T> ruleChecker;

    public Action(ActionParser parser, Map<T> map, Player<T> player, ActionRuleChecker<T> ruleChecker){
        this.parser = parser;
        this.theMap = map;
        this.thePlayer = player;
        this.ruleChecker = ruleChecker;
    }
//    public Action(ActionParser parser, Map<T> map, Player<T> player){
//        this(parser,map,player, new UnitNumberRuleChecker<>(null));
//    }
    public String checkRule(){
        return ruleChecker.checkActionRule(parser,theMap,thePlayer);
    }
    public abstract String doAction();
}
