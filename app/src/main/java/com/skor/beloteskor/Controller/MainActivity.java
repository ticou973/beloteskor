package com.skor.beloteskor.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skor.beloteskor.R;

public class MainActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private BottomNavigationView navigation;
    private EditText yourName, yourPartnerName, onYourLeftName, onYourRightName;
    private TextView totalScoreA, totalScoreB;
    private Button newGameBtn;
    private ImageView triangleView;

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

                        Toast.makeText(MainActivity.this, "Players", Toast.LENGTH_SHORT).show();

                        return true;

                    case R.id.scores:

                        Toast.makeText(MainActivity.this, "Scores", Toast.LENGTH_SHORT).show();

                        return true;

                    case R.id.statistics:

                        Toast.makeText(MainActivity.this, "Statistics", Toast.LENGTH_SHORT).show();

                        return true;

                    default:
                        Toast.makeText(MainActivity.this, "default", Toast.LENGTH_SHORT).show();
                        return false;

                }

            }
        });

        //gestion de la barre des scores
        yourName = findViewById(R.id.et_you);
        yourPartnerName = findViewById(R.id.et_your_partner);
        onYourLeftName = findViewById(R.id.et_on_your_left);
        onYourRightName = findViewById(R.id.et_on_your_right);
        totalScoreA = findViewById(R.id.score_total_equipeA);
        totalScoreB = findViewById(R.id.score_total_equipeB);

        triangleView = findViewById(R.id.triangleView);
        triangleView.setVisibility(View.INVISIBLE);


        newGameBtn = findViewById(R.id.new_game_btn);
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //disparition et désactivation du bouton de nouvelle partie
                newGameBtn.setVisibility(View.INVISIBLE);
                newGameBtn.setEnabled(false);

            }
        });


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
}
