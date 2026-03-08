package com.example.yahtzy;

import java.util.Arrays;

public class Player {
    String Name;
    int[] points;
    int rolls;
    int[] dieStates;
    public Player(String name){
        // Yatzy table for player points
        points = new int[18];
        Arrays.fill(points, -1);
        rolls = 0;
        // Creating default die states
        dieStates = new int[6];
        // Name
        Name = name;
    }
    public void Roll(int[] newStates){
        rolls ++;
        dieStates = newStates;
    }
}
