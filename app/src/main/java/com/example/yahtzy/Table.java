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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Table#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Table extends Fragment {
    List<String> allItems = new ArrayList<>(Arrays.asList("1's: ", "2's: ", "3's: ", "4's: ", "5's: ", "6's: ", "One pair: ", "Two pair: ", "Three of a kind: ", "Four of a kind: ", "Full house: ", "Small straight: ", "Large straight: ", "Chance: ", "Yatzy: "));
    List<String> realItems = new ArrayList<>();
    List<String> spinnerList = new ArrayList<>();
    int[] possiblePoints = new int[14];
    List<Integer> realPoints = new ArrayList<>();
    Player player;
    Spinner spinner;
    Button chooseButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Table() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Table.
     */
    // TODO: Rename and change types and number of parameters
    public static Table newInstance(String param1, String param2) {
        Table fragment = new Table();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity =  (MainActivity) requireActivity();
        player = (Player) activity.getPlayersArr()[activity.getCurrentPlayer()];
        // Compiling possible choices for player
        for (int i = 1; i < 7; i++){
            multiples(i,possiblePoints,player);
        }
        pair(possiblePoints,player);
        specials(possiblePoints,player);
        // Checking which have already been chosen and deleting
        for(int i = 0; i < possiblePoints.length; i++){
            if(player.getPoints()[i] != -1){
                possiblePoints[i] = -1;
                Log.d(allItems.get(i) + "?", Integer.toString(-1));
            }
            else {
                realPoints.add(possiblePoints[i]);
                realItems.add(allItems.get(i));
                Log.d(allItems.get(i), Integer.toString( possiblePoints[i]));
            }
            allItems.set(i, allItems.get(i) + possiblePoints[i]);
        }
        for(int i = 0; i < realItems.toArray().length; i++){
            spinnerList.add(realItems.get(i) + realPoints.get(i).toString());
        }
        // Compiling list of actual possible choices
        spinner = requireView().findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chooseButton = requireActivity().findViewById(R.id.chooseButton);
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetching selected item in spinner
                String current = spinner.getSelectedItem().toString();
                int index = allItems.indexOf(current);
                player.setPointsAt(index, possiblePoints[index]);
                player.resetDice();

                // MainActivity
                MainActivity activity = (MainActivity) requireActivity();
                activity.nextPlayer();

                // Check if game is ended
                if(!activity.getIfGameEndedState()){
                    Fragment fragment = new Dice();
                    FragmentManager manager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, fragment);
                    transaction.commit();
                }else{
                    Fragment fragment = new Summary();
                    FragmentManager manager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, fragment);
                    transaction.commit();
                }
            }
        });
    }
    public void multiples(int number, int[] numList, Player player){
        // 1'-6's
        int value = 0;
        for(int i = 0; i < player.getDieStates().length; i++){
            if(player.getDieStates()[i] == number){
                value += number;
            }
        }
        numList[number - 1] = value;
    }
    public void pair(int[] numList, Player player){
        int[] stateCounter = new int[6];
        for(int i = 1; i < stateCounter.length + 1; i++){
            for(int j = 0; j < player.getDieStates().length; j++){
                if(player.getDieStates()[j] == i){
                    stateCounter[i - 1]++;
                }
            }
        }
        if(IntStream.of(stateCounter).anyMatch(x -> x >= 2)){
            // One pair
            for(int i = stateCounter.length - 1; i > 0; i--){
                if(stateCounter[i] >= 2){
                    numList[6] = (i+1)*2;
                    break;
                }
            }
            // Two pairs
            int pairCount = 0;
            int[] pairs = new int[2];
            for(int i = stateCounter.length - 1; i > 0; i--){
                if(stateCounter[i] >= 2){
                    if(Math.floor(stateCounter[i]/2) >= 2){
                        pairs[0] = (i+1)*2; pairs[1] = (i+1)*2;
                        pairCount = 2;
                        break;
                    }
                    pairs[pairCount] = (i+1)*2;
                    pairCount ++;
                }
            }
            if(pairCount > 1){
                numList[7] = pairs[0] + pairs[1];
            }
        }
        if(IntStream.of(stateCounter).anyMatch(x -> x >= 3)){
            // Three of a kind
            for(int i = stateCounter.length; i > 0; i--){
                if(stateCounter[i-1] >= 3){
                    numList[8] = i*3;
                    break;
                }
            }
            // Full house
            if(IntStream.of(stateCounter).anyMatch(x -> x == 2)){
                for(int i = stateCounter.length - 1; i > 0; i--){
                    if(stateCounter[i] == 2){
                        numList[10] = (i+1)*2 + numList[8];
                        break;
                    }
                }
            }
        }
        // Four of a kind
        if(IntStream.of(stateCounter).anyMatch(x -> x >= 4)){
            for(int i = stateCounter.length - 1; i > 0; i--){
                if(stateCounter[i] >= 4){
                    numList[9] = (i+1)*4;
                }
            }
        }

    }
    public void specials(int[] numList, Player player){
        // Small straight
        if(IntStream.of(player.getDieStates()).anyMatch(x -> x == 1) && IntStream.of(player.getDieStates()).anyMatch(x -> x == 2) && IntStream.of(player.getDieStates()).anyMatch(x -> x == 3) && IntStream.of(player.getDieStates()).anyMatch(x -> x == 4) && IntStream.of(player.getDieStates()).anyMatch(x -> x == 5)){
            numList[11] = 15;
        }
        // Large straight
        else if(IntStream.of(player.getDieStates()).anyMatch(x -> x == 2) && IntStream.of(player.getDieStates()).anyMatch(x -> x == 3) && IntStream.of(player.getDieStates()).anyMatch(x -> x == 4) && IntStream.of(player.getDieStates()).anyMatch(x -> x == 5) && IntStream.of(player.getDieStates()).anyMatch(x -> x == 6)){
            numList[12] = 20;
        }
        // Chance
        for(int i = 0; i < player.getDieStates().length; i++){
            numList[13] += player.getDieStates()[i];
        }
        // Yatzy
        try {
            for(int i = 0; i < player.getDieStates().length - 1; i++){
                if(player.getDieStates()[i] != player.getDieStates()[i+1]) {
                    throw new Exception();
                }
            }
            numList[14] = 50;
        }catch (Exception ignore){

        }

    }
}