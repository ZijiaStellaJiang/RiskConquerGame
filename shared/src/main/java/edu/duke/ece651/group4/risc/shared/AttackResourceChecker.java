package edu.duke.ece651.group4.risc.shared;

public class AttackResourceChecker<T> extends ActionRuleChecker<T> {
    public AttackResourceChecker(ActionRuleChecker<T> next){
        super(next);
    }

    @Override
    public String checkMyRule(ActionParser parse, Map<T> map, Player<T> p){
        int unitNum = parse.getUnit();
        int costPerUnit = 1;
        if(p.getFoodNum()>=unitNum*costPerUnit) return null;
        return "That action is invalid: player doesn't have enough food to attack.";
    }
}
