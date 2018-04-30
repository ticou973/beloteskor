package com.skor.beloteskor.ViewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.skor.beloteskor.R;

import static android.graphics.Color.rgb;

public class DonneViewHolder extends RecyclerView.ViewHolder {

    private TextView scoreDonneEquipeA, scoreDonneEquipeB, numDonne;
    private CardView cardViewDonne, cardViewDonneDetails;
    private Button validationBtn;

    private String message;


    //child

    private TextView player1Name, player2Name, player3Name, player4Name;
    private EditText essai;
    private String name;


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
        essai = itemView.findViewById(R.id.et_essai);
        cardViewDonneDetails = itemView.findViewById(R.id.cardView_donne_details);

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


    public String getNameEssai (){

        name = essai.getText().toString();

        return name;

    }

    public void setNameEssai (String name){

        essai.setText(name);
    }

    public EditText getEssai() {
        return essai;
    }

    public void setEssai(EditText essai) {
        this.essai = essai;
    }

    public void setBGColor (int color){

        cardViewDonne.setBackgroundColor(color);
    }

    public void setVisibilityBtn (boolean visible){

        if (visible) {

            validationBtn.setVisibility(View.VISIBLE);

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

    public void expand() {
        cardViewDonne.setBackgroundColor(rgb(76,175,80));

    }


    public void collapse() {

        cardViewDonne.setBackgroundColor(rgb(255,255,255));

    }



}
