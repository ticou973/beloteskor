package com.skor.beloteskor.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by thierrycouilleault on 13/11/2017.
 */


@Entity
public class Equipe {
    //Variables d'instance

    @PrimaryKey(autoGenerate = true)
    private int EquipeId;

    @ColumnInfo(name="nom_equipe")
    private String nomEquipe;

    @ColumnInfo(name="nom_joueur1")
    private String nomjoueur1;

    @ColumnInfo(name="nom_joueur2")
    private String nomJoueur2;


    //Méthodes constructeurs

    public Equipe() {}

    public Equipe(String nomEquipe, String nomjoueur1, String nomJoueur2) {
        this.nomEquipe = nomEquipe;
        this.nomjoueur1 = nomjoueur1;
        this.nomJoueur2 = nomJoueur2;
    }

    public Equipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    //Autres méthodes

    //Getter et Setter


    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public String getNomjoueur1() {
        return nomjoueur1;
    }

    public void setNomjoueur1(String nomjoueur1) {
        this.nomjoueur1 = nomjoueur1;
    }

    public String getNomJoueur2() {
        return nomJoueur2;
    }

    public void setNomJoueur2(String nomJoueur2) {
        this.nomJoueur2 = nomJoueur2;
    }

    public int getEquipeId() {
        return EquipeId;
    }

    public void setEquipeId(int equipeId) {
        EquipeId = equipeId;
    }
}
