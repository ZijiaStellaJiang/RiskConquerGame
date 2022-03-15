package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;
import java.util.Random;

public class V1SimpleResolution<T> extends CombatResolution<T> {
    public V1SimpleResolution(Territory<T> attack, Territory<T> defend){
        super(attack, defend);
    }

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
            if(attackRoll>defendRoll){
                defendNum--;
                defend.removeUnit(new SimpleUnit<>());
            }
            else {
                attackNum--;
                defend.removeEnemyUnit(new SimpleUnit<>());
            }
        }
    }
}
