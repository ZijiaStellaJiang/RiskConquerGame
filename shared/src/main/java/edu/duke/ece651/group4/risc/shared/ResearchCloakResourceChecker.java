package edu.duke.ece651.group4.risc.shared;

public class ResearchCloakResourceChecker<T> extends ActionRuleChecker<T>  {

    public ResearchCloakResourceChecker(ActionRuleChecker<T> next) {super(next);}

    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p) {
        int resource = p.getWoodNum();
        if (100 > resource) {
            return "That action is invalid: The technology resources are not enough for researching cloaking.";
        }
        return null;
    }

}
