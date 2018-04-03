package com.skor.beloteskor.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by thierrycouilleault on 17/01/2018.
 */


@Entity
public class Joueur {

   @PrimaryKey(autoGenerate = true)
   private int joueurId;


    @ColumnInfo(name ="nom_joueur")
    private String nomJoueur;


    //Méthodes constructeurs

    public Joueur (){
    }

    public Joueur (String nomJoueur) {
        this.nomJoueur = nomJoueur;

    }


    //Autres méthodes



    //Getter et Setter


    public String getNomJoueur() {
        return nomJoueur;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public int getJoueurId() {
        return joueurId;
    }

    public void setJoueurId(int joueurId) {
        this.joueurId = joueurId;
    }
}



