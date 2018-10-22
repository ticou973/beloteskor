package com.skor.beloteskor.Scores;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.skor.beloteskor.R;

import java.util.ArrayList;
import java.util.List;


public class PlayerScoreFragment extends Fragment {

    private OnPlayerScoreFragmentListener mListener;

    private EditText yourPartnerName, onYourLeftName, onYourRightName;
    private AutoCompleteTextView yourName;
    private TextView totalScoreA, totalScoreB;
    private ImageView triangleView;

    private String player1="", player2="", player3="", player4="";
    private Boolean isInScoreFragment = false;
    public static final String EXTRA="com.skor.beloteskor.MESSAGE";
    public static final String EXTRA1="com.skor.beloteskor.MESSAGE1";


    private String[] listPlayerName;
    private ArrayList<String> listPlayers;
    List<String> nomJoueurs;
    String[] players;

    public PlayerScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPlayerScoreFragmentListener) {
            mListener = (OnPlayerScoreFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.name_score_players, container, false);

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //todo penser à gérer le format des noms (longueur...)
        //todo penser à gérer les doublons dans les 4 noms
        //todo penser à un autofill pour les noms déjà connus

        //JOUEURS


        //Noms des joueurs et Scores

        yourName = getActivity().findViewById(R.id.et_you);
        yourPartnerName = getActivity().findViewById(R.id.et_your_partner);
        onYourLeftName = getActivity().findViewById(R.id.et_on_your_left);
        onYourRightName = getActivity().findViewById(R.id.et_on_your_right);
        totalScoreA = getActivity().findViewById(R.id.score_total_equipeA);
        totalScoreB = getActivity().findViewById(R.id.score_total_equipeB);
        triangleView = getActivity().findViewById(R.id.triangleView);
        triangleView.setVisibility(View.INVISIBLE);


        //Gestion du mode Joueur

        listPlayerName = getArguments().getStringArray(EXTRA);
        isInScoreFragment = getArguments().getBoolean(EXTRA1);


        if (isInScoreFragment) {

            yourName.setText(listPlayerName[0]);
            yourPartnerName.setText(listPlayerName[1]);
            onYourLeftName.setText(listPlayerName[2]);
            onYourRightName.setText(listPlayerName[3]);

            yourName.setEnabled(false);
            yourPartnerName.setEnabled(false);
            onYourLeftName.setEnabled(false);
            onYourRightName.setEnabled(false);
            triangleView.setVisibility(View.VISIBLE);
            totalScoreA.setText("0");
            totalScoreB.setText("0");
        }

        //todo mettre un autocomplete à partir de la base des joueurs - A retirer dès que test fini

        joueursAutoComplete();

        yourName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                player1 = yourName.getEditableText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        yourPartnerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                player2 = yourPartnerName.getEditableText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        onYourLeftName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                player3 = onYourLeftName.getEditableText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        onYourRightName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                player4 = onYourRightName.getEditableText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    public interface OnPlayerScoreFragmentListener {
        void onPlayerScoreInteraction();
    }

    public String[] getPlayersName() {

        String[] listPlayers = {player1, player2, player3, player4};

        return listPlayers;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer3() {
        return player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    public String getPlayer4() {
        return player4;
    }

    public void setPlayer4(String player4) {
        this.player4 = player4;
    }


    //todo a virer dès que les test sont terminés
    private void joueursAutoComplete() {

        player1 = "Alfred";
        player2 = "Berangère";
        player3 = "Charles";
        player4 = "Dorothée";

        yourName.setText(player1);
        yourPartnerName.setText(player2);
        onYourLeftName.setText(player3);
        onYourRightName.setText(player4);

    }
}
