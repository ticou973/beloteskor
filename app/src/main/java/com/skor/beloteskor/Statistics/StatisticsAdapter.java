package com.skor.beloteskor.Statistics;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skor.beloteskor.Model_DB.MainDb.Partie;
import com.skor.beloteskor.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class StatisticsAdapter extends RecyclerView.Adapter<StatisticsViewHolder> {

    private List<Partie> parties;
    private Partie partie;
    private int numPartie, scoreEquipeA,scoreEquipeB;
    private String equipeA, equipeB, date, statut;

    public StatisticsAdapter(List<Partie> parties) {
        this.parties = parties;
    }

    @Override
    public StatisticsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_parties, parent, false);

        return new StatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder holder, int position) {

        partie=parties.get(position);

        numPartie = position+1;
        holder.setTextNumPartie(String.valueOf(numPartie));

        scoreEquipeA=partie.getScoreEquipeA();
        scoreEquipeB=partie.getScoreEquipeB();
        holder.setTextScoreEquipeA(String.valueOf(scoreEquipeA));
        holder.setTextScoreEquipeB(String.valueOf(scoreEquipeB));

        equipeA=partie.getTable().getEquipeA().getJoueur1().getNomJoueur()+" - "+ partie.getTable().getEquipeA().getJoueur2().getNomJoueur();
        equipeB=partie.getTable().getEquipeB().getJoueur1().getNomJoueur()+" - "+ partie.getTable().getEquipeB().getJoueur2().getNomJoueur();
        holder.setTextEquipeA(equipeA);
        holder.setTextEquipeB(equipeB);

        if(partie.isPartieterminee()){ statut ="terminée";
        }else{ statut ="en cours";
        }
        holder.setTextStatut(statut);

        date = aujourdhui();
        holder.setTextDate(date);

    }

    @Override
    public int getItemCount() {
        return parties.size();
    }

    public String aujourdhui() {
        final Date date = new Date();
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
}

