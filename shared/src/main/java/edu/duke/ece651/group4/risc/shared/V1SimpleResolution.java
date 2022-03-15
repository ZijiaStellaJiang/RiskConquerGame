package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;
import java.util.Random;

public class V1SimpleResolution<T> extends CombatResolution<T> {
    public V1SimpleResolution(Territory<T> attack, Territory<T> defend, ArrayList<Unit<T>> attackUnits){
        super(attack, defend, attackUnits);
    }

    public void resolveCombat(){
        int attackNum = attackUnits.size();
        int defendNum = defend.getUnitNumber();
        while(true){
            if(attackNum==0){
                //TODO: mark defender wins
                break;
            }
            if(defendNum==0){
                //TODO: mark attacker wins
                break;
            }
            int attackRoll = new Random().nextInt(20);
            int defendRoll = new Random().nextInt(20);
            if(attackRoll>defendRoll){
                defendNum--;
            }
            else {
                attackNum--;
            }
        }
        //TODO: return winner information;
        return;
    }
}
