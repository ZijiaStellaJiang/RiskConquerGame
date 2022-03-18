package edu.duke.ece651.group4.risc.server;
import edu.duke.ece651.group4.risc.shared.*;

/**
 * This interface is used to generate the original setting of map in all the versions
 * This will confirm the original territories units and players
 */
public interface AbstractMapFactory<T> {
    public Map<T> generateMap();
}

