package edu.duke.ece651.group4.risc.shared;

import java.util.ArrayList;

public class TextPlayer extends Player<Character> {
    public TextPlayer(String name, int foodInit, int woodInit){
        super(name, foodInit, woodInit);
    }
    public TextPlayer(String name){
        this(name,0,0);
    }
}
