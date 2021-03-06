package com.skor.beloteskor;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.skor.beloteskor.Model_DB.MainDb.AppDatabase;
import com.skor.beloteskor.Model_DB.MainDb.Donne;
import com.skor.beloteskor.Model_DB.MainDb.Joueur;
import com.skor.beloteskor.Model_DB.MainDb.Partie;
import com.skor.beloteskor.Model_DB.UtilsDb.ModeEquipe;
import com.skor.beloteskor.Players.PlayerMenuFragment;
import com.skor.beloteskor.Players.PlayersFragment;
import com.skor.beloteskor.Scores.Adapters.DonneAdapter;
import com.skor.beloteskor.Scores.DialogDonneNullFragment;
import com.skor.beloteskor.Scores.DialogModeEquipeFragment;
import com.skor.beloteskor.Scores.DialogWinnerFragment;
import com.skor.beloteskor.Scores.PlayerScoreFragment;
import com.skor.beloteskor.Scores.ScoresFragment;
import com.skor.beloteskor.Scores.SettingsGameFragment;
import com.skor.beloteskor.Scores.TeamScoreFragment;
import com.skor.beloteskor.Statistics.StatisticsFragment;
import com.skor.beloteskor.Statistics.StatisticsMenuFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SettingsGameFragment.OnSettingsGameFragmentListener, PlayersFragment.OnPlayersFragmentInteractionListener, StatisticsFragment.OnStatisticsFragmentInteractionListener,
        DialogModeEquipeFragment.DialogModeEquipeFragmentListener, PlayerScoreFragment.OnPlayerScoreFragmentListener, TeamScoreFragment.OnTeamFragmentInteractionListener, DonneAdapter.OnDonneAdapterListener, ScoresFragment.OnScoresFragmentInteractionListener, DialogDonneNullFragment.DialogDonneNullFragmentListener, DialogWinnerFragment.DialogWinnerFragmentListener {

    private android.support.v7.widget.Toolbar toolbar;
    private BottomNavigationView navigation;
    private FrameLayout flFragmentMain, flFragmentNameScore;

    private FragmentTransaction transaction;
    private SettingsGameFragment settingsGameFragment;
    private PlayerScoreFragment playerScoreFragment;
    private TeamScoreFragment teamScoreFragment;
    private ScoresFragment scoresFragment;
    private StatisticsMenuFragment statisticsMenuFragment;
    private PlayerMenuFragment playerMenuFragment;
    private Partie nellePartie, currentPartie;

    public static final String EXTRA="com.skor.beloteskor.MESSAGE";
    public static final String EXTRA1="com.skor.beloteskor.MESSAGE1";
    public static final String EXTRA2="com.skor.beloteskor.MESSAGE2";
    public static final String EXTRA3="com.skor.beloteskor.MESSAGE3";
    public static final String EXTRA4="com.skor.beloteskor.MESSAGE4";

    private static String TAG = "coucou";
    private String player1, player2, player3, player4,currentDistrib;
    private String[] listPlayersName={"","","",""};
    private List<Donne> donnes = null;
    private ModeEquipe modeEquipe = null;
    private boolean isFromMainActivity, isInScoresFragment;
    private ArrayList<Integer> scores;

    public static AppDatabase beloteSkorDb;

    //todo V1 faire un splash
    //todo V1 faire un icon d'application
    //todo V1a Faire du beau code en mettant des méthodes pour tous les endroits où j'ai mis des titres
    //todo V1a vérifier qu'il y ait besoin d'instancier le fragment en premier avec la navigation
    //todo V0 récupérer les contextes dans tous les fragments avec le onAttach des fragments en déclarant context au départ
    //todo V1a mettre une methode newinstance dans le fragment pour éviter de créer cette instance dans le main instance, peut être fait avec les mlistener
    //todo V1a Changer le modèle par un modèleView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gestion de la DB : instance de la DB

        //todo V1a voir pour le allowMain Thread queries et fallbackTodestruction
        //todo V1a voir pour les migrations évolutions des bases de données

        beloteSkorDb = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"BeloteSkorDb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        //gestion de la Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("  BeloteSkor");
        actionBar.setIcon(R.drawable.ic_clubs_33561);


                                    //FRAGMENTS

        flFragmentMain = findViewById(R.id.fl_fragment_main);
        flFragmentNameScore = findViewById(R.id.fl_fragment_name_score);

        //Lancement du fragment de settings

        playerScoreFragment = new PlayerScoreFragment();
        isInScoresFragment = false;
        isFromMainActivity = false;
        currentDistrib=" ";
        scores = new ArrayList<>();
        scores.add(0);
        scores.add(0);

        Bundle argsinit = new Bundle();
        argsinit.putStringArray(EXTRA,listPlayersName);
        argsinit.putBoolean(EXTRA1,isInScoresFragment);
        argsinit.putBoolean(EXTRA2,isFromMainActivity);
        argsinit.putString(EXTRA3,currentDistrib);
        argsinit.putIntegerArrayList(EXTRA4,scores);
        playerScoreFragment.setArguments(argsinit);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

        settingsGameFragment = new SettingsGameFragment();
        Bundle args = new Bundle();
        args.putStringArray(EXTRA,listPlayersName);
        settingsGameFragment.setArguments(args);
        replaceFragment(settingsGameFragment);

        //gestion de la bottom navigation view
        navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.scores);

        //Navigation dans la Bottom view
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.players:

                        playerMenuFragment = new PlayerMenuFragment();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fl_fragment_name_score, playerMenuFragment).commit();

                        PlayersFragment playersFragment = new PlayersFragment();
                        replaceFragment(playersFragment);
                        return true;

                    case R.id.scores:

                        currentPartie = beloteSkorDb.partieDao().getLastPartie();

                        Log.i(TAG, "onNavigationItemSelected: "+currentPartie.getType().getModeEquipe());

                        //todo V0 gérer les différents cas pour les scores
                        if(isInScoresFragment){
                            if(currentPartie.getType().getModeEquipe().equals(ModeEquipe.MODE_EQUIPE_STATIQUE_NOMINATIF.toString())){
                                //todo V0 à virer inscoreF dès que cela marche
                                isInScoresFragment = true;
                                isFromMainActivity = false;
                                currentDistrib = playerScoreFragment.currentDistrib.getNomJoueur();

                                scores = new ArrayList<>();
                                scores.add(currentPartie.getScoreEquipeA());
                                scores.add(currentPartie.getScoreEquipeB());

                                playerScoreFragment = new PlayerScoreFragment();
                                Bundle args = new Bundle();
                                args.putStringArray(EXTRA,listPlayersName);
                                args.putBoolean(EXTRA1,isInScoresFragment);
                                args.putBoolean(EXTRA2,isFromMainActivity);
                                args.putString(EXTRA3,currentDistrib);
                                args.putIntegerArrayList(EXTRA4,scores);

                                playerScoreFragment.setArguments(args);
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

                                scoresFragment = new ScoresFragment();
                                //todo V0 voir si obligé ?
                                Bundle argsscore = new Bundle();
                                argsscore.putStringArray(EXTRA,listPlayersName);
                                scoresFragment.setArguments(argsscore);
                                replaceFragment(scoresFragment);


                            }else if(currentPartie.getType().getModeEquipe().equals(ModeEquipe.MODE_EQUIPE_STATIQUE_ANONYME.toString())){

                                scores = new ArrayList<>();
                                scores.add(currentPartie.getScoreEquipeA());
                                scores.add(currentPartie.getScoreEquipeB());

                                currentDistrib=currentPartie.getPremierDistributeur().getNomJoueur();

                                teamScoreFragment = new TeamScoreFragment();
                                Bundle args = new Bundle();
                                args.putIntegerArrayList(EXTRA4,scores);

                                teamScoreFragment.setArguments(args);
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.fl_fragment_name_score, teamScoreFragment).commit();

                                scoresFragment = new ScoresFragment();
                                replaceFragment(scoresFragment);

                            }
                           // ScoresFragment scoresFragment = new ScoresFragment();
                           // replaceFragment(scoresFragment);

                        }else{
                            playerScoreFragment = new PlayerScoreFragment();
                            //todo V0 à virer inscoreF dès que cela marche

                            isInScoresFragment=false;
                            isFromMainActivity = false;
                            currentDistrib=" ";
                            scores = new ArrayList<>();
                            scores.add(0);
                            scores.add(0);

                            Bundle argsinit = new Bundle();
                            argsinit.putStringArray(EXTRA,listPlayersName);
                            argsinit.putBoolean(EXTRA1,isInScoresFragment);
                            argsinit.putBoolean(EXTRA2,isFromMainActivity);
                            argsinit.putString(EXTRA3,currentDistrib);
                            argsinit.putIntegerArrayList(EXTRA4,scores);
                            playerScoreFragment.setArguments(argsinit);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

                            //todo V1a prendre en compte les options déjà déclarées
                            settingsGameFragment = new SettingsGameFragment();
                            Bundle args = new Bundle();
                            args.putStringArray(EXTRA,listPlayersName);
                            settingsGameFragment.setArguments(args);
                            replaceFragment(settingsGameFragment);
                        }

                        return true;

                    case R.id.statistics:

                        statisticsMenuFragment =new StatisticsMenuFragment();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fl_fragment_name_score, statisticsMenuFragment).commit();

                        StatisticsFragment statisticsFragment = new StatisticsFragment();
                        replaceFragment(statisticsFragment);
                        return true;

                    default:
                        Toast.makeText(MainActivity.this, "default", Toast.LENGTH_SHORT).show();
                        return false;
                }
            }
        });
    }

                        //MENU OPTIONS

    //Méthode qui inflate le menu d'option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);

        return true;
    }

    //todo V0 menu options
  //Méthode qui gère la sélection de le menu de la Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    //todo V0 prévoir un onglet nouvelle partie (à la place de search ?

        switch (item.getItemId()) {

            case R.id.preferences:

                return true;

            case R.id.infos_donne:

                return true;

            case R.id.app_bar_search:

                return true;

            case R.id.logout:

                return true;

                //todo V0 penser à retirer ces 2 menus plus tard et dans le menu.xml

            case R.id.reset_db:

                beloteSkorDb.joueurDao().deleteAll();
                beloteSkorDb.partieDao().deleteAll();
                beloteSkorDb.donneDao().deleteAll();

                return true;

            case R.id.relaunch:

                replaceFragment(settingsGameFragment);

                Boolean isInScoresFragment = false;
                Boolean isFromMainActivity = false;


                playerScoreFragment = new PlayerScoreFragment();
                Bundle args = new Bundle();
                args.putStringArray(EXTRA,listPlayersName);
                args.putBoolean(EXTRA1,isInScoresFragment);
                args.putBoolean(EXTRA2,isFromMainActivity);

                playerScoreFragment.setArguments(args);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

                return true;

            default:
            return super.onOptionsItemSelected(item);
        }
    }


                            //METHODES FRAGMENTS IMPLEMENTES

                             //Fragments SettingsGame

    @Override
    public void onSettingsStartGamePLayers() {
        //todo V0 vérifier si modeEquipe intéressant ici ou à supprimer
        modeEquipe = ModeEquipe.MODE_EQUIPE_STATIQUE_NOMINATIF;
        isInScoresFragment = true;
        isFromMainActivity = false;
        currentDistrib=beloteSkorDb.partieDao().getLastPartie().getPremierDistributeur().getNomJoueur();

       // Log.i(TAG, "onSettingsStartGamePLayers: "+ currentDistrib);

        scores = new ArrayList<>();
        scores.add(0);
        scores.add(0);

        playerScoreFragment = new PlayerScoreFragment();
        Bundle args = new Bundle();
        args.putStringArray(EXTRA,listPlayersName);
        args.putBoolean(EXTRA1,isInScoresFragment);
        args.putBoolean(EXTRA2,isFromMainActivity);
        args.putString(EXTRA3,currentDistrib);
        args.putIntegerArrayList(EXTRA4,scores);

        playerScoreFragment.setArguments(args);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

        scoresFragment = new ScoresFragment();
        //todo V0 voir si obligé ?
        Bundle argsscore = new Bundle();
        argsscore.putStringArray(EXTRA,listPlayersName);
        scoresFragment.setArguments(argsscore);
        replaceFragment(scoresFragment);

    }

    @Override
    public boolean onSettingsVerifNoms() {

        player1 = playerScoreFragment.getPlayer1();
        player2 = playerScoreFragment.getPlayer2();
        player3 = playerScoreFragment.getPlayer3();
        player4 = playerScoreFragment.getPlayer4();

        listPlayersName = new String[]{player1, player2, player3, player4};

        Bundle args = new Bundle();
        args.putStringArray(EXTRA,listPlayersName);
        settingsGameFragment.setArguments(args);

        if((player1.equals("")|| player2.equals("") || player3.equals("") || player4.equals(""))){ return false; }

        return true;
    }

    @Override
    public void onSettingsModeEquipeChoice() {

        DialogFragment dialog = new DialogModeEquipeFragment();
        dialog.show(getSupportFragmentManager(),"TAG");
    }


    //Fragments Boite de dialogue de choix de mode equipe

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        Toast.makeText(this, "Modifiez SVP les noms en haut de l'écran...", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        isInScoresFragment = true;
        isFromMainActivity = false;
        settingsGameFragment.saveSettingsPartieAnonyme();
        //todo V0 voir à enlever
        modeEquipe = ModeEquipe.MODE_EQUIPE_STATIQUE_ANONYME;
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(0);
        scores.add(0);

        teamScoreFragment = new TeamScoreFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(EXTRA4,scores);

        teamScoreFragment.setArguments(args);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, teamScoreFragment).commit();

        scoresFragment = new ScoresFragment();
        replaceFragment(scoresFragment);
    }

                         //Fragments Scores

    @Override
    public void onScoresDonneNullChoice() {

        DialogFragment dialog1 = new DialogDonneNullFragment();
        dialog1.show(getSupportFragmentManager(),"TAG");

    }

    @Override
    public void onScoreChangePreneur(int score1,int score2) {
        isInScoresFragment = true;
        isFromMainActivity = true;
        currentDistrib = playerScoreFragment.currentDistrib.getNomJoueur();
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(score1);
        scores.add(score2);

        playerScoreFragment = new PlayerScoreFragment();
        Bundle args = new Bundle();
        args.putStringArray(EXTRA,listPlayersName);
        args.putBoolean(EXTRA1,isInScoresFragment);
        args.putBoolean(EXTRA2,isFromMainActivity);
        args.putString(EXTRA3,currentDistrib);
        args.putIntegerArrayList(EXTRA4,scores);

        playerScoreFragment.setArguments(args);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

    }

    @Override
    public void onScoreDisplayScoreTotal(int score1, int score2) {
        isInScoresFragment = true;
        isFromMainActivity = false;
        currentDistrib = playerScoreFragment.currentDistrib.getNomJoueur();
        scores = new ArrayList<>();
        scores.add(score1);
        scores.add(score2);

        playerScoreFragment = new PlayerScoreFragment();
        Bundle args = new Bundle();
        args.putStringArray(EXTRA,listPlayersName);
        args.putBoolean(EXTRA1,isInScoresFragment);
        args.putBoolean(EXTRA2,isFromMainActivity);
        args.putString(EXTRA3,currentDistrib);
        args.putIntegerArrayList(EXTRA4,scores);

        playerScoreFragment.setArguments(args);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

    }

    @Override
    public void onScoreChangeScoreTotal(int score1, int score2) {

        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(score1);
        scores.add(score2);

        teamScoreFragment = new TeamScoreFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(EXTRA4,scores);

        teamScoreFragment.setArguments(args);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, teamScoreFragment).commit();

    }

    @Override
    public void onScoreDialogWinner() {
        DialogFragment dialog2 = new DialogWinnerFragment();
        dialog2.show(getSupportFragmentManager(),"TAG");
    }

    @Override
    public void onDialogWinnerPositiveClick(DialogFragment dialog) {

        Log.i(TAG, "onDialogWinnerPositiveClick: nouvelle partie");

        currentPartie = beloteSkorDb.partieDao().getLastPartie();

        if(currentPartie.getType().getModeEquipe().equals(ModeEquipe.MODE_EQUIPE_STATIQUE_NOMINATIF.toString())){
            Log.i(TAG, "onDialogWinnerPositiveClick1: nouvelle partie");

            isInScoresFragment = true;
            isFromMainActivity = false;
            currentDistrib = playerScoreFragment.currentDistrib.getNomJoueur();

            scores = new ArrayList<>();
            scores.add(0);
            scores.add(0);

            playerScoreFragment = new PlayerScoreFragment();
            Bundle args = new Bundle();
            args.putStringArray(EXTRA,listPlayersName);
            args.putBoolean(EXTRA1,isInScoresFragment);
            args.putBoolean(EXTRA2,isFromMainActivity);
            args.putString(EXTRA3,currentDistrib);
            args.putIntegerArrayList(EXTRA4,scores);

            playerScoreFragment.setArguments(args);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

            scoresFragment = new ScoresFragment();
            //todo V0 voir si obligé ?
            Bundle argsscore = new Bundle();
            argsscore.putStringArray(EXTRA,listPlayersName);
            scoresFragment.setArguments(argsscore);
            replaceFragment(scoresFragment);


        }else if(currentPartie.getType().getModeEquipe().equals(ModeEquipe.MODE_EQUIPE_STATIQUE_ANONYME.toString())){

            //todo V0 vérifier si le score déclaré ne marcherait pas
            ArrayList<Integer> scores = new ArrayList<>();
            scores.add(0);
            scores.add(0);

            currentDistrib=currentPartie.getPremierDistributeur().getNomJoueur();

            teamScoreFragment = new TeamScoreFragment();
            Bundle args = new Bundle();
            args.putIntegerArrayList(EXTRA4,scores);

            teamScoreFragment.setArguments(args);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_fragment_name_score, teamScoreFragment).commit();

            scoresFragment = new ScoresFragment();
            replaceFragment(scoresFragment);

        }

        Joueur lastDistrib = new Joueur(currentDistrib);

        Partie partie = new Partie(currentPartie.getType(),currentPartie.getTable(),lastDistrib,currentPartie.getSensJeu(),0,0,false,aujourdhui());

        MainActivity.beloteSkorDb.partieDao().insertPartie(partie);

    }

    @Override
    public void onDialogWinnerNegativeClick(DialogFragment dialog) {

        Log.i(TAG, "onDialogWinnerNegativeClick: fin de partie");

    }

    @Override
    public void onDialogDonnePositiveClick(DialogFragment dialog) {

        scoresFragment.setTotalScore();
        scoresFragment.upDateTotalScore();
        scoresFragment.testFinPartie();

    Partie lastPartie =beloteSkorDb.partieDao().getLastPartie();;

        if (!lastPartie.isPartieterminee()){
            scoresFragment.createDonne();
        }
    }

    @Override
    public void onDialogDonneNegativeClick(DialogFragment dialog) {


        Toast.makeText(this, "Veuillez modifier les scores de la donne, SVP...", Toast.LENGTH_SHORT).show();

    }


    //Fragments Player et scores

    @Override
    public void onPlayerScoreInteraction() {

    }

    //fragments team et scores
    @Override
    public void onTeamFragmentInteraction() {

    }

    //Fragments listes des parties des joueurs

    @Override
    public void onPlayersFragmentInteraction() {

    }


    //Fragments statistics

    @Override
    public void onStatisticsFragmentInteraction() {

    }

    //Méthode implémentée par le donne Adapter

    @Override
    public String[] onDonneAdapterPlayers() {

        return listPlayersName ;
    }

    @Override
    public void onDonneAdapterUpdateDonne(int numDonne) {

        scoresFragment.upDateCurrentDonne(numDonne);
    }

    @Override
    public void onDonneAdapterSetTotalScore() {
        scoresFragment.setTotalScore();
    }

    @Override
    public void onDonneAdapterDisplayScoreTotal() {
        int score1 = scoresFragment.getScoreTotalEquipe1();
        int score2 = scoresFragment.getScoreTotalEquipe2();

        scoresFragment.displayScoreTotal(score1,score2);
    }

    @Override
    public void onDonneAdapterUpDateTotalScore() {

        scoresFragment.upDateTotalScore();

    }

    @Override
    public void onDonneAdapterDisplayTotalScoreAnonyme() {

        int score1 = scoresFragment.getScoreTotalEquipe1();
        int score2 = scoresFragment.getScoreTotalEquipe2();

       // Log.i(TAG, "onDonneAdapterDisplayTotalScoreAnonyme: "+score1+score2);

        scoresFragment.displayChangeScoreTotal(score1,score2);
    }

    @Override
    public void onDonneAdapterTestFinPartie() {

        scoresFragment.testFinPartie();
    }


    //Autres méthodes

    public void replaceFragment (Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_main, fragment);
        transaction.commit();

    }

    //todo V0 ajouter les heures et minutes
    public String aujourdhui() {
        final Date date = new Date();
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }


}
