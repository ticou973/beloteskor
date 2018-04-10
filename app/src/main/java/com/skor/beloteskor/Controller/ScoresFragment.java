package com.skor.beloteskor.Controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.skor.beloteskor.Adapters.DonneAdapter;
import com.skor.beloteskor.Model.DonneScore;
import com.skor.beloteskor.Model.DonneScoreDetails;
import com.skor.beloteskor.R;

import java.util.ArrayList;
import java.util.List;


public class ScoresFragment extends Fragment {

    private OnScoresFragmentInteractionListener mListener;
    private RecyclerView scoreRecyclerView;
    private DonneAdapter donneAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView addDonneBtn;

    private List<DonneScore> donnesScore;

    public ScoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scores, container, false);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onScoresFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnScoresFragmentInteractionListener) {
            mListener = (OnScoresFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        scoreRecyclerView = getActivity().findViewById(R.id.recycler_view_scores);
        getDonneScore();
        donneAdapter = new DonneAdapter(donnesScore);
        layoutManager = new LinearLayoutManager(getContext());
        scoreRecyclerView.setLayoutManager(layoutManager);
        scoreRecyclerView.setAdapter(donneAdapter);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getDonneScore() {

        donnesScore = new ArrayList<>(6);
        for (int i = 0; i <6 ; i++) {

            List<DonneScoreDetails> donneScoreDetails = new ArrayList<>(1);
            donneScoreDetails.add(new DonneScoreDetails("Joueur" +i, "roro","ruru","rara","riri","roro", 2*i, 162-2*i));

            donnesScore.add(new DonneScore("Score N°" +i, donneScoreDetails));

        }
    }


    public interface OnScoresFragmentInteractionListener {
        // TODO: Update argument type and name
        void onScoresFragmentInteraction();
    }
}
