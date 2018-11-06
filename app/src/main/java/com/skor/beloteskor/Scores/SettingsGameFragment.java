package com.skor.beloteskor.Scores;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
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
    private SensJeu sensJeu;

    //Todo virer dès que test viré
    private static String TAG = "coucou";



//todo penser à faire des méthodes pour enlever les quantités énormes de codes plus loin
                             //CONSTRUCTEURS


    public SettingsGameFragment() {
        // Required empty public constructor
    }

                            //INTERFACE FRAGMENT
    public interface OnSettingsGameFragmentListener {
        void onSettingsStartGamePLayers();
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

// todo A voir si pas dans le OncreateView
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listPlayers = getArguments().getStringArray(MainActivity.EXTRA);

        initSettings();

        setListenerSettings();

        setListenerEditorToTiet(tietPoints);
        setListenerEditorToTiet(tietDonnes);


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

                    sensAiguillesBtn.setChecked(true);
                    sensInverseBtn.setChecked(false);

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
                    startGamePlayers();

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

                //METHODES lIEES A L INTERFACE

    public void startGamePlayers() {
        saveSettings();

        if (mListener != null) {
            mListener.onSettingsStartGamePLayers();
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


                  //METHODES FRAGMENT

    private void initSettings() {

        //Settings du type de jeu sans ou avec annonces

        sansAnnonceBtn = getActivity().findViewById(R.id.sans_annonce_btn);
        annoncesBtn = getActivity().findViewById(R.id.annonces_btn);
        sansAnnonceBtn.setChecked(true);
        annoncesBtn.setChecked(false);
        sansAnnonceBtn.setBackgroundResource(R.drawable.radius_button_accent);
        annoncesBtn.setBackgroundResource(R.drawable.radius_button_accent);
        annoncesBtn.setAlpha(0.3f);

        //Settings type de jeu (en points ou donnes)

        tilDonnes = getActivity().findViewById(R.id.til_nb_donnes);
        tilPoints = getActivity().findViewById(R.id.til_nb_points);
        tietDonnes = getActivity().findViewById(R.id.nb_donnes_jouees);
        tietPoints = getActivity().findViewById(R.id.nb_points_joues);


        nbPoints= getResources().getInteger(R.integer.nb_points_classic_partie);
        nbDonnes= getResources().getInteger(R.integer.nb_donnes_classic_partie);

        tietPoints.setHint(String.valueOf(nbPoints));
        tilPoints.setHint("Nb de Points : ");
        tilPoints.setBackgroundColor(getResources().getColor(R.color.color_accent2));
        tilPoints.setAlpha(1.0f);
        tilDonnes.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
        tilDonnes.setAlpha(0.3f);
        tietDonnes.setHint(String.valueOf(nbDonnes));
        tilDonnes.setHint("Nb de Donnes");

        //DISTRIBUTION

        //Card View Distribution

        cvDistribution = getActivity().findViewById(R.id.cv_distribution);
        cvDistribution.setVisibility(View.GONE);
        cvDistribution.setEnabled(false);



        //Sens du jeu

        sensAiguillesBtn = getActivity().findViewById(R.id.sens_aiguilles_btn);
        sensInverseBtn = getActivity().findViewById(R.id.sens_inverse_btn);
        sensAiguillesBtn.setBackgroundResource(R.drawable.radius_button_accent);
        sensInverseBtn.setBackgroundResource(R.drawable.radius_button_accent);
        sensInverseBtn.setAlpha(0.3f);

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

    }

    private void setListenerSettings() {

        setListenerCheckedToToogleButton(sansAnnonceBtn,annoncesBtn);
        setListenerCheckedToToogleButton(annoncesBtn,sansAnnonceBtn);
        setListenerCheckedToToogleButton(sensAiguillesBtn,sensInverseBtn);
        setListenerCheckedToToogleButton(sensInverseBtn,sensAiguillesBtn);

        setListenerFocusToTiet(tietPoints,tilPoints,tietDonnes,tilDonnes);
        setListenerFocusToTiet(tietDonnes,tilDonnes,tietPoints,tilPoints);

        tietPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tietPoints.setCursorVisible(true);
            }
        });

        tietDonnes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tietDonnes.setCursorVisible(true);
            }
        });

        //todo gérer le fait d'obliger à valider l'un des deux (touche DOne)
        //todo limiter les nombres en grandeur à l'intérieur

        setListenerCheckedTo4ToggleButton(distribYouBtn,distribYourPartnerBtn,distribLeftBtn,distribRightBtn);
        setListenerCheckedTo4ToggleButton(distribYourPartnerBtn,distribYouBtn,distribLeftBtn,distribRightBtn);
        setListenerCheckedTo4ToggleButton(distribLeftBtn,distribYouBtn,distribYourPartnerBtn,distribRightBtn);
        setListenerCheckedTo4ToggleButton(distribRightBtn,distribYouBtn,distribYourPartnerBtn,distribLeftBtn);

    }

    private void setListenerCheckedToToogleButton (final ToggleButton mainTb, final ToggleButton secondTb){

        mainTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && secondTb.isChecked()){
                    secondTb.setChecked(false);
                    mainTb.setAlpha(1.0f);
                    secondTb.setAlpha(0.3f);
                    secondTb.setBackgroundResource(R.drawable.radius_button_accent);

                }  else if (!isChecked && !secondTb.isChecked()) {

                    mainTb.setChecked(true);
                    mainTb.setAlpha(1.0f);
                    mainTb.setBackgroundResource(R.drawable.radius_button_accent);
                }
            }
        });


    }

    private void setListenerFocusToTiet(final TextInputEditText mainTiet, final TextInputLayout mainTil, final TextInputEditText secondTiet, final TextInputLayout secondTil) {

        mainTiet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    mainTil.setBackgroundColor(getResources().getColor(R.color.color_accent2));
                    //todo mettre une ressource pour les alphas
                    mainTil.setAlpha(1.0f);
                    secondTil.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
                    secondTil.setAlpha(0.3f);
                    mainTiet.setCursorVisible(true);


                }else{
                    mainTil.setBackgroundColor(getResources().getColor(R.color.colorbuttonfalse));
                    secondTil.setBackgroundColor(getResources().getColor(R.color.color_accent2));
                    secondTil.setAlpha(1.0f);
                    mainTil.setAlpha(0.3f);
                    secondTiet.setCursorVisible(true);
                }
            }
        });

    }

    private void setListenerEditorToTiet(final TextInputEditText mainTiet) {

        mainTiet.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    mainTiet.setCursorVisible(false);

                    hideKeyboardFrom(getContext(),mainTiet);

                    handled = true;
                }
                return handled;

            }
        });

    }

    private void setListenerCheckedTo4ToggleButton(final ToggleButton mainTb, final ToggleButton firstTb, final ToggleButton secondTb, final ToggleButton thirdTb) {

        mainTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && (firstTb.isChecked()||secondTb.isChecked()||thirdTb.isChecked())){
                    firstTb.setChecked(false);
                    secondTb.setChecked(false);
                    thirdTb.setChecked(false);

                    firstTb.setAlpha(0.3f);
                    secondTb.setAlpha(0.3f);
                    thirdTb.setAlpha(0.3f);
                    mainTb.setAlpha(1.0f);

                    firstTb.setBackgroundResource(R.drawable.radius_button_accent);
                    secondTb.setBackgroundResource(R.drawable.radius_button_accent);
                    thirdTb.setBackgroundResource(R.drawable.radius_button_accent);

                } else if (!isChecked && !firstTb.isChecked() && !secondTb.isChecked() && !thirdTb.isChecked()) {

                    mainTb.setChecked(true);
                    mainTb.setAlpha(1.0f);
                    mainTb.setBackgroundResource(R.drawable.radius_button_accent);
                }
            }
        });

    }

    public void saveSettingsPartieAnonyme(){

        String nomJoueurEquipe1 = getResources().getString(R.string.us);
        String nomJoueurEquipe2 = getResources().getString(R.string.you);

        Joueur joueur1 = new Joueur(nomJoueurEquipe1+"1");
        Joueur joueur2 = new Joueur(nomJoueurEquipe1+"2");
        Joueur joueur3 = new Joueur(nomJoueurEquipe2+"1");
        Joueur joueur4 = new Joueur(nomJoueurEquipe2+"2");

        MainActivity.beloteSkorDb.joueurDao().insertPlayers(joueur1,joueur2,joueur3,joueur4);

        //type de partie
        TypeDePartie typeDePartie = new TypeDePartie();

        typeDePartie.setModeEquipe(ModeEquipe.MODE_EQUIPE_STATIQUE_ANONYME.toString());

        savePointsDonnesAnnonce(typeDePartie);

        //Equipes et Table
        Equipe equipeA = new Equipe("EquipeA",joueur1,joueur2);
        Equipe equipeB = new Equipe("EquipeB",joueur3,joueur4);

        MainActivity.beloteSkorDb.equipeDao().insertAll(equipeA,equipeB);

        Table table = new Table(equipeA,equipeB);

        sensJeu = SensJeu.NO_SENS_JEU;

        if (sensAiguillesBtn.isChecked()){

            sensJeu = SensJeu.SENS_INVERSE_AIGUILLE;

        }else if(sensInverseBtn.isChecked()){
            sensJeu = SensJeu.SENS_AIGUILLE;
        }

        Joueur premierDistrib = new Joueur();

        premierDistrib.setNomJoueur("Nodistrib");

        //Partie


        Partie partie = new Partie(typeDePartie,table,premierDistrib,sensJeu,0,0,false);

        MainActivity.beloteSkorDb.partieDao().insertPartie(partie);

        //TEST

        //todo virer le test dès que implémenter ailleurs

        Partie lastPartie = MainActivity.beloteSkorDb.partieDao().getLastPartie();

        TypeDePartie lastTypePartie = lastPartie.getType();
        Table lastTable = lastPartie.getTable();
        Joueur lastPremierDistrib = lastPartie.getPremierDistributeur();
        String lastNomPremierDistrib = lastPremierDistrib.getNomJoueur();
        SensJeu lastSensJeu = lastPartie.getSensJeu();
        int lastScoreEquipeA = lastPartie.getScoreEquipeA();
        int lastScoreEquipeB = lastPartie.getScoreEquipeB();
        boolean lastPartieterm = lastPartie.isPartieterminee();

        String lastTypeJeu = lastTypePartie.getTypeJeu();
        String lastTypeAnnonce = lastTypePartie.getTypeAnnonce();
        String lastModeEquipe = lastTypePartie.getModeEquipe();
        int lastNbPoints = lastTypePartie.getNbPoints();
        int lastNbDonnes = lastTypePartie.getNbDonnes();

        Equipe lastEquipeA = lastTable.getEquipeA();
        Equipe lastEquipeB = lastTable.getEquipeB();

        String lastJoueur1EqA = lastEquipeA.getJoueur1().getNomJoueur();
        String lastJoueur2EqA = lastEquipeA.getJoueur2().getNomJoueur();
        String lastJoueur1EqB = lastEquipeB.getJoueur1().getNomJoueur();
        String lastJoueur2EqB = lastEquipeB.getJoueur2().getNomJoueur();

        String newligne=System.getProperty("line.separator");

        Log.i(TAG, newligne + lastPartie.getPartieId() + newligne + "type jeu : " + lastTypeJeu + newligne + "type Annonce : " + lastTypeAnnonce + newligne + "Mode Equipe : " + lastModeEquipe + newligne +  "Nb Points : " + lastNbPoints + newligne + "Nb Donnes : " + lastNbDonnes
                + newligne + "joueurs : " + lastJoueur1EqA + ", "  + lastJoueur2EqA + ", " + lastJoueur1EqB + ", " + lastJoueur2EqB + newligne + "premier distributeur : " + lastNomPremierDistrib + newligne + "Sens Jeu : "
                + lastSensJeu + newligne + "lastScoreEqA : " + lastScoreEquipeA + newligne + "lastScoreEquipeB : " + lastScoreEquipeB + newligne + "statut partie : " + lastPartieterm);



    }
    //todo voir pour faire des asynctasks pour les settings pour éviter le thread main (trop long au départ)

    private void saveSettings(){
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

        //nbpoints ou nbDonnes

        savePointsDonnesAnnonce(typeDePartie);

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

        //TEST

        //todo virer le test dès que implémenter ailleurs

        Partie lastPartie = MainActivity.beloteSkorDb.partieDao().getLastPartie();

        TypeDePartie lastTypePartie = lastPartie.getType();
        Table lastTable = lastPartie.getTable();
        Joueur lastPremierDistrib = lastPartie.getPremierDistributeur();
        String lastNomPremierDistrib = lastPremierDistrib.getNomJoueur();
        SensJeu lastSensJeu = lastPartie.getSensJeu();
        int lastScoreEquipeA = lastPartie.getScoreEquipeA();
        int lastScoreEquipeB = lastPartie.getScoreEquipeB();
        boolean lastPartieterm = lastPartie.isPartieterminee();

        String lastTypeJeu = lastTypePartie.getTypeJeu();
        String lastTypeAnnonce = lastTypePartie.getTypeAnnonce();
        String lastModeEquipe = lastTypePartie.getModeEquipe();
        int lastNbPoints = lastTypePartie.getNbPoints();
        int lastNbDonnes = lastTypePartie.getNbDonnes();

        Equipe lastEquipeA = lastTable.getEquipeA();
        Equipe lastEquipeB = lastTable.getEquipeB();

        String lastJoueur1EqA = lastEquipeA.getJoueur1().getNomJoueur();
        String lastJoueur2EqA = lastEquipeA.getJoueur2().getNomJoueur();
        String lastJoueur1EqB = lastEquipeB.getJoueur1().getNomJoueur();
        String lastJoueur2EqB = lastEquipeB.getJoueur2().getNomJoueur();

        String newligne=System.getProperty("line.separator");

        Log.i(TAG, newligne + lastPartie.getPartieId() + newligne + "type jeu : " + lastTypeJeu + newligne + "type Annonce : " + lastTypeAnnonce + newligne + "Mode Equipe : " + lastModeEquipe + newligne +  "Nb Points : " + lastNbPoints + newligne + "Nb Donnes : " + lastNbDonnes
                + newligne + "joueurs : " + lastJoueur1EqA + ", "  + lastJoueur2EqA + ", " + lastJoueur1EqB + ", " + lastJoueur2EqB + newligne + "premier distributeur : " + lastNomPremierDistrib + newligne + "Sens Jeu : "
                 + lastSensJeu + newligne + "lastScoreEqA : " + lastScoreEquipeA + newligne + "lastScoreEquipeB : " + lastScoreEquipeB + newligne + "statut partie : " + lastPartieterm);



    }

    private void savePointsDonnesAnnonce(TypeDePartie typeDePartie) {
        if (tilPoints.getAlpha()==1.0f){
            nbDonnes =0;

            if (!TextUtils.isEmpty(tietPoints.getEditableText())) {
                nbPoints = Integer.parseInt(tietPoints.getEditableText().toString());
            }

        }else{
            nbPoints =0;
            if (!TextUtils.isEmpty(tietDonnes.getEditableText())) {
                nbDonnes = Integer.parseInt(tietDonnes.getEditableText().toString());            }
        }


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

    }



    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
