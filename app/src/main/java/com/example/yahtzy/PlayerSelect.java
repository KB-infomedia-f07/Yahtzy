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
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerSelect extends Fragment {
    Player[] players;
    int playersAmt;
    private List<EditText> playerTexts = new ArrayList<>();
    LinearLayout playerLayout;
    Button nextButton;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlayerSelect() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerSelect.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerSelect newInstance(String param1, String param2) {
        PlayerSelect fragment = new PlayerSelect();
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
        return inflater.inflate(R.layout.fragment_player_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Fetch MainActivity and players amount
        MainActivity activity = (MainActivity) requireActivity();
        playersAmt = activity.getPlayersAmt();

        // Remove extra players
        while(true){
            // try and catch for playerLayout
            try{
                playerLayout = requireView().findViewById(R.id.playerLayout);
                try{
                    playerLayout.removeViewAt(playersAmt);
                }
                catch (Exception e){
                    // Break when all extras are deleted
                    break;
                }
            }
            // Necessary catch
            catch (Exception e){
                break;
            }
        }

        playerLayout = requireView().findViewById(R.id.playerLayout);
        for(int i = 0; i < playersAmt; i++){
            playerTexts.add( (EditText) ( (LinearLayout) playerLayout.getChildAt(i) ).getChildAt(1) );
        }

        try{
            nextButton = getActivity().findViewById(R.id.nextButton);
        }catch (Exception ignore)
        {

        }

        players = new Player[playersAmt];

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating player objects
                for(int i = 0; i < playersAmt; i++){
                    try{
                    players[i] = new Player(playerTexts.get(i).getText().toString());
                    }catch (Exception e){
                        players[i] = new Player("player" + i);
                    }

                }
                // Setting players in MainActivity
                MainActivity activity = (MainActivity) requireActivity();
                activity.setPlayersArr(players);

                // Changing fragments
                Fragment fragment = new Dice();
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView, fragment);
                transaction.commit();
            }
        });

    }
}