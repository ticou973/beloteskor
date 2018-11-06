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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skor.beloteskor.MainActivity;
import com.skor.beloteskor.Model_DB.MainDb.Joueur;
import com.skor.beloteskor.Model_DB.MainDb.Partie;
import com.skor.beloteskor.Model_DB.UtilsDb.SensJeu;
import com.skor.beloteskor.R;

import java.util.ArrayList;


public class PlayerScoreFragment extends Fragment {

    private OnPlayerScoreFragmentListener mListener;

    private EditText yourName, yourPartnerName, onYourLeftName, onYourRightName;
    private TextView totalScoreA, totalScoreB;
    private ImageView triangleView;
    private Button distribBtn;

    private String player1="", player2="", player3="", player4="";
    private Boolean isInScoreFragment = false;
    private Boolean isFromMainActivity = false;
    //todo mettre les public en getter et setter
    public ArrayList<Integer> scores = new ArrayList<>();
    private int scoreTotalEquipe1,scoreTotalEquipe2;
    public static final String EXTRA="com.skor.beloteskor.MESSAGE";
    public static final String EXTRA1="com.skor.beloteskor.MESSAGE1";
    public static final String EXTRA2="com.skor.beloteskor.MESSAGE2";
    public static final String EXTRA3="com.skor.beloteskor.MESSAGE3";
    public static final String EXTRA4="com.skor.beloteskor.MESSAGE4";


    private String[] listPlayerName;
    private ArrayList<String> listPreneurs;;

    private Partie lastPartie;
    public Joueur currentDistrib;
    private SensJeu sensJeu;
    public int i;
    private String currentDistribName;

    public PlayerScoreFragment() {
        // Required empty public constructor
    }

    public interface OnPlayerScoreFragmentListener {
        void onPlayerScoreInteraction();
    }

                                     //LIFECYCLE
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Gestion du mode Joueur

        listPlayerName = getArguments().getStringArray(EXTRA);
        isInScoreFragment = getArguments().getBoolean(EXTRA1);
        isFromMainActivity = getArguments().getBoolean(EXTRA2);

        /*if(isFromMainActivity){
            currentDistribName= getArguments().getString(EXTRA3);
        }*/

        currentDistribName= getArguments().getString(EXTRA3);

        scores=getArguments().getIntegerArrayList(EXTRA4);
        scoreTotalEquipe1 = scores.get(0);
        scoreTotalEquipe2 = scores.get(1);

