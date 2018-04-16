package com.skor.beloteskor.Controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.skor.beloteskor.R;


public class PlayerScoreFragment extends Fragment {

    private OnPlayerScoreFragmentListener mListener;

    private EditText yourName, yourPartnerName, onYourLeftName, onYourRightName;
    private TextView totalScoreA, totalScoreB;
    private ImageView triangleView;

    private String player1="", player2="", player3="", player4="";
    public static final String EXTRA="com.skor.beloteskor.MESSAGE";

    private String[] listPlayerName;

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

        if (listPlayerName[0] != "" && listPlayerName[1] != "" && listPlayerName[2] !="" && listPlayerName[3] != "") {

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
}
