package edu.duke.ece651.group4.risc.server;

import edu.duke.ece651.group4.risc.shared.*;

public abstract class Action<T> {
    protected final ActionParser parser;
    protected final Map<T> theMap;

    public Action(ActionParser parser, Map<T> map){
        this.parser = parser;
        this.theMap = map;
    }
    public abstract void doAction();
}
