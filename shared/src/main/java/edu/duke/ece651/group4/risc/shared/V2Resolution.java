package edu.duke.ece651.group4.risc.shared;

public class V2Resolution<T> extends CombatResolution<T> {
    public V2Resolution(Territory<T> dest){
        super(dest);
    }

    @Override
    public boolean resolveCombat(){
        //TODO: resolution

        return true;
    }
}
