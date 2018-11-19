package com.skor.beloteskor.Statistics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.skor.beloteskor.R;

class StatisticsViewHolder extends RecyclerView.ViewHolder {

    private TextView numPartie, statut, equipeA, equipeB, scoreEquipeA, scoreEquipeB, date;

    public StatisticsViewHolder(View itemView) {
        super(itemView);

        numPartie = itemView.findViewById(R.id.tv_numero_partie);
        statut=itemView.findViewById(R.id.tv_statut);
        equipeA = itemView.findViewById(R.id.tv_equipeA);
        equipeB= itemView.findViewById(R.id.tv_equipeB);
        scoreEquipeA=itemView.findViewById(R.id.tv_score_equipeA);
        scoreEquipeB=itemView.findViewById(R.id.tv_score_equipeB);
        date=itemView.findViewById(R.id.tv_date_partie);
    }

   public void setTextNumPartie(String numero){

        numPartie.setText("Partie N° "+ numero);

   }

   public void setTextStatut(String stat){

        statut.setText("Statut : "+stat);
   }

   public void setTextEquipeA(String eqA){

        equipeA.setText(eqA);
   }

    public void setTextEquipeB(String eqB){

        equipeB.setText(eqB);
    }


    public void setTextScoreEquipeA(String scEqA){

        scoreEquipeA.setText(scEqA);
    }

    public void setTextScoreEquipeB(String scEqB){

        scoreEquipeB.setText(scEqB);
    }

    public void setTextDate(String datePartie){

        date.setText("Date :"+datePartie);
    }
}
