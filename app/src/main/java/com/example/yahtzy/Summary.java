package com.example.yahtzy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Summary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Summary extends Fragment {
    int playersAmt;
    LinearLayout playerLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Summary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Summary.
     */
    // TODO: Rename and change types and number of parameters
    public static Summary newInstance(String param1, String param2) {
        Summary fragment = new Summary();
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
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                catch (Exception ignore){
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
        for(int i = 0; i < playerLayout.getChildCount(); i++){
            // Writing Summary for each player
            ((TextView) ((LinearLayout) playerLayout.getChildAt(i)).getChildAt(0)).setText(activity.getPlayersArr()[i].getName());
            ((TextView) ((LinearLayout) playerLayout.getChildAt(i)).getChildAt(2)).setText(Integer.toString(activity.getPlayersArr()[i].getPointsSummary()));
            if(activity.getPlayersArr()[i].HasBonus()){
                ((TextView) ((LinearLayout) playerLayout.getChildAt(i)).getChildAt(1)).setText("Yes");
            }else{
                ((TextView) ((LinearLayout) playerLayout.getChildAt(i)).getChildAt(1)).setText("No");
            }
        }

    }
}