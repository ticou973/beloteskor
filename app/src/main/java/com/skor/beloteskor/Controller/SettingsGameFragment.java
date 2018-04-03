package com.skor.beloteskor.Controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.skor.beloteskor.R;


public class SettingsGameFragment extends Fragment {

    private OnSettingsGameFragmentListener mListener;

    private Button  startGameBtn;
    private TextInputLayout tilPoints, tilDonnes;
    private TextInputEditText tietPoints, tietDonnes;
    private ToggleButton sansAnnonceBtn, annoncesBtn, sensAiguillesBtn, sensInverseBtn, distribYouBtn, distribYourPartnerBtn, distribLeftBtn, distribRightBtn;

    private String nom1;

    public SettingsGameFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingsGameFragmentListener) {
            mListener = (OnSettingsGameFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_game, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //gestion des settings des annonces
        sansAnnonceBtn = getActivity().findViewById(R.id.sans_annonce_btn);
        annoncesBtn = getActivity().findViewById(R.id.annonces_btn);
        sansAnnonceBtn.setChecked(true);
        annoncesBtn.setChecked(false);
        sansAnnonceBtn.setBackgroundResource(R.drawable.radius_button_accent);
        annoncesBtn.setBackgroundResource(R.drawable.radius_button_accent);
        annoncesBtn.setAlpha(0.3f);

        sansAnnonceBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && annoncesBtn.isChecked()){
                    annoncesBtn.setChecked(false);
                    annoncesBtn.setAlpha(0.3f);
                    sansAnnonceBtn.setAlpha(1.0f);
                    annoncesBtn.setBackgroundResource(R.drawable.radius_button_accent);

                }  else if (!isChecked && !annoncesBtn.isChecked()) {

                    sansAnnonceBtn.setChecked(true);
                   sansAnnonceBtn.setAlpha(1.0f);
                    sansAnnonceBtn.setBackgroundResource(R.drawable.radius_button_accent);
                }
            }
        });

        annoncesBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && sansAnnonceBtn.isChecked()){
                    sansAnnonceBtn.setChecked(false);
                    annoncesBtn.setAlpha(1.0f);
                    sansAnnonceBtn.setAlpha(0.3f);
                    sansAnnonceBtn.setBackgroundResource(R.drawable.radius_button_accent);

                }  else if (!isChecked && !sansAnnonceBtn.isChecked()) {

                    annoncesBtn.setChecked(true);
                    annoncesBtn.setAlpha(1.0f);
                    annoncesBtn.setBackgroundResource(R.drawable.radius_button_accent);
                }
            }
        });


        //gestion du sens des aiguilles

        sensAiguillesBtn = getActivity().findViewById(R.id.sens_aiguilles_btn);
        sensInverseBtn = getActivity().findViewById(R.id.sens_inverse_btn);
        sensAiguillesBtn.setChecked(true);
        sensInverseBtn.setChecked(false);
        sensAiguillesBtn.setBackgroundResource(R.drawable.radius_button_accent);
        sensInverseBtn.setBackgroundResource(R.drawable.radius_button_accent);
        sensInverseBtn.setAlpha(0.3f);

        sensAiguillesBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && sensInverseBtn.isChecked()){
                    sensInverseBtn.setChecked(false);
                    sensInverseBtn.setAlpha(0.3f);
                    sensAiguillesBtn.setAlpha(1.0f);
                    sensInverseBtn.setBackgroundResource(R.drawable.radius_button_accent);

                } else if (!isChecked && !sensInverseBtn.isChecked()) {

                    sensAiguillesBtn.setChecked(true);
                    sensAiguillesBtn.setAlpha(1.0f);
                    sensAiguillesBtn.setBackgroundResource(R.drawable.radius_button_accent);
                }
            }
        });

        sensInverseBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && sensAiguillesBtn.isChecked()){
                    sensAiguillesBtn.setChecked(false);
                    sensAiguillesBtn.setAlpha(0.3f);
                    sensInverseBtn.setAlpha(1.0f);
                    sensAiguillesBtn.setBackgroundResource(R.drawable.radius_button_accent);

                } else if (!isChecked && !sensAiguillesBtn.isChecked()) {

                    sensInverseBtn.setChecked(true);
                    sensInverseBtn.setAlpha(1.0f);
                    sensInverseBtn.setBackgroundResource(R.drawable.radius_button_accent);
                }
            }
        });


        //Gestion du premier distributeur

        distribYouBtn = getActivity().findViewById(R.id.distrib_you_btn);
        distribYourPartnerBtn = getActivity().findViewById(R.id.distrib_partner_btn);
        distribLeftBtn = getActivity().findViewById(R.id.distrib_left_btn);
        distribRightBtn = getActivity().findViewById(R.id.distrib_right_btn);


        distribRightBtn.setChecked(false);
        distribLeftBtn.setChecked(false);
        distribYourPartnerBtn.setChecked(false);
        distribYouBtn.setChecked(true);

        distribYourPartnerBtn.setAlpha(0.3f);
        distribLeftBtn.setAlpha(0.3f);
        distribRightBtn.setAlpha(0.3f);

        distribYouBtn.setBackgroundResource(R.drawable.radius_button_accent);
        distribYourPartnerBtn.setBackgroundResource(R.drawable.radius_button_accent);
        distribLeftBtn.setBackgroundResource(R.drawable.radius_button_accent);
        distribRightBtn.setBackgroundResource(R.drawable.radius_button_accent);


        distribYouBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && (distribYourPartnerBtn.isChecked()||distribLeftBtn.isChecked()||distribRightBtn.isChecked())){
                    distribRightBtn.setChecked(false);
                    distribLeftBtn.setChecked(false);
                    distribYourPartnerBtn.setChecked(false);

                    distribYourPartnerBtn.setAlpha(0.3f);
                    distribLeftBtn.setAlpha(0.3f);
                    distribRightBtn.setAlpha(0.3f);
                    distribYouBtn.setAlpha(1.0f);

                    distribYourPartnerBtn.setBackgroundResource(R.drawable.radius_button_accent);
                    distribLeftBtn.setBackgroundResource(R.drawable.radius_button_accent);
                    distribRightBtn.setBackgroundResource(R.drawable.radius_button_accent);

                } else if (!isChecked && !distribYourPartnerBtn.isChecked() && !distribLeftBtn.isChecked() && !distribRightBtn.isChecked()) {

                    distribYouBtn.setChecked(true);
                    distribYouBtn.setAlpha(1.0f);
                    distribYouBtn.setBackgroundResource(R.drawable.radius_button_accent);
                }
            }
        });

        distribYourPartnerBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && (distribYouBtn.isChecked()||distribLeftBtn.isChecked()||distribRightBtn.isChecked())){
                    distribRightBtn.setChecked(false);
                    distribLeftBtn.setChecked(false);
                    distribYouBtn.setChecked(false);

                    distribYourPartnerBtn.setAlpha(1.0f);
                    distribLeftBtn.setAlpha(0.3f);
                    distribRightBtn.setAlpha(0.3f);
                    distribYouBtn.setAlpha(0.3f);

                    distribYouBtn.setBackgroundResource(R.drawable.radius_button_accent);
                    distribLeftBtn.setBackgroundResource(R.drawable.radius_button_accent);
                    distribRightBtn.setBackgroundResource(R.drawable.radius_button_accent);

                } else if (!isChecked && !distribYouBtn.isChecked() && !distribLeftBtn.isChecked() && !distribRightBtn.isChecked()) {

                    distribYourPartnerBtn.setChecked(true);
                    distribYourPartnerBtn.setAlpha(1.0f);
                    distribYourPartnerBtn.setBackgroundResource(R.drawable.radius_button_accent);
                }
            }
        });

        distribLeftBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && (distribYourPartnerBtn.isChecked()||distribYouBtn.isChecked()||distribRightBtn.isChecked())){
                    distribRightBtn.setChecked(false);
                    distribYourPartnerBtn.setChecked(false);
                    distribYouBtn.setChecked(false);

                    distribYourPartnerBtn.setAlpha(0.3f);
                    distribLeftBtn.setAlpha(1.0f);
                    distribRightBtn.setAlpha(0.3f);
                    distribYouBtn.setAlpha(0.3f);

                    distribYouBtn.setBackgroundResource(R.drawable.radius_button_accent);
                    distribYourPartnerBtn.setBackgroundResource(R.drawable.radius_button_accent);
                    distribRightBtn.setBackgroundResource(R.drawable.radius_button_accent);


                } else if (!isChecked && !distribYourPartnerBtn.isChecked() && !distribYouBtn.isChecked() && !distribRightBtn.isChecked()) {

                    distribLeftBtn.setChecked(true);
                    distribLeftBtn.setAlpha(1.0f);
                    distribLeftBtn.setBackgroundResource(R.drawable.radius_button_accent);
                }
            }
        });

        distribRightBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && (distribYourPartnerBtn.isChecked()||distribLeftBtn.isChecked()||distribYouBtn.isChecked())){
                    distribLeftBtn.setChecked(false);
                    distribYourPartnerBtn.setChecked(false);
                    distribYouBtn.setChecked(false);

                    distribYourPartnerBtn.setAlpha(0.3f);
                    distribLeftBtn.setAlpha(0.3f);
                    distribRightBtn.setAlpha(1.0f);
                    distribYouBtn.setAlpha(0.3f);

                    distribYouBtn.setBackgroundResource(R.drawable.radius_button_accent);
                    distribYourPartnerBtn.setBackgroundResource(R.drawable.radius_button_accent);
                    distribLeftBtn.setBackgroundResource(R.drawable.radius_button_accent);

                } else if (!isChecked && !distribYourPartnerBtn.isChecked() && !distribLeftBtn.isChecked() && !distribYouBtn.isChecked()) {

                    distribRightBtn.setChecked(true);
                    distribRightBtn.setAlpha(1.0f);
                    distribRightBtn.setBackgroundResource(R.drawable.radius_button_accent);
                }
            }
        });



        //gestion des nb points ou Donnes

        tilDonnes = getActivity().findViewById(R.id.til_nb_donnes);
        tilPoints = getActivity().findViewById(R.id.til_nb_points);
        tietDonnes = getActivity().findViewById(R.id.nb_donnes_jouees);
        tietPoints = getActivity().findViewById(R.id.nb_points_joues);


        tietPoints.setHint("1001");
        tilPoints.setHint("Nb de Points : ");
        tilPoints.setBackgroundColor(getResources().getColor(R.color.color_accent2));
        tilPoints.setAlpha(1.0f);
        tilDonnes.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
        tilDonnes.setAlpha(0.5f);
        tietDonnes.setHint("12");
        tilDonnes.setHint("Nb de Donnes");


        tietPoints.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus ){
                    tilPoints.setBackgroundColor(getResources().getColor(R.color.color_accent2));
                    tilPoints.setAlpha(1.0f);
                    tilDonnes.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
                    tilDonnes.setAlpha(0.5f);
                    tietPoints.setCursorVisible(true);


                }else{
                    tilPoints.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
                    tilDonnes.setBackgroundColor(getResources().getColor(R.color.color_accent2));
                    tilDonnes.setAlpha(1.0f);
                    tilPoints.setAlpha(0.5f);
                    tietDonnes.setCursorVisible(true);
                }
            }
        });

        tietDonnes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (hasFocus){

                    tilPoints.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
                    tilDonnes.setBackgroundColor(getResources().getColor(R.color.color_accent2));
                    tilDonnes.setAlpha(1.0f);
                    tilPoints.setAlpha(0.5f);
                    tietDonnes.setCursorVisible(true);

                } else {
                    tilPoints.setBackgroundColor(getResources().getColor(R.color.color_accent2));
                    tilPoints.setAlpha(1.0f);
                    tilDonnes.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
                    tilDonnes.setAlpha(0.5f);
                    tietPoints.setCursorVisible(true);
                }
            }
        });


        tietPoints.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    String nbPoints = v.getEditableText().toString();

                    tietPoints.setText("");
                    tietPoints.setCursorVisible(false);
                    tietPoints.setHint(nbPoints);

                    handled = true;
                }
                return handled;

            }
        });

        tietDonnes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    String nbDonnes = v.getEditableText().toString();

                    tietDonnes.setText("");
                    tietDonnes.setCursorVisible(false);
                    tietDonnes.setHint(nbDonnes);

                    handled = true;
                }
                return handled;
            }
        });



        //Lancement de la partie
        startGameBtn = getActivity().findViewById(R.id.start_game_btn2);
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startGame();

            }
        });


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnSettingsGameFragmentListener {
        void onSettingsGameFragmentInteraction();
    }


    public void startGame() {
        if (mListener != null) {
            mListener.onSettingsGameFragmentInteraction();
        }
    }
}
