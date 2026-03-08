package com.example.yahtzy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;


public class Dice extends Fragment {
    Random rand = new Random();
    private Player player;
    private TextView playerText;
    private List<Switch> lockSwitch = new ArrayList<>();
    private Button rollButton;
    private Button endButton;
    private Button cheatSheetButton;
    private LinearLayout diceLayout;
    private List<LinearLayout> dice = new ArrayList<>();
    private List<View> constraintList;
    private int[] dieStates;

    public Dice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();

        player = activity.getPlayersArr()[activity.getCurrentPlayer()];

        diceLayout = getActivity().findViewById(R.id.dice);
        rollButton = getActivity().findViewById(R.id.rollButton);
        playerText = getActivity().findViewById(R.id.playerText);

        playerText.setText(player.Name);

        dieStates = player.dieStates;
        for(int i = 0; i < diceLayout.getChildCount(); i++){
            dice.add((LinearLayout) diceLayout.getChildAt(i));
            setDie(dieStates[i], (ImageView) dice.get(i).getChildAt(0));
        }
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    for(int i = 0; i < diceLayout.getChildCount(); i++){
                        if(( (Switch) dice.get(i).getChildAt(1) ).isChecked() && dieStates[i] == 0){
                            Toast.makeText(getContext(), "Unlock Mikitaka if you want to roll", Toast.LENGTH_SHORT).show();
                            throw new RuntimeException();
                        }
                    }

                    if(player.rolls < 3){
                        for(int i = 0; i < diceLayout.getChildCount(); i++){
                            if( !( (Switch) dice.get(i).getChildAt(1) ).isChecked()){
                                dieStates[i] = rand.nextInt(1,7);
                                setDie(dieStates[i], (ImageView) dice.get(i).getChildAt(0));
                            }
                        }
                        player.Roll(dieStates);
                    }
                    else{
                        Toast.makeText(getContext(), "You can't roll anymore", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ignore) {

                }
            }
        });
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.dieStates = dieStates;

                Fragment fragment = new Table();
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView, fragment);
                transaction.commit();
            }
        });
    }
    public void setDie(int value, ImageView dieImg){
        if(value == 0){
            dieImg.setImageResource(R.drawable.dice0);
        } else if(value == 1){
            dieImg.setImageResource(R.drawable.dice1);
        } else if (value == 2) {
            dieImg.setImageResource(R.drawable.dice2);
        } else if (value == 3) {
            dieImg.setImageResource(R.drawable.dice3);
        } else if (value == 4) {
            dieImg.setImageResource(R.drawable.dice4);
        } else if (value == 5) {
            dieImg.setImageResource(R.drawable.dice5);
        } else if (value == 6) {
            dieImg.setImageResource(R.drawable.dice6);
        }
    }
}