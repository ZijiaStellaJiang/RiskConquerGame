package edu.duke.ece651.group4.risc.shared;

public class AttackAction<T> extends Action<T> {
    public AttackAction(ActionParser parser, Map<T> map, Player<T> player){
        super(parser,map,player,new UnitNumberRuleChecker<>(null));
    }

    /** test constructor */
    public AttackAction(ActionParser parser, Map<T> map, Player<T> player, ActionRuleChecker<T> ruleChecker){
        super(parser, map, player, ruleChecker);
    }

    @Override
    public String doAction(){
        return null;
    }
}
