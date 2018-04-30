package com.skor.beloteskor.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.skor.beloteskor.Adapters.DonneAdapter;
import com.skor.beloteskor.Model.DonneScore;
import com.skor.beloteskor.Model.DonneScoreDetails;
import com.skor.beloteskor.Model.ModeEquipe;
import com.skor.beloteskor.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SettingsGameFragment.OnSettingsGameFragmentListener,
        ScoresFragment.OnScoresFragmentInteractionListener, PlayersFragment.OnPlayersFragmentInteractionListener, StatisticsFragment.OnStatisticsFragmentInteractionListener,
        DialogModeEquipeFragment.DialogModeEquipeFragmentListener, PlayerScoreFragment.OnPlayerScoreFragmentListener, TeamScoreFragment.OnTeamFragmentInteractionListener, DonneAdapter.OnDonneAdapterListener{

    private android.support.v7.widget.Toolbar toolbar;
    private BottomNavigationView navigation;
    private FrameLayout flFragmentMain, flFragmentNameScore;

    private FragmentTransaction transaction;
    private SettingsGameFragment settingsGameFragment;
    private PlayerScoreFragment playerScoreFragment;

    public static final String EXTRA="com.skor.beloteskor.MESSAGE";



    private String player1, player2, player3, player4;
    private String[] listPlayersName = {"","","",""};
    private List<DonneScore> donnesScore = null;
    private ModeEquipe modeEquipe = null;

    public List<DonneScore> getDonnesScore() {
        return donnesScore;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //gestion de la Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BeloteSkor");

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
        Bundle argsinit = new Bundle();
        argsinit.putStringArray(EXTRA,listPlayersName);
        playerScoreFragment.setArguments(argsinit);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

        settingsGameFragment = new SettingsGameFragment();
        Bundle args=new Bundle();
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

        switch (item.getItemId()) {

            case R.id.preferences:

                return true;

            case R.id.app_bar_search:

                return true;

            case R.id.logout:

                return true;

            default:
            return super.onOptionsItemSelected(item);
        }
    }


                            //METHODES FRAGMENTS IMPLEMENTES

    //Fragments SettingsGame

    @Override
    public void onSettingsGameFragmentInteraction() {

        modeEquipe = ModeEquipe.MODE_EQUIPE_STATIQUE_NOMINATIF;

        playerScoreFragment = new PlayerScoreFragment();
        Bundle args = new Bundle();
        args.putStringArray(EXTRA,listPlayersName);
        playerScoreFragment.setArguments(args);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_name_score, playerScoreFragment).commit();

        ScoresFragment scoresFragment = new ScoresFragment();

        //todo voir si obligé ?
        Bundle argsscore=new Bundle();
        argsscore.putStringArray(EXTRA,listPlayersName);
        scoresFragment.setArguments(argsscore);
        replaceFragment(scoresFragment);

    }

    @Override
    public void onSettingsModeEquipeChoice() {

        DialogFragment dialog = new DialogModeEquipeFragment();
        dialog.show(getSupportFragmentManager(),"TAG");
    }

    @Override
    public boolean onSettingsVerifNoms() {

        player1 = playerScoreFragment.getPlayer1();
        player2 = playerScoreFragment.getPlayer2();
        player3 = playerScoreFragment.getPlayer3();
        player4 = playerScoreFragment.getPlayer4();

        listPlayersName = new String[]{player1, player2, player3, player4};

        Bundle args=new Bundle();
        args.putStringArray(EXTRA,listPlayersName);
        settingsGameFragment.setArguments(args);

        if((player1.equals("")|| player2.equals("") || player3.equals("") || player4.equals(""))){

            return false;
        }

        return true;
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


    //Fragments Player et scores

    @Override
    public void onPlayerScoreInteraction() {

    }

    //fragments team et scores
    @Override
    public void onTeamFragmentInteraction() {

    }

    //Fragments scores des donnes

    @Override
    public List<DonneScore> onScoresFragmentInteraction() {

        if (donnesScore == null) {

            donnesScore = new ArrayList<>();

            for (int i = 0; i < 3; i++) {

                DonneScoreDetails donneScoreDetails = new DonneScoreDetails(player3, player1, player2, player3, player4, player1, 2 * i, 162 - 2 * i) ;

                donnesScore.add(new DonneScore(i+1, donneScoreDetails.getScoreDonneEquipeA(), donneScoreDetails.getScoreDonneEquipeB()));



            }
        } else {


        }

        return donnesScore;

    }

    @Override
    public void onPressedAddDonnesBtn() {


        DonneScoreDetails donneScoreDetails = new DonneScoreDetails(player3, player1, player2, player3, player4, player1, 60, 102) ;

        donnesScore.add(new DonneScore(donnesScore.size(), donneScoreDetails.getScoreDonneEquipeA(), donneScoreDetails.getScoreDonneEquipeB()));

        ScoresFragment scoresFragment = new ScoresFragment();
        replaceFragment(scoresFragment);

    }

    //Fragments listes des parties des joueurs

    @Override
    public void onPlayersFragmentInteraction() {

    }


    //Fragments statistics

    @Override
    public void onStatisticsFragmentInteraction() {

    }


    //Autres méthodes

    public void replaceFragment (Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_main, fragment);
        transaction.commit();

    }

    @Override
    public String[] onDonneAdapterPlayers() {

        return listPlayersName;
    }
}
