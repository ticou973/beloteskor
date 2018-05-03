package com.skor.beloteskor.Controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.skor.beloteskor.Adapters.DonneAdapter;
import com.skor.beloteskor.Model.DonneScore;
import com.skor.beloteskor.R;

import java.util.List;


public class ScoresFragment extends Fragment {

    private OnScoresFragmentInteractionListener mListener;
    private RecyclerView scoreRecyclerView;
    private DonneAdapter donneAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView addDonneBtn;

    private CardView cardView;


    public static final String EXTRA="com.skor.beloteskor.MESSAGE";
    private String player1, player2, player3, player4;
    private String[] listPlayers;


    private List<DonneScore> donnesScore;


    private GestureDetector detector;


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

        //Recycler View

        scoreRecyclerView = getActivity().findViewById(R.id.recycler_view_scores);
        donnesScore = getDonnesScores();

        donneAdapter = new DonneAdapter(donnesScore);
        layoutManager = new LinearLayoutManager(getContext());
        scoreRecyclerView.setLayoutManager(layoutManager);
        scoreRecyclerView.setAdapter(donneAdapter);


        //Button add donnes

        addDonneBtn = getActivity().findViewById(R.id.donne_add_btn);

        addDonneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addDonnesBtn();
            }
        });

    }




    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    //Todo à mettre dans la main activity avec l'aide du listener, idem pour le bouton d'ajout d'une mène ce qui permettra de rajouter un élément à la liste


    public interface OnScoresFragmentInteractionListener {
        // TODO: Update argument type and name
        List<DonneScore> onScoresFragmentInteraction();
        void onPressedAddDonnesBtn();


    }


    public List<DonneScore> getDonnesScores() {

        if (mListener != null) {
            mListener.onScoresFragmentInteraction();

            return mListener.onScoresFragmentInteraction();
        }

        return null;
    }

    public void addDonnesBtn(){
        if (mListener != null) {
            mListener.onPressedAddDonnesBtn();
        }
    }



}
