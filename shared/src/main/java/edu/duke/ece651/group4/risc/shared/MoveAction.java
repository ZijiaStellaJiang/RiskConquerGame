package edu.duke.ece651.group4.risc.shared;

import java.util.Collection;

public class MoveAction<T> extends Action<T>{
    boolean moveToSamePlayer;

    /**
     * @param samePlayer: indicate whether to move units into same player's territory
     *                  pass true if for real "move" order
     *                  pass false if this move is for attacker to move units into enemy's territory
     */
    public MoveAction(/*ActionParser parser, Map<T> map, Player<T> player, */boolean samePlayer){
        super(/*parser,map,player,*/new UnitNumberRuleChecker<>(new MoveOwnershipChecker<>(
                new MovePathChecker<>(null))));
        this.moveToSamePlayer = samePlayer;
    }

    /** for test constructor only, DO NOT use in server and client */
    public MoveAction(/*ActionParser parser, Map<T> map, Player<T> player, */ActionRuleChecker<T> ruleChecker,
                      boolean samePlayer){
        super(/*parser, map, player, */ruleChecker);
        this.moveToSamePlayer = samePlayer;
    }

    /**
     * helper function to move units
     */
    private void moveUnits(ActionParser parser,Territory<T> source, Collection<Territory<T> > toFind, int toMove){
        for(Territory<T> dest: toFind){
            if(dest.getName().toUpperCase().equals(parser.getDest())){
                for(int i=0; i<toMove; i++){
                    if(moveToSamePlayer){
                        dest.addUnit(new SimpleUnit<>());
                    }
                    else {
                        dest.addEnemyUnit(new SimpleUnit<>());
                    }
                    source.removeUnit(new SimpleUnit<>());
                }
                break;
            }
        }
    }

    @Override
    /**
     * this function do move for each move order (or attack move phase)
     * @param parser: the order which this move action is going to perform.
     * @param theMap: the whole map.
     * @param thePlayer: the player who issue this move action.
     * @return null if action success
     * @return error message if action is invalid
     */
    public String doAction(ActionParser parser,Map<T> theMap, Player<T> thePlayer){
        //if this move is used for attack, the rule checker should change
        if(!moveToSamePlayer){
            ruleChecker = new UnitNumberRuleChecker<>(new AttackOwnershipChecker<>(
                    new AttackPathChecker<>(null)));
        }
        String checkRule = ruleChecker.checkActionRule(parser,theMap,thePlayer);
        if(checkRule!=null){
            return checkRule;
        }
        for(Territory<T> source: thePlayer.getMyTerritories()){
            if(source.getName().toUpperCase().equals(parser.getSource())){
                int toMove = parser.getUnit();
                if(moveToSamePlayer){
                    moveUnits(parser,source,thePlayer.getMyTerritories(),toMove);
                }
                else {
                    moveUnits(parser,source,theMap.getMyTerritories(),toMove);
                }
                break;
            }
        }
        return null;
    }
}
