package com.example.yahtzy;

import java.util.Arrays;

public class Player {
    private String name;
    private int[] points;
    private int rolls;
    private int[] dieStates;
    private boolean hasBonus;
    public Player(String name){
        // Yatzy table for player points
        this.points = new int[14];
        Arrays.fill(points, -1);
        this.rolls = 0;
        // Creating default die states
        this.dieStates = new int[6];
        //dieStates[0] = 1; dieStates[1] = 1; dieStates[2] = 1; dieStates[3] = 6; dieStates[4] = 6;
        // Name
        this.name = name;
        this.hasBonus = false;
    }
    public void Roll(int[] newStates){
        this.rolls ++;
        this.dieStates = newStates;
    }

    public int getRolls() {
        return this.rolls;
    }

    public int[] getDieStates() {
        return this.dieStates;
    }

    public void setDieStates(int[] dieStates) {
        this.dieStates = dieStates;
    }

    public String getName() {
        return this.name;
    }
    public int[] getPoints(){
        return this.points;
    }
    public void setPointsAt(int index, int value){
        this.points[index] = value;
    }
    public void resetDice(){
        this.dieStates = new int[6];
        this.rolls = 0;
    }
    public int getPointsSummary(){
        int sum = 0;
        // Bonus for > 63 points
        for(int i = 0; i < 6; i++){
            sum += this.points[i];
        }
        if(sum > 63){
            sum += 50;
            this.hasBonus = true;
        }
        for(int i = 6; i < 14; i++){
            sum += this.points[i];
        }
        return sum;
    }
    public boolean HasBonus(){
        return hasBonus;
    }
}