        listPreneurs = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.name_score_players, container, false);

        //JOUEURS
        //todo penser à gérer le format des noms (longueur...)
        //todo penser à gérer les doublons dans les 4 noms
        //todo penser à un autofill pour les noms déjà connus
        //todo penser à ajouter le distributeur courant

        //Noms des joueurs et Scores
        yourName = view.findViewById(R.id.et_you);
        yourPartnerName = view.findViewById(R.id.et_your_partner);
        onYourLeftName = view.findViewById(R.id.et_on_your_left);
        onYourRightName = view.findViewById(R.id.et_on_your_right);
        totalScoreA = view.findViewById(R.id.score_total_equipeA);
        totalScoreB = view.findViewById(R.id.score_total_equipeB);
        triangleView = view.findViewById(R.id.triangleView);
        triangleView.setVisibility(View.INVISIBLE);
        distribBtn=view.findViewById(R.id.btn_distrib);
        distribBtn.setVisibility(View.INVISIBLE);


        //todo mettre un autocomplete à partir de la base des joueurs - A retirer dès que test fini
        joueursAutoComplete();
        setListenerTextName();

        if (isInScoreFragment) {
            initTableScore();

            /*if(isFromMainActivity) {
                currentDistrib.setNomJoueur(currentDistribName);
            }*/

            currentDistrib.setNomJoueur(currentDistribName);

            //gestion du sens de jeu
            getListPreneurs();

            changePreneur();
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    // AUTRES MÉTHODES DU FRAGMENT

    private void setListenerTextName() {

        yourName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player1 = yourName.getEditableText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        yourPartnerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player2 = yourPartnerName.getEditableText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        onYourLeftName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player3 = onYourLeftName.getEditableText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });


        onYourRightName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player4 = onYourRightName.getEditableText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });


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

    private void initTableScore() {

       //UI
        yourName.setText(listPlayerName[0]);
        yourPartnerName.setText(listPlayerName[1]);
        onYourLeftName.setText(listPlayerName[2]);
        onYourRightName.setText(listPlayerName[3]);

        yourName.setEnabled(false);
        yourPartnerName.setEnabled(false);
        onYourLeftName.setEnabled(false);
        onYourRightName.setEnabled(false);
        triangleView.setVisibility(View.VISIBLE);
        distribBtn.setVisibility(View.VISIBLE);
        totalScoreA.setText(String.valueOf(scoreTotalEquipe1));
        totalScoreB.setText(String.valueOf(scoreTotalEquipe2));

        //DataBase
        lastPartie = MainActivity.beloteSkorDb.partieDao().getLastPartie();
        //currentDistrib = lastPartie.getPremierDistributeur();

        currentDistrib=MainActivity.beloteSkorDb.joueurDao().loadJoueurByName(currentDistribName);

        sensJeu = lastPartie.getSensJeu();

        setColorDistrib(currentDistrib);

    }

    private void setColorDistrib(Joueur currentDistrib) {

        if (currentDistrib.getNomJoueur().equals(player1)){
            yourName.setBackgroundResource(R.drawable.radius_button_accent);
            yourName.setBackgroundColor(getResources().getColor(R.color.color_accent2));
        }else if (currentDistrib.getNomJoueur().equals(player2)){
            yourPartnerName.setBackgroundResource(R.drawable.radius_button_accent);
            yourPartnerName.setBackgroundColor(getResources().getColor(R.color.color_accent2));
        }else if (currentDistrib.getNomJoueur().equals(player3)){
            onYourLeftName.setBackgroundResource(R.drawable.radius_button_accent);
            onYourLeftName.setBackgroundColor(getResources().getColor(R.color.color_accent2));
        }else if (currentDistrib.getNomJoueur().equals(player4)){
            onYourRightName.setBackgroundResource(R.drawable.radius_button_accent);
            onYourRightName.setBackgroundColor(getResources().getColor(R.color.color_accent2));
        }
    }

    private void getListPreneurs() {

        if (sensJeu == SensJeu.SENS_AIGUILLE) {

            listPreneurs.add(player1);
            listPreneurs.add(player3);
            listPreneurs.add(player2);
            listPreneurs.add(player4);

        }else{
            listPreneurs.add(player1);
            listPreneurs.add(player4);
            listPreneurs.add(player2);
            listPreneurs.add(player3);
        }

        i = listPreneurs.indexOf(currentDistrib.getNomJoueur());

    }


    private void changePreneur() {

        changeManually();

        if (isFromMainActivity) {

            changeCouleurPreneur();
        }
    }

    private void changeCouleurPreneur() {

        if (i<3) {
            currentDistrib.setNomJoueur(listPreneurs.get(i+1));

            changeColorPreneur(listPreneurs.get(i+1));

        }else{
            i=-1;
            currentDistrib.setNomJoueur(listPreneurs.get(i+1));
            changeColorPreneur(listPreneurs.get(i+1));                }

        for (String preneur:listPreneurs) {

            for (int j = 0; j < 4; j++) {

                if (j!=i+1){

                    preneur = listPreneurs.get(j);

                    ChangeColorNonPreneur(preneur);
                }
            }
        }

        i++;
    }

    private void changeManually() {

        distribBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeCouleurPreneur();

            }
        });



    }

    private void ChangeColorNonPreneur(String mainName) {

        if (mainName == player1){

            yourName.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

        }else if (mainName == player2){
            yourPartnerName.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

        }else if (mainName == player3){
            onYourLeftName.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

        }else if (mainName == player4){
            onYourRightName.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

        }

    }


    private void changeColorPreneur(String mainName) {

        Toast.makeText(getContext(), mainName, Toast.LENGTH_SHORT).show();


        if (mainName == player1) {
            yourName.setBackgroundResource(R.drawable.radius_button_accent);
            yourName.setBackgroundColor(getResources().getColor(R.color.color_accent2));

        }else if (mainName == player2){
            yourPartnerName.setBackgroundResource(R.drawable.radius_button_accent);
            yourPartnerName.setBackgroundColor(getResources().getColor(R.color.color_accent2));

        }else if (mainName == player3){
            onYourLeftName.setBackgroundResource(R.drawable.radius_button_accent);
            onYourLeftName.setBackgroundColor(getResources().getColor(R.color.color_accent2));

        }else if (mainName == player4){
            onYourRightName.setBackgroundResource(R.drawable.radius_button_accent);
            onYourRightName.setBackgroundColor(getResources().getColor(R.color.color_accent2));

        }

    }

    public void refreshTotalScore(int totalScore1, int totalScore2){

        totalScoreA.setText(String.valueOf(totalScore1));
        totalScoreB.setText(String.valueOf(totalScore2));
    }


    //Getter et Setter
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
