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

    private void resolveHelper(Territory<T> source, Territory<T> dest){
        CombatResolution<T> resolve = new V1SimpleResolution<>(source,dest);
        //if attacker wins, change both player's own territory
        if(resolve.resolveCombat()){
            thePlayer.addToTerritory(dest);
            theMap.findPlayer(dest).removeFromTerritory(dest);
            for(int i=0; i<dest.getEnemyUnitNum(); i++){
                dest.addUnit(new SimpleUnit<>());
                dest.removeEnemyUnit(new SimpleUnit<>());
            }
        }
        //attacker loses, ownership doesn't change, nothing changed
    }

    @Override
    public String doAction(){
        if(checkRule()!=null){
            return checkRule();
        }
        for (Territory<T> source: thePlayer.getMyTerritories()){
            if(source.getName().toUpperCase().equals(parser.getSource())){
                for (Territory<T> dest: theMap.getMyTerritories()){
                    if(dest.getName().toUpperCase().equals(parser.getDest())){
                        //find the source and dest territories
                        resolveHelper(source,dest);
                        break;
                    }
                }
                break;
            }
        }
        return null;
    }
}
