package edu.duke.ece651.group4.risc.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ActionParserTest {
  @Test
  public void test_normal_constructor() {
    ActionParser parser = new ActionParser("move", "a", "test", 3);
    assertEquals("MOVE", parser.getType());
    assertEquals("A", parser.getSource());
    assertEquals("TEST", parser.getDest());
    assertEquals(3, parser.getUnit());

    //test with invalid case
    assertThrows(IllegalArgumentException.class, () -> new ActionParser("invalid", "a", "123", 3));
  }


  @Test
  public void test_string_constructor(){
    ActionParser parser = new ActionParser("move a test 3");
    assertEquals("MOVE", parser.getType());
    assertEquals("A", parser.getSource());
    assertEquals("TEST", parser.getDest());
    assertEquals(3, parser.getUnit());

    //test with error case
    assertThrows(IllegalArgumentException.class, () -> new ActionParser("a s v"));
    assertThrows(IllegalArgumentException.class, () -> new ActionParser("move a b 2 3 3"));
    assertThrows(IllegalArgumentException.class, () -> new ActionParser("move1 a test 3"));
  }

}
