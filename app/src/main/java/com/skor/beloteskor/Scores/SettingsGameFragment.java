package com.skor.beloteskor.Scores;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.skor.beloteskor.MainActivity;
import com.skor.beloteskor.Model_DB.MainDb.Equipe;
import com.skor.beloteskor.Model_DB.MainDb.Joueur;
import com.skor.beloteskor.Model_DB.MainDb.Partie;
import com.skor.beloteskor.Model_DB.UtilsDb.ModeEquipe;
import com.skor.beloteskor.Model_DB.UtilsDb.SensJeu;
import com.skor.beloteskor.Model_DB.UtilsDb.Table;
import com.skor.beloteskor.Model_DB.UtilsDb.TypeAnnonce;
import com.skor.beloteskor.Model_DB.UtilsDb.TypeDePartie;
import com.skor.beloteskor.Model_DB.UtilsDb.TypeJeu;
import com.skor.beloteskor.R;

import java.util.List;


public class SettingsGameFragment extends Fragment {

    private OnSettingsGameFragmentListener mListener;

    private Button  startGameBtn;
    private TextInputLayout tilPoints, tilDonnes;
    private TextInputEditText tietPoints, tietDonnes;
    private ToggleButton sansAnnonceBtn, annoncesBtn, sensAiguillesBtn, sensInverseBtn, distribYouBtn, distribYourPartnerBtn, distribLeftBtn, distribRightBtn;
    private CardView cvDistribution;

    private String player1, player2, player3, player4;
    private int nbPoints, nbDonnes;
    private String[] listPlayers;
    private List<Joueur> playersList;
    private SensJeu sensJeu;


//todo penser à faire des méthodes pour enlever les quantités énormes de codes plus loin
                             //CONSTRUCTEURS


    public SettingsGameFragment() {
        // Required empty public constructor
    }

                            //INTERFACE FRAGMENT
    public interface OnSettingsGameFragmentListener {
        void onSettingsGameFragmentInteraction();
        void onSettingsModeEquipeChoice();
        boolean onSettingsVerifNoms();
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

        listPlayers = getArguments().getStringArray(MainActivity.EXTRA);


                                            //VARIANTES DE JEU

        //Settings du type de jeu sans ou avec annonces

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


        //Settings type de jeu (en points ou donnes)

        tilDonnes = getActivity().findViewById(R.id.til_nb_donnes);
        tilPoints = getActivity().findViewById(R.id.til_nb_points);
        tietDonnes = getActivity().findViewById(R.id.nb_donnes_jouees);
        tietPoints = getActivity().findViewById(R.id.nb_points_joues);


        nbPoints=R.integer.nb_points_classic_partie;
        nbDonnes=R.integer.nb_donnes_classic_partie;
        tietPoints.setHint(nbPoints);
        tilPoints.setHint("Nb de Points : ");
        tilPoints.setBackgroundColor(getResources().getColor(R.color.color_accent2));
        tilPoints.setAlpha(1.0f);
        tilDonnes.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
        tilDonnes.setAlpha(0.3f);
        tietDonnes.setHint(nbDonnes);
        tilDonnes.setHint("Nb de Donnes");



        tietPoints.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    tilPoints.setBackgroundColor(getResources().getColor(R.color.color_accent2));
                    tilPoints.setAlpha(1.0f);
                    tilDonnes.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
                    tilDonnes.setAlpha(0.3f);
                    tietPoints.setCursorVisible(true);


                }else{
                    tilPoints.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
                    tilDonnes.setBackgroundColor(getResources().getColor(R.color.color_accent2));
                    tilDonnes.setAlpha(1.0f);
                    tilPoints.setAlpha(0.3f);
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
                    tilPoints.setAlpha(0.3f);
                    tietDonnes.setCursorVisible(true);

                } else {
                    tilPoints.setBackgroundColor(getResources().getColor(R.color.color_accent2));
                    tilPoints.setAlpha(1.0f);
                    tilDonnes.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
                    tilDonnes.setAlpha(0.3f);
                    tietPoints.setCursorVisible(true);
                }
            }
        });


        tietPoints.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    nbPoints = Integer.parseInt(v.getEditableText().toString());

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

                    nbDonnes = Integer.parseInt(v.getEditableText().toString());

                    tietDonnes.setText("");
                    tietDonnes.setCursorVisible(false);
                    tietDonnes.setHint(nbDonnes);

                    handled = true;
                }
                return handled;
            }
        });


                                     //DISTRIBUTION

        //Card View Distribution

        cvDistribution = getActivity().findViewById(R.id.cv_distribution);
        cvDistribution.setVisibility(View.GONE);
        cvDistribution.setEnabled(false);



        //Sens du jeu

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


        //Premier distributeur

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





                                    //LANCEMENT D'UNE PARTIE

        //Bouton Start et DB
        startGameBtn = getActivity().findViewById(R.id.start_game_btn2);
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean verif = verifNoms();

                listPlayers = getArguments().getStringArray(MainActivity.EXTRA);

                player1 = listPlayers[0];
                player2 = listPlayers[1];
                player3 = listPlayers[2];
                player4 = listPlayers[3];

                if (verif && !cvDistribution.isEnabled()){

                    cvDistribution.setVisibility(View.VISIBLE);
                    cvDistribution.setEnabled(true);

                    distribYouBtn.setTextOff(player1);
                    distribYouBtn.setTextOn(player1);
                    distribYouBtn.setText(player1);
                    distribYourPartnerBtn.setTextOn(player2);
                    distribYourPartnerBtn.setTextOff(player2);
                    distribYourPartnerBtn.setText(player2);
                    distribLeftBtn.setTextOff(player3);
                    distribLeftBtn.setTextOn(player3);
                    distribLeftBtn.setText(player3);
                    distribRightBtn.setTextOn(player4);
                    distribRightBtn.setTextOff(player4);
                    distribRightBtn.setText(player4);

                } else if (verif && cvDistribution.isEnabled()){

                    startGame();

                } else if (!verif) {

                    ShowDialogModeEquipe();

                }

            }
        });
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


                        //METHODES FRAGMENT
