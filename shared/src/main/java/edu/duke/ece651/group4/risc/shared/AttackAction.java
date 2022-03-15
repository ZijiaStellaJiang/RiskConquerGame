package edu.duke.ece651.group4.risc.shared;

public class AttackAction<T> extends Action<T> {
    public AttackAction(ActionParser parser, Map<T> map, Player<T> player){
        super(parser,map,player,new UnitNumberRuleChecker<>(
                new AttackOwnershipChecker<>(new AttackPathChecker<>(null))));
    }

    /** test constructor */
//    public AttackAction(ActionParser parser, Map<T> map, Player<T> player, ActionRuleChecker<T> ruleChecker){
//        super(parser, map, player, ruleChecker);
//    }

    @Override
    public String doAction(){
        if(checkRule()!=null){
            return checkRule();
        }
        for (Territory<T> source: thePlayer.getMyTerritories()){
            if(source.getName().toUpperCase().equals(parser.getSource())){
                int attack_num = parser.getUnit();
                for (Territory<T> dest: theMap.getMyTerritories()){
                    if(dest.getName().toUpperCase().equals(parser.getDest())){
                        /** find the source and dest territories and the attack number */
                    }
                }
            }
        }
        return null;
    }
}
