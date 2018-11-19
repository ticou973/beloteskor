package com.skor.beloteskor.Players;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.skor.beloteskor.R;

class PlayerViewHolder extends RecyclerView.ViewHolder {

   private TextView playerName,nbPartiesJouees,nbPartiesgagnees,pourcentagePartiesGagnees;


    public PlayerViewHolder(View itemView) {
        super(itemView);

        playerName=itemView.findViewById(R.id.tv_player_name);
        nbPartiesJouees=itemView.findViewById(R.id.tv_nb_parties_jouees);
        nbPartiesgagnees=itemView.findViewById(R.id.tv_nb_parties_gagnees);
        pourcentagePartiesGagnees=itemView.findViewById(R.id.tv_pourcentage_parties_gagnees);

    }


    public void setPlayerName(String name){
        playerName.setText(name);
    }

    public void setNbPartiesJouees(int nb){
        nbPartiesJouees.setText(String.valueOf(nb));
    }

    public void setNbPartiesgagnees(int nb){
        nbPartiesgagnees.setText(String.valueOf(nb));
    }

    public void setPourcentagePartiesGagnees(int pourcentage){

        String pourcentageText = "("+String.valueOf(pourcentage)+" %)";
        pourcentagePartiesGagnees.setText(pourcentageText);
    }
}


