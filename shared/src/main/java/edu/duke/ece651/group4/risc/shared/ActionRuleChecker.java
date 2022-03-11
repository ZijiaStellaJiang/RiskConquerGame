package edu.duke.ece651.group4.risc.shared;

public abstract class ActionRuleChecker<T> {
    private final ActionRuleChecker<T> next;

    public ActionRuleChecker(ActionRuleChecker<T> next){
        this.next = next;
    }
    /**
     *@return null if placement valid for certain rule
     *return the string that indicate the invalid message otherwise
     */
    protected abstract String checkMyRule(ActionParser parse, Map<T> map);
    public String checkPlacement(ActionParser parse, Map<T> map) {
        //if we fail our own rule, stop the placement is not legal,return the description message
        if(checkMyRule(parse, map)!=null) {
            return checkMyRule(parse, map);
        }
        //otherwise, ask the rest of the chain
        if(next != null) {
            return next.checkPlacement(parse, map);
        }
        //if there are no more rules, then the placement is legal
        return null;
    }


}
