package edu.duke.ece651.group4.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlayerTest {
  @Test
  public void test_player_constructor() {
    Player player = new Player("test_player");
    assertEquals("test_player", player.getName());
  }

  @Test
  public void test_my_territory(){
    Player player = new Player("test_player");
    Territory<Character> territory = new Territory<Character>("test1");
    Territory<Character> territory1 = new Territory<Character>("test2");    

    
    player.addToTerritory(null);
  }

}
