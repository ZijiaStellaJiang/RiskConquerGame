package edu.duke.ece651.group4.risc.server;
import edu.duke.ece651.group4.risc.shared.*;

public interface AbstractMapFactory<T> {
    public Map<T> generateMap();
}

