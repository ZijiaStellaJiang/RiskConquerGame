package edu.duke.ece651.group4.risc.shared;

public abstract class ActionRuleChecker<T> implements java.io.Serializable {
    private final ActionRuleChecker<T> next;

    public ActionRuleChecker(ActionRuleChecker<T> next){
        this.next = next;
    }
    /**
     *@return null if placement valid for certain rule
     *return the string that indicate the invalid message otherwise
     */
    protected abstract String checkMyRule(ActionParser parse, Map<T> map, Player<T> p);
    public String checkActionRule(ActionParser parse, Map<T> map, Player<T> p) {
        //if we fail our own rule, stop the placement is not legal,return the description message
        if(checkMyRule(parse, map,p)!=null) {
            return checkMyRule(parse, map,p);
        }
        //otherwise, ask the rest of the chain
        if(next != null) {
            return next.checkActionRule(parse, map, p);
        }
        //if there are no more rules, then the placement is legal
        return null;
    }


}
