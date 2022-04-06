package edu.duke.ece651.group4.risc.shared;

public class AttackAction<T> extends Action<T> {
    String seed;

    public AttackAction(){
        super(new UnitNumberRuleChecker<>(new AttackOwnershipChecker<>(
                new AttackPathChecker<>(new AttackResourceChecker<>(null)))));
        this.seed=null;
    }

    /** test constructor only, DO NOT use in server and client */
    public AttackAction(String seed){
        this();
        this.seed=seed;
    }

    private void resolveHelper(Territory<T> dest,Map<T> theMap,Player<T> thePlayer){
        CombatResolution<T> resolve = new V1SimpleResolution<>(dest,seed);
        //if attacker wins, change both player's own territory
        if(resolve.resolveCombat()){
            Player<T> preOwner = theMap.findPlayer(dest);
            preOwner.removeFromTerritory(dest);
            preOwner.addLoseTerritory(dest.getName());
            thePlayer.addToTerritory(dest);
            thePlayer.addWinTerritory(dest.getName());
            int remain = dest.getEnemyUnitNum();
            for(int i=0; i<remain; i++){
                int minLevel = dest.getEnemyMinUnit();
                dest.addMyUnit(new SimpleUnit<>(minLevel));
                dest.removeEnemyUnit(new SimpleUnit<>(minLevel));
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
     * @return null
     */
    @Override
    public String doAction(ActionParser parser,Map<T> theMap, Player<T> thePlayer){
        for (Territory<T> toResolve: theMap.getMyTerritories()){
            if(!thePlayer.checkMyTerritory(toResolve)){
                resolveHelper(toResolve,theMap,thePlayer);
            }
        }
        return null;
    }
}
