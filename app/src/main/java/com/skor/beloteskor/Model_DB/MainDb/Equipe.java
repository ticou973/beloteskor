package com.skor.beloteskor.Model_DB.MainDb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
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

    @Embedded(prefix = "1_")
    private Joueur joueur1;

    @Embedded(prefix = "2_")
    private Joueur Joueur2;



    //Méthodes constructeurs

    public Equipe() {}

    public Equipe(String nomEquipe, Joueur joueur1, Joueur joueur2) {
        this.nomEquipe = nomEquipe;
        this.joueur1 = joueur1;
        this.Joueur2 = joueur2;
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

    public Joueur getJoueur1() {
        return joueur1;
    }

    public void setJoueur1(Joueur joueur1) {
        this.joueur1 = joueur1;
    }

    public Joueur getJoueur2() {
        return Joueur2;
    }

    public void setJoueur2(Joueur joueur2) {
        Joueur2 = joueur2;
    }

    public int getEquipeId() {
        return EquipeId;
    }

    public void setEquipeId(int equipeId) {
        EquipeId = equipeId;
    }
}
