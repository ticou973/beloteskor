package com.skor.beloteskor.Players;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skor.beloteskor.MainActivity;
import com.skor.beloteskor.Model_DB.MainDb.Joueur;
import com.skor.beloteskor.Model_DB.MainDb.Partie;
import com.skor.beloteskor.R;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder> {

    private List<Joueur> joueurs;
    private List<Partie> partiesJoueur, partiesGagneesJoueur;
    private String name, partenaire;
    private int nbPartiesJ, nbPartiesG;
    private int pourcentageG;

    private final static String TAG="coucou";


    public PlayerAdapter(List<Joueur> joueurs){
        this.joueurs=joueurs;

    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_players, parent, false);

        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {

        //nom du joueur
        name = joueurs.get(position).getNomJoueur();
        holder.setPlayerName(name);

        //nb de parties jouées et gagnées
        partiesJoueur=MainActivity.beloteSkorDb.partieDao().getPartiesByPlayerName(name);
        nbPartiesJ=partiesJoueur.size();
        holder.setNbPartiesJouees(nbPartiesJ);


        nbPartiesG=calculNbPartiesGagnees();
        holder.setNbPartiesgagnees(nbPartiesG);

        pourcentageG= (int) Math.round((double)nbPartiesG/(double)nbPartiesJ*100);

        holder.setPourcentagePartiesGagnees(pourcentageG);

        //partenaire privilégié
        partenaire="Alberto";
        holder.setPartenairePrivilegie(partenaire);
    }

    @Override
    public int getItemCount() {
        return joueurs.size();
    }

    private int calculNbPartiesGagnees() {

        int i =0;

        for (Partie partie: partiesJoueur) {

           if(partie.getScoreEquipeA()>partie.getScoreEquipeB()&&(partie.getTable().getEquipeA().getJoueur1().equals(name)||partie.getTable().getEquipeA().getJoueur2().equals(name))){

               i++;
           }else if(partie.getScoreEquipeB()>partie.getScoreEquipeA()&&(partie.getTable().getEquipeB().getJoueur1().equals(name)||partie.getTable().getEquipeB().getJoueur2().equals(name))){

               i++;
           }
        }
        return i;
    }
}
