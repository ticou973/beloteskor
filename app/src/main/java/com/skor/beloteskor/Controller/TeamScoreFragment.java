package com.skor.beloteskor.Controller;

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


public class TeamScoreFragment extends Fragment {

    private OnTeamFragmentInteractionListener mListener;

    private TextView totalScoreA, totalScoreB, etUs, etYou;
    private ImageView triangleView;

    private String player1="", player2="", player3="", player4="";

    public TeamScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.name_score_team, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //JOUEURS

        //Noms des joueurs et Scores

        etUs = getActivity().findViewById(R.id.et_you);
        etYou = getActivity().findViewById(R.id.et_your_partner);
        totalScoreA = getActivity().findViewById(R.id.score_total_equipeA);
        totalScoreB = getActivity().findViewById(R.id.score_total_equipeB);
        triangleView = getActivity().findViewById(R.id.triangleView);
        triangleView.setVisibility(View.INVISIBLE);

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
