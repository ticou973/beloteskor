package com.skor.beloteskor.Controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    private String player1, player2, player3, player4;

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPlayerScoreInteraction();
        }
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



        /*
           triangleView.setVisibility(View.VISIBLE);
                    totalScoreA.setText("0");
                    totalScoreB.setText("0");
         */
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnPlayerScoreFragmentListener {
        void onPlayerScoreInteraction();
    }

    private boolean verifNoms() {

        player1 = yourName.getEditableText().toString();
        player2 = yourPartnerName.getEditableText().toString();
        player3 = onYourLeftName.getEditableText().toString();
        player4 = onYourRightName.getEditableText().toString();


        if((player1.equals("")|| player2.equals("") || player3.equals("") || player4.equals(""))){

            return false;
        }

        return true;

    }

}
