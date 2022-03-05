package edu.duke.ece651.group4.risc.client;

/**
 *This interface can display any format of the game
 */
public interface View {
    /**
     * display the initial map with only territories
     * they have not been assigned to players yet
     */
    public void displayOriginMap();
    /**
     * display the current map
     */
    public void displayCurrentMap();
}
