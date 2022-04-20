package edu.duke.ece651.group4.risc.shared;

public class CloakResourceChecker<T> extends ActionRuleChecker<T> {
    public CloakResourceChecker(ActionRuleChecker<T> next) {super(next);}

    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p) {
        int resource = p.getWoodNum();
        if (20 > resource) {
            return "That action is invalid: The technology resources are not enough for cloaking.";
        }
        return null;
    }
}
