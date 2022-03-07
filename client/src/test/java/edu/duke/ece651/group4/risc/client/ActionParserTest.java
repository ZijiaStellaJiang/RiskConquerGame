package edu.duke.ece651.group4.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ActionParserTest {
  @Test
  public void test_normal_constructor() {
    ActionParser parser = new ActionParser("move", "a", "test", 3);
    assertEquals("move", parser.getType());
    assertEquals("a", parser.getSource());
    assertEquals("test", parser.getDest());
    assertEquals(3, parser.getUnit());
  }


  @Test
  public void test_string_constructor(){
    ActionParser parser = new ActionParser("move a test 3");
    assertEquals("MOVE", parser.getType());
    assertEquals("A", parser.getSource());
    assertEquals("TEST", parser.getDest());
    assertEquals(3, parser.getUnit());
  }

}
