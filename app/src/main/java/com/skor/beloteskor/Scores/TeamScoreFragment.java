package com.skor.beloteskor.Scores;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skor.beloteskor.R;

import java.util.ArrayList;


public class TeamScoreFragment extends Fragment {

    private OnTeamFragmentInteractionListener mListener;

    private TextView totalScoreA, totalScoreB, etUs, etYou;
    private ImageView triangleView;

    private String player1="", player2="", player3="", player4="";
    public static final String EXTRA4="com.skor.beloteskor.MESSAGE4";
    private int scoreTotalEquipe1,scoreTotalEquipe2;
    public ArrayList<Integer> scores = new ArrayList<>();




    public TeamScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scores=getArguments().getIntegerArrayList(EXTRA4);
        scoreTotalEquipe1 = scores.get(0);
        scoreTotalEquipe2 = scores.get(1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.name_score_team, container, false);

        //JOUEURS

        //Noms des joueurs et Scores

        etUs = view.findViewById(R.id.et_you);
        etYou = view.findViewById(R.id.et_your_partner);
        totalScoreA = view.findViewById(R.id.score_total_equipeA);
        totalScoreB = view.findViewById(R.id.score_total_equipeB);
        triangleView = view.findViewById(R.id.triangleView);
        triangleView.setVisibility(View.INVISIBLE);

        totalScoreA.setText(String.valueOf(scoreTotalEquipe1));
        totalScoreB.setText(String.valueOf(scoreTotalEquipe2));

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onTeamFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTeamFragmentInteractionListener) {
            mListener = (OnTeamFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnTeamFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTeamFragmentInteraction();
    }
}
