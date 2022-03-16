package edu.duke.ece651.group4.risc.shared;

public class AttackAction<T> extends Action<T> {
    String seed;

    public AttackAction(/*ActionParser parser ,Map<T> map, Player<T> player*/){
        super(/*parser,map,player,*/new UnitNumberRuleChecker<>(
                new AttackOwnershipChecker<>(new AttackPathChecker<>(null))));
        this.seed=null;
    }

    /** test constructor only, DO NOT use in server and client */
    public AttackAction(/*ActionParser parser, Map<T> map, Player<T> player,*/ String seed){
        this(/*parser, map, player*/);
        this.seed=seed;
    }

    private void resolveHelper(Territory<T> dest,Map<T> theMap,Player<T> thePlayer){
        CombatResolution<T> resolve = new V1SimpleResolution<>(dest,seed);
        //if attacker wins, change both player's own territory
        if(resolve.resolveCombat()){
            theMap.findPlayer(dest).removeFromTerritory(dest);
            thePlayer.addToTerritory(dest);
            int remain = dest.getEnemyUnitNum();
            for(int i=0; i<remain; i++){
                dest.addUnit(new SimpleUnit<>());
                dest.removeEnemyUnit(new SimpleUnit<>());
            }
        }
        //attacker loses, ownership doesn't change, nothing changed
    }

    /**
     * this function will do all the attack action for ONE player
     * server should apply this function for each player in the game
     * @param parser: just pass null, useless in this function
     * @param theMap: the whole map
     * @param thePlayer: the player who stands the side of attacker
     * @return
     */
    @Override
    public String doAction(ActionParser parser,Map<T> theMap, Player<T> thePlayer){
        for (Territory<T> toResolve: theMap.getMyTerritories()){
            if(!thePlayer.checkMyTerritory(toResolve)){
                resolveHelper(toResolve,theMap,thePlayer);
            }
        }
//        for (Territory<T> source: thePlayer.getMyTerritories()){
//            if(source.getName().toUpperCase().equals(parser.getSource())){
//                for (Territory<T> dest: theMap.getMyTerritories()){
//                    if(dest.getName().toUpperCase().equals(parser.getDest())){
//                        //find the source and dest territories
//                        resolveHelper(source,dest,theMap,thePlayer);
//                        break;
//                    }
//                }
//                break;
//            }
//        }
        return null;
    }
}
