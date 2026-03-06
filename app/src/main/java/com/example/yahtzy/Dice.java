package com.example.yahtzy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dice extends Fragment {

    private String player;
    private int RollAmount = 0;
    private Switch LockSwitch;
    private Button RollButton;
    private LinearLayout DiceLayout;
    private ArrayList<View> Dice;
    private List<View> ConstraintList;
    private int[] ints;

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
}