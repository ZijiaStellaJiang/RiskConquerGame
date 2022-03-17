package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;

/**
 * This abstract class decides rule to resolve combat between territories
 */
public abstract class CombatResolution<T> implements java.io.Serializable {
    protected Territory<T> defend;

    public CombatResolution(Territory<T> defend){
        this.defend = defend;
    }

    /**
     * resolve the combat between attack side and defend side
     * return true if attacker successes
     * return false if attacker failed to take ownership of the defender's territory
     */
    public abstract boolean resolveCombat();
}
