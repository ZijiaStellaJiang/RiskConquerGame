package edu.duke.ece651.group4.risc.shared;

public class UpdateResourceChecker<T> extends ActionRuleChecker<T>  {
    public UpdateResourceChecker(ActionRuleChecker<T> next) {super(next);}

    /**
     * This rule ensures the player has enough resources
     */
    @Override
    protected String checkMyRule(ActionParser parse, Map<T> map, Player<T> p){
        int level = parse.getLevel();
        int num = parse.getLevel();
        Integer ter_num = null;
        for(Territory<T> source : p.getMyTerritories()){
            if(source.getName().toUpperCase().equals(parse.getSource())){
                ter_num = source.getLevelUnitNum(level);
                break;
            }
        }
        if (ter_num == null || ter_num < num) {
            return "That action is invalid: The technology resources are not enough for this updating action";
        }
        return null;
    }
}