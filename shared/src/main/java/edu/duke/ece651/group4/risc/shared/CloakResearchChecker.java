package edu.duke.ece651.group4.risc.shared;

public class CloakResearchChecker<T> extends ActionRuleChecker<T> {

    public CloakResearchChecker(ActionRuleChecker<T> next) {super(next);}

    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p) {
        if (!p.cloakIsResearch()) {
            return "That action is invalid: Cloaking is not being researched";
        }
        return null;
    }

}
