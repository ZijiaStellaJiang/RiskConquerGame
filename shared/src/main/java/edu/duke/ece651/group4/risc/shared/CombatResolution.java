package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;

public abstract class CombatResolution<T> {
    protected Territory<T> attack;
    protected Territory<T> defend;
    protected ArrayList<Unit<T>> attackUnits;

    public CombatResolution(Territory<T> attack, Territory<T> defend, ArrayList<Unit<T>> attackUnits){
        this.attack = attack;
        this.defend = defend;
        this.attackUnits = attackUnits;
    }

    //public abstract void resolveCombat();
}
