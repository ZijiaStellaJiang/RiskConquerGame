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
     * with the players own their territories and units, as well as their neighbors
     */
    public void displayCurrentMap();

    /**
     * display the combat result of last round to the player
     * then display the player_id's color(name) message and action instruction
     */
    public void displayPlayerMsg(int id);

    /**
     * IF there is a player wins the game, display the winner message to the clients.
     */
    public void displayVictoryMsg(int id);
}
