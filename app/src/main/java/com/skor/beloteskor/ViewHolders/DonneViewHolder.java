package com.skor.beloteskor.ViewHolders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.skor.beloteskor.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.graphics.Color.rgb;

public class DonneViewHolder extends GroupViewHolder{

    private TextView scoreDonneEquipeA, scoreDonneEquipeB, numDonne;
    private CardView cardViewDonne;
    private Button validationBtn;


    public DonneViewHolder(View itemView) {
        super(itemView);

        scoreDonneEquipeA =itemView.findViewById(R.id.score_donne_equipeA);
        scoreDonneEquipeB =itemView.findViewById(R.id.score_donne_equipeB);
        numDonne = itemView.findViewById(R.id.tv_numero_donne);
        cardViewDonne = itemView.findViewById(R.id.cardView_donne);
        validationBtn = itemView.findViewById(R.id.validation_score_btn);

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

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }


    private void animateExpand() {

        cardViewDonne.setBackgroundColor(rgb(76,175,80));

    }


    private void animateCollapse() {

        cardViewDonne.setBackgroundColor(rgb(255,255,255));

    }
}
