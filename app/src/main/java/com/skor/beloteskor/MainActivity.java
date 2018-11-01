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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.skor.beloteskor.Model_DB.MainDb.AppDatabase;
import com.skor.beloteskor.Model_DB.MainDb.Donne;
import com.skor.beloteskor.Model_DB.UtilsDb.ModeEquipe;
import com.skor.beloteskor.Players.PlayersFragment;
import com.skor.beloteskor.Scores.Adapters.DonneAdapter;
import com.skor.beloteskor.Scores.DialogDonneNullFragment;
import com.skor.beloteskor.Scores.DialogModeEquipeFragment;
import com.skor.beloteskor.Scores.PlayerScoreFragment;
import com.skor.beloteskor.Scores.ScoresFragment;
import com.skor.beloteskor.Scores.SettingsGameFragment;
import com.skor.beloteskor.Scores.TeamScoreFragment;
import com.skor.beloteskor.Statistics.StatisticsFragment;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SettingsGameFragment.OnSettingsGameFragmentListener, PlayersFragment.OnPlayersFragmentInteractionListener, StatisticsFragment.OnStatisticsFragmentInteractionListener,
        DialogModeEquipeFragment.DialogModeEquipeFragmentListener, PlayerScoreFragment.OnPlayerScoreFragmentListener, TeamScoreFragment.OnTeamFragmentInteractionListener, DonneAdapter.OnDonneAdapterListener, ScoresFragment.OnScoresFragmentInteractionListener, DialogDonneNullFragment.DialogDonneNullFragmentListener{

    private android.support.v7.widget.Toolbar toolbar;
    private BottomNavigationView navigation;
    private FrameLayout flFragmentMain, flFragmentNameScore;

    private FragmentTransaction transaction;
    private SettingsGameFragment settingsGameFragment;
    private PlayerScoreFragment playerScoreFragment;
    private ScoresFragment scoresFragment;

    public static final String EXTRA="com.skor.beloteskor.MESSAGE";
    public static final String EXTRA1="com.skor.beloteskor.MESSAGE1";
    public static final String EXTRA2="com.skor.beloteskor.MESSAGE2";
    public static final String EXTRA3="com.skor.beloteskor.MESSAGE3";



    private String player1, player2, player3, player4;
    private String[] listPlayersName={"","","",""};
    private List<Donne> donnes = null;
    private ModeEquipe modeEquipe = null;

    public static AppDatabase beloteSkorDb;

    //todo faire un splash
    //todo faire un icon d'application
    //todo Faire du beau code en en mettant des méthodes pour tous les endroits où j'ai mis des titres
    //todo vérifier qu'il y ait besoin d'instancier le fragment en premier avec la navigation
    //todo récupérer les contextes dans tous les fragments avec le onAttach des fragments en déclarant context au départ
    //todo mettre une methode newinstance dans le fragment pour éviter de créer cette instance dans le main instance, peut être fait avec les mlistener
    //todo Changer le modèle par un modèleView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gestion de la DB : instance de la DB

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


        //gestion de la bottom navigation view
        navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.scores);

        //Navigation dans la Bottom view
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.players:
                        PlayersFragment playersFragment = new PlayersFragment();
                        replaceFragment(playersFragment);
                        return true;

                    case R.id.scores:
                        ScoresFragment scoresFragment = new ScoresFragment();
                        replaceFragment(scoresFragment);
                        return true;

                    case R.id.statistics:
                        StatisticsFragment statisticsFragment = new StatisticsFragment();
                        replaceFragment(statisticsFragment);
                        return true;

                    default:
                        Toast.makeText(MainActivity.this, "default", Toast.LENGTH_SHORT).show();
                        return false;
                }
            }
        });
                                    //FRAGMENTS

        flFragmentMain = findViewById(R.id.fl_fragment_main);
        flFragmentNameScore = findViewById(R.id.fl_fragment_name_score);

        //Lancement du fragment de settings

        playerScoreFragment = new PlayerScoreFragment();
        Boolean isInScoresFragment = false;
        Boolean isFromMainActivity = false;
        Bundle argsinit = new Bundle();
        argsinit.putStringArray(EXTRA,listPlayersName);
        argsinit.putBoolean(EXTRA1,isInScoresFragment);
        argsinit.putBoolean(EXTRA2,isFromMainActivity);
        playerScoreFragment.setArguments(argsinit);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

        settingsGameFragment = new SettingsGameFragment();
        Bundle args = new Bundle();
        args.putStringArray(EXTRA,listPlayersName);
        settingsGameFragment.setArguments(args);
        replaceFragment(settingsGameFragment);

    }


                        //MENU OPTIONS

    //Méthode qui inflate le menu d'option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);

        return true;
    }


    //todo menu options
  //Méthode qui gère la sélection de le menu de la Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//todo prévoir un onglet nouvelle partie (à la place de search ?

        switch (item.getItemId()) {

            case R.id.preferences:

                return true;

            case R.id.infos_donne:

                return true;

            case R.id.app_bar_search:

                return true;

            case R.id.logout:

                return true;

                //todo penser à retirer ces 2 menus plus tard et dands le menu.xml

            case R.id.reset_db:

                MainActivity.beloteSkorDb.joueurDao().deleteAll();

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

        //todo vérifier si modeEquipe intéressant ici ou à supprimer
        modeEquipe = ModeEquipe.MODE_EQUIPE_STATIQUE_NOMINATIF;
        Boolean isInScoresFragment = true;
        Boolean isFromMainActivity = false;

        playerScoreFragment = new PlayerScoreFragment();
        Bundle args = new Bundle();
        args.putStringArray(EXTRA,listPlayersName);
        args.putBoolean(EXTRA1,isInScoresFragment);
        args.putBoolean(EXTRA2,isFromMainActivity);

        playerScoreFragment.setArguments(args);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

        scoresFragment = new ScoresFragment();
        //todo voir si obligé ?
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

        if((player1.equals("")|| player2.equals("") || player3.equals("") || player4.equals(""))){

            return false;
        }

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

        TeamScoreFragment teamScoreFragment = new TeamScoreFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, teamScoreFragment).commit();

        ScoresFragment scoresFragment = new ScoresFragment();
        modeEquipe = ModeEquipe.MODE_EQUIPE_STATIQUE_ANONYME;
        replaceFragment(scoresFragment);
    }

                         //Fragments Scores

    @Override
    public void onScoresDonneNullChoice() {

        DialogFragment dialog1 = new DialogDonneNullFragment();
        dialog1.show(getSupportFragmentManager(),"TAG");

    }

    @Override
    public void onScoreChangePreneur() {

        Boolean isInScoresFragment = true;
        Boolean isFromMainActivity = true;
        String currentDistrib = playerScoreFragment.currentDistrib.getNomJoueur();

        playerScoreFragment = new PlayerScoreFragment();
        Bundle args = new Bundle();
        args.putStringArray(EXTRA,listPlayersName);
        args.putBoolean(EXTRA1,isInScoresFragment);
        args.putBoolean(EXTRA2,isFromMainActivity);
        args.putString(EXTRA3,currentDistrib);

        playerScoreFragment.setArguments(args);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

    }

    @Override
    public void onDialogDonnePositiveClick(DialogFragment dialog) {

        scoresFragment.CreateDonne();

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


    //Autres méthodes

    public void replaceFragment (Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_main, fragment);
        transaction.commit();

    }

}
