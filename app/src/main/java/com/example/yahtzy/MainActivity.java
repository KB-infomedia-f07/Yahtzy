package com.example.yahtzy;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int playersAmt;
    private int currentPlayer = 0;
    private Player[] players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void setPlayersArr(Player[] value){
        players = value;
    }
    public Player[] getPlayersArr(){
        return players;
    }
    public void nextPlayer(){
        if(currentPlayer < playersAmt){
            currentPlayer ++;
        }
        else{
            currentPlayer = 0;
        }
    }
    public int getCurrentPlayer(){
        return currentPlayer;
    }
    public int getPlayersAmt(){
        return playersAmt;
    }
    public void setPlayersAmt(int value){
        playersAmt = value;
    }
}