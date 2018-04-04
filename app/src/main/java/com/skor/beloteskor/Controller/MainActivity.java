package com.skor.beloteskor.Controller;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.skor.beloteskor.R;

//todo faire attention à l'impléme,tation de l'interface qui répète 2 fois la même méthode de Callback
public class MainActivity extends AppCompatActivity implements SettingsGameFragment.OnSettingsGameFragmentListener, ScoresFragment.OnFragmentInteractionListener, PlayersFragment.OnFragmentInteractionListener, StatisticsFragment.OnFragmentInteractionListener, DialogModeEquipeFragment.DialogModeEquipeFragmentListener{

    private android.support.v7.widget.Toolbar toolbar;
    private BottomNavigationView navigation;


    private FrameLayout flFragments;

    private FragmentTransaction transaction;

    public static final String EXTRA="com.skor.beloteskor.MESSAGE";

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
        //Navigation dans la Bootom view
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.players:
                        PlayersFragment playersFragment = new PlayersFragment();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fl_fragment, playersFragment).commit();
                        return true;

                    case R.id.scores:
                        ScoresFragment scoresFragment = new ScoresFragment();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fl_fragment, scoresFragment).commit();
                        return true;

                    case R.id.statistics:
                        StatisticsFragment statisticsFragment = new StatisticsFragment();
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fl_fragment, statisticsFragment).commit();
                        return true;

                    default:
                        Toast.makeText(MainActivity.this, "default", Toast.LENGTH_SHORT).show();
                        return false;
                }
            }
        });






        flFragments = findViewById(R.id.fl_fragment);


        //Lancement du fragment de settings
        SettingsGameFragment settingsGameFragment = new SettingsGameFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment, settingsGameFragment).commit();


    }

    //Méthode qui inflate le menu d'option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);

        return true;
    }


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

    //Méthodes des fragments implémentés

    @Override
    public void onSettingsGameFragmentInteraction() {

        ScoresFragment scoresFragment = new ScoresFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment, scoresFragment).commit();

    }

    @Override
    public void onSettingsModeEquipeChoice() {

        DialogFragment dialog = new DialogModeEquipeFragment();
        dialog.show(getSupportFragmentManager(),"TAG");


    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {



    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {


    }
}
