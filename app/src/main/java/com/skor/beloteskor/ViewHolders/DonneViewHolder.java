package com.skor.beloteskor.ViewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.skor.beloteskor.R;

import static android.graphics.Color.rgb;

public class DonneViewHolder extends RecyclerView.ViewHolder  {

    private TextView scoreDonneEquipeA, scoreDonneEquipeB, numDonne;
    private CardView cardViewDonne, cardViewDonneDetails;
    private Button validationBtn;

    //child

    private TextView player1Name, player2Name, player3Name, player4Name;
    private Button belote;
    private FrameLayout flLeft, flRight;


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
        belote = itemView.findViewById(R.id.btn_belote);
        flLeft = itemView.findViewById(R.id.fl_bckg_left);
        flRight = itemView.findViewById(R.id.fl_bckg_right);

    }

    public void setScoreEquipeA(int scoreEquipeA) {
        scoreDonneEquipeA.setText(String.valueOf(scoreEquipeA));
    }

    public void setScoreEquipeB(int scoreEquipeB) {

        scoreDonneEquipeB.setText(String.valueOf(scoreEquipeB));
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

    public Button getBelote() {
        return belote;
    }

    public void expand(int numDonne) {
        cardViewDonne.setBackgroundColor(rgb(76,175,80));
        setVisibilityBtn(true,numDonne);

    }


    public void collapse(int numDonne) {

        cardViewDonne.setBackgroundColor(rgb(255,255,255));
        setVisibilityBtn(false,numDonne);

    }

    public void animateBelote(float x, float y) {

        belote.animate()
                .x(x)
                .y(y)
                .setDuration(100)
                .start();

    }



    public void setColorFlLeft(boolean focus) {

        int red, green, blue;
        float opacity;

        if(focus) {

            red = 76;
            green = 175;
            blue = 80;
            opacity = 1;

        }else{
            red = 128;
            green = 226;
            blue = 126;
            opacity = 0.3f;
        }

        flLeft.setBackgroundColor(rgb(red,green,blue));
        flLeft.setAlpha(opacity);

    }

    public void setColorFlRight(boolean focus) {

        int red, green, blue;

        if(focus) {

            red = 76;
            green = 175;
            blue = 80;

        }else{
            red = 128;
            green = 226;
            blue = 126;
        }

        flRight.setBackgroundColor(rgb(red,green,blue));

    }


}
