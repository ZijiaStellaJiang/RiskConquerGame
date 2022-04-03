package edu.duke.ece651.group4.risc.shared;

import java.util.Random;

public class V1SimpleResolution<T> extends CombatResolution<T> {
    String seed;

    public V1SimpleResolution(Territory<T> defend,String seed){
        super(defend);
        this.seed = seed;
    }

    /**
     * This resolution decides combat results using two 20-sided dices for both sides
     * return true is attack wins, false otherwise
     */
    public boolean resolveCombat(){
        int attackNum = defend.getEnemyUnitNum();
        int defendNum = defend.getUnitNumber();
        int turnNum = 1;   //decide whether this turn to use "MaxAttack VS MinDefend" or inverse.
        while(true){
            if(attackNum==0){
                return false;
            }
            if(defendNum==0){
                return true;
            }
            int attackRoll = new Random().nextInt(20);
            int defendRoll = new Random().nextInt(20);
            if(seed!=null){
                long seed_num = Integer.parseInt(seed);
                attackRoll = new Random(seed_num).nextInt(20);
                defendRoll = new Random(1).nextInt(20); //this seed generate 5
            }
            Unit<T> attacker = new SimpleUnit<>(defend.getEnemyMaxUnit());
            Unit<T> defender = new SimpleUnit<>(defend.getMyMinUnit());
            // if this is the even number turn, then MinAttacker VS MaxDefender
            if(turnNum%2 == 0) {
                attacker = new SimpleUnit<>(defend.getEnemyMinUnit());
                defender = new SimpleUnit<>(defend.getMyMaxUnit());
            }
            attackRoll = attackRoll + attacker.getBonus();
            defendRoll = defendRoll + defender.getBonus();
            if(attackRoll>defendRoll){
                defendNum--;
                defend.removeMyUnit(defender);
            }
            else {
                attackNum--;
                defend.removeEnemyUnit(attacker);
            }
            turnNum++;
        }
    }
}
