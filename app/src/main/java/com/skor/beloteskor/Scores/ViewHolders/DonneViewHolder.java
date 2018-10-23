package com.skor.beloteskor.Scores.ViewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.shawnlin.numberpicker.NumberPicker;
import com.skor.beloteskor.R;

import static android.graphics.Color.rgb;

public class DonneViewHolder extends RecyclerView.ViewHolder  {

    private TextView scoreDonneEquipeA, scoreDonneEquipeB, numDonne;
    private CardView cardViewDonne, cardViewDonneDetails;
    private Button validationBtn;

    //child

    private TextView player1Name, player2Name, player3Name, player4Name;
    private FrameLayout flLeft, flRight;
    private NumberPicker numberPicker;
    private ToggleButton capot_team1, capot_team2, belote_team1, belote_team2;
    private ImageView preneur_trefle, preneur_carreau, preneur_pique, preneur_coeur;


    public DonneViewHolder(View itemView) {
        super(itemView);

        scoreDonneEquipeA =itemView.findViewById(R.id.score_donne_equipeA);
        scoreDonneEquipeB =itemView.findViewById(R.id.score_donne_equipeB);
        numDonne = itemView.findViewById(R.id.tv_numero_donne);
        cardViewDonne = itemView.findViewById(R.id.cardView_donne);
        validationBtn = itemView.findViewById(R.id.validation_score_btn);

        //Child

        player1Name = itemView.findViewById(R.id.details_donne_player1_name);
        player2Name = itemView.findViewById(R.id.details_donne_player2_name);
        player3Name = itemView.findViewById(R.id.details_donne_player3_name);
        player4Name = itemView.findViewById(R.id.details_donne_player4_name);
        cardViewDonneDetails = itemView.findViewById(R.id.cardView_donne_details);
        flLeft = itemView.findViewById(R.id.fl_bckg_left);
        flRight = itemView.findViewById(R.id.fl_bckg_right);
        numberPicker = itemView.findViewById(R.id.numberPicker);
        capot_team1 = itemView.findViewById(R.id.capot_team1_btn);
        capot_team2 = itemView.findViewById(R.id.capot_team2_btn);
        belote_team1 = itemView.findViewById(R.id.belote_team1_btn);
        belote_team2 = itemView.findViewById(R.id.belote_team2_btn);
        preneur_carreau = itemView.findViewById(R.id.preneur_carreau);
        preneur_pique = itemView.findViewById(R.id.preneur_pique);
        preneur_trefle = itemView.findViewById(R.id.preneur_trefle);
        preneur_coeur = itemView.findViewById(R.id.preneur_coeur);

    }


                        //Méthodes du ViewHolder

    public void setVisibilityBtn (boolean visible, int numDonne){

        if (visible) {

            validationBtn.setVisibility(View.VISIBLE);
            validationBtn.setText(String.valueOf(numDonne));

        }else{

            validationBtn.setVisibility(View.INVISIBLE);
        }
    }

    public CardView getCardViewDonneDetails() {
        return cardViewDonneDetails;
    }

    public CardView getCardViewDonne() {
        return cardViewDonne;
    }


    public NumberPicker getNumberPicker() {
        return numberPicker;
    }

    public void expand(int numDonne) {
        getCardViewDonneDetails().setVisibility(View.VISIBLE);
        cardViewDonne.setBackgroundColor(rgb(76,175,80));
        setVisibilityBtn(true,numDonne);

    }


    public void collapse(int numDonne) {
        getCardViewDonneDetails().setVisibility(View.GONE);
        cardViewDonne.setBackgroundColor(rgb(255,255,255));
        setVisibilityBtn(false,numDonne);


    }

    public void animateNumberPicker(float x, float y) {

        numberPicker.animate()
                .x(x)
                .y(y)
                .setDuration(100)
                .start();

    }


    public void setColorPreneurCouleur (ImageView couleur, boolean focus) {

        int red, green, blue;

        if(focus) {

            red = 255;
            green = 235;
            blue = 59;

        }else{
            red = 128;
            green = 226;
            blue = 126;
        }

        couleur.setBackgroundColor(rgb(red,green,blue));



    }

    public void setColorPlayerPreneur (TextView preneur, boolean focus) {

        int red, green, blue;

        if(focus) {

            red = 255;
            green = 235;
            blue = 59;

        }else{
            red = 128;
            green = 226;
            blue = 126;
        }

        preneur.setBackgroundColor(rgb(red,green,blue));


    }

    public void setScoreEquipeA(int scoreEquipeA) {
        scoreDonneEquipeA.setText(String.valueOf(scoreEquipeA));
    }

    public int getScoreEquipeA(){

        int scoreA;

        scoreA = Integer.parseInt(scoreDonneEquipeA.getText().toString());

        return scoreA;
    }

    public void setScoreEquipeB(int scoreEquipeB) {

        scoreDonneEquipeB.setText(String.valueOf(scoreEquipeB));
    }

    public int getScoreEquipeB(){

        int scoreB;

        scoreB = Integer.parseInt(scoreDonneEquipeB.getText().toString());

        return scoreB;
    }


    public void setNumDonne (int numeroDonne) {

        numDonne.setText(String.valueOf(numeroDonne));
    }


    public void setPlayer1Name (String name){

        player1Name.setText(name);
    }

    public void setPlayer2Name (String name){

        player2Name.setText(name);
    }

    public void setPlayer3Name (String name){

        player3Name.setText(name);
    }

    public void setPlayer4Name (String name){

        player4Name.setText(name);
    }


    public void setBGColor (int color){

        cardViewDonne.setBackgroundColor(color);
    }

                        //GETTER ET SETTER

    public TextView getPlayer1Name() {
        return player1Name;
    }

    public TextView getPlayer2Name() {
        return player2Name;
    }

    public TextView getPlayer3Name() {
        return player3Name;
    }

    public TextView getPlayer4Name() {
        return player4Name;
    }

    public ToggleButton getCapot_team1() {
        return capot_team1;
    }

    public ToggleButton getCapot_team2() {
        return capot_team2;
    }

    public ToggleButton getBelote_team1() {
        return belote_team1;
    }

    public ToggleButton getBelote_team2() {
        return belote_team2;
    }

    public ImageView getPreneur_trefle() {
        return preneur_trefle;
    }

    public ImageView getPreneur_carreau() {
        return preneur_carreau;
    }

    public ImageView getPreneur_pique() {
        return preneur_pique;
    }

    public ImageView getPreneur_coeur() {
        return preneur_coeur;
    }



}
