package com.skor.beloteskor.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.skor.beloteskor.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class DonneViewHolder extends GroupViewHolder{

    private TextView scoreDonneEquipeA, scoreDonneEquipeB, numDonne;

    public DonneViewHolder(View itemView) {
        super(itemView);

        scoreDonneEquipeA =itemView.findViewById(R.id.score_donne_equipeA);
        scoreDonneEquipeB =itemView.findViewById(R.id.score_donne_equipeB);
        numDonne = itemView.findViewById(R.id.tv_numero_donne);
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
}