//todo mettre startGame en équipe nominatif
    public void startGame() {
                        //Ecriture dans la DB des settings de la partie en cours

        //todo voir les simplifications pour joueur et player, cela semble redondant
        //Joueurs

        Joueur joueur1 = new Joueur(player1);
        Joueur joueur2 = new Joueur(player2);
        Joueur joueur3 = new Joueur(player3);
        Joueur joueur4 = new Joueur(player4);

        MainActivity.beloteSkorDb.joueurDao().insertPlayers(joueur1,joueur2,joueur3,joueur4);

        //Type de partie

        TypeDePartie typeDePartie = new TypeDePartie();

        typeDePartie.setModeEquipe(ModeEquipe.MODE_EQUIPE_STATIQUE_NOMINATIF.toString());

        if (sansAnnonceBtn.isChecked()){
            typeDePartie.setTypeAnnonce(TypeAnnonce.SANS_ANNONCE.toString());
        }else{
            typeDePartie.setTypeAnnonce(TypeAnnonce.AVEC_ANNONCES.toString());
        }

        if (tilDonnes.getAlpha()==1.0f) {

            typeDePartie.setTypeJeu(TypeJeu.DONNES.toString());
            typeDePartie.setNbPoints(0);
            typeDePartie.setNbDonnes(nbDonnes);

        }else{
            typeDePartie.setTypeJeu(TypeJeu.POINTS.toString());
            typeDePartie.setNbPoints(nbPoints);
            typeDePartie.setNbDonnes(0);
        }

        //Equipes et Table
        Equipe equipeA = new Equipe("EquipeA",joueur1,joueur2);
        Equipe equipeB = new Equipe("EquipeB",joueur3,joueur4);

        MainActivity.beloteSkorDb.equipeDao().insertAll(equipeA,equipeB);

        Table table = new Table(equipeA,equipeB);

        //Distribution

        if (sensAiguillesBtn.isChecked()){

            sensJeu = SensJeu.SENS_AIGUILLE;

        }else{
            sensJeu = SensJeu.SENS_INVERSE_AIGUILLE;
        }

        Joueur premierDistrib = new Joueur();

        if (distribYouBtn.isChecked()){

            premierDistrib.setNomJoueur(player1);

        }else if(distribYourPartnerBtn.isChecked()){

            premierDistrib.setNomJoueur(player2);

        }else if(distribLeftBtn.isChecked()){

            premierDistrib.setNomJoueur(player3);

        }else if(distribRightBtn.isChecked()){

            premierDistrib.setNomJoueur(player4);
        }

        //Partie

        Partie partie = new Partie(typeDePartie,table,premierDistrib,sensJeu,0,0,false);

        MainActivity.beloteSkorDb.partieDao().insertPartie(partie);

        //test

        //todo virer le test dès que implémenter ailleurs

        Partie lastPartie = MainActivity.beloteSkorDb.partieDao().getLastPartie();

        Joueur lastPArtiePremDistrib = lastPartie.getPremierDistributeur();

        String namePremDistrib = lastPArtiePremDistrib.getNomJoueur();

        Toast.makeText(getContext(), namePremDistrib, Toast.LENGTH_SHORT).show();




        if (mListener != null) {
            mListener.onSettingsGameFragmentInteraction();
        }
    }

    private void ShowDialogModeEquipe() {

        if (mListener != null) {
            mListener.onSettingsModeEquipeChoice();
        }

    }


    private boolean verifNoms() {

        if (mListener != null) {
            mListener.onSettingsVerifNoms();

            return mListener.onSettingsVerifNoms();
        }

        return false;
    }



}
