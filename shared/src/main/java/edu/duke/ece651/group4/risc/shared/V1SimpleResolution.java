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
            if(attackRoll>defendRoll){
                defendNum--;
                defend.removeMyUnit(new SimpleUnit<>());
            }
            else {
                attackNum--;
                defend.removeEnemyUnit(new SimpleUnit<>());
            }
        }
    }
}
