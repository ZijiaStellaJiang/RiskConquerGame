package edu.duke.ece651.group4.risc.shared;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

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
    assertEquals(false, player.checkMyTerritory(territory));
    assertEquals(false, player.checkMyTerritory(territory1)); 
    player.addToTerritory(territory);
    player.removeFromTerritory(territory1);
    assertEquals(true, player.checkMyTerritory(territory));
    assertEquals(false, player.checkMyTerritory(territory1));   
    //player.addToTerritory(territory1);
    player.removeFromTerritory(territory);
    assertEquals(false, player.checkMyTerritory(territory));
    assertEquals(false, player.checkMyTerritory(territory1));
  }


  @Test
  public void test_equals(){
    Player player = new Player("test_player");
    Player player1 = new Player("test_player1");
    assertEquals(false, player1.equals(player));
    assertEquals(false, player1.equals(1));
    assertEquals(false, player1.equals("test_player1"));
    assertEquals(true, player1.equals(player1));
    Player player2 = new Player("test_player1");   
    assertEquals(true, player1.equals(player2));
  }
  @Test
  public void test_get_my_territory(){
    Player p1 = new Player("A");
    Territory<Character> t1 = new Territory<Character>("A1");
    Territory<Character> t2 = new Territory<Character>("A2");
    p1.addToTerritory(t1);
    p1.addToTerritory(t2);
    ArrayList<Territory<Character>> myTerri = new ArrayList<>();
    myTerri.add(t1);
    myTerri.add(t2);
    assertEquals(myTerri,p1.getMyTerritories());
  }

}
