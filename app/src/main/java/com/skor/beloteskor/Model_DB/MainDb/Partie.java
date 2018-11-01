package com.skor.beloteskor.Model_DB.MainDb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.skor.beloteskor.Model_DB.UtilsDb.SensJeu;
import com.skor.beloteskor.Model_DB.UtilsDb.Table;
import com.skor.beloteskor.Model_DB.UtilsDb.TypeDePartie;

/**
 * Created by thierrycouilleault on 13/11/2017.
 */
//todo voir pour utilisation de l'hértitage et du polymorphisme pour améliorer mon code (voir partie annonces, et sans annonces
    //todo rendre abstract cette class si héritage pour ne pas pouvoir l'implémenter et obliger les classes héritées à impléméneter les méthodes

@Entity
public class Partie {

    // Variables d'instance

    @PrimaryKey(autoGenerate = true)
    private int partieId;

    @Embedded
    private TypeDePartie type;

    @Embedded
    private Table table;

    @Embedded(prefix = "distrib_")
    private Joueur premierDistributeur;

    @ColumnInfo(name ="sens_jeu")
    private SensJeu sensJeu;

    @ColumnInfo(name ="score_equipeA")
    private int scoreEquipeA;

    @ColumnInfo(name="score_equipeB")
    private int scoreEquipeB;

    @ColumnInfo(name="etat_partie")
    private boolean partieterminee;



    //Méthodes constructeurs

    public Partie(TypeDePartie type, Table table, Joueur premierDistributeur, SensJeu sensJeu, int scoreEquipeA, int scoreEquipeB, boolean partieterminee) {
        this.type = type;
        this.table = table;
        this.premierDistributeur = premierDistributeur;
        this.sensJeu = sensJeu;
        this.scoreEquipeA = scoreEquipeA;
        this.scoreEquipeB = scoreEquipeB;
        this.partieterminee = partieterminee;
    }


    //Autres méthodes



    //Getter et Setter


    public int getPartieId() {
        return partieId;
    }

    public void setPartieId(int partieId) {
        this.partieId = partieId;
    }

    public TypeDePartie getType() {
        return type;
    }

    public void setType(TypeDePartie type) {
        this.type = type;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Joueur getPremierDistributeur() {
        return premierDistributeur;
    }

    public void setPremierDistributeur(Joueur premierDistributeur) {
        this.premierDistributeur = premierDistributeur;
    }

    public int getScoreEquipeA() {
        return scoreEquipeA;
    }

    public void setScoreEquipeA(int scoreEquipeA) {
        this.scoreEquipeA = scoreEquipeA;
    }

    public int getScoreEquipeB() {
        return scoreEquipeB;
    }

    public void setScoreEquipeB(int scoreEquipeB) {
        this.scoreEquipeB = scoreEquipeB;
    }

    public boolean isPartieterminee() {
        return partieterminee;
    }

    public void setPartieterminee(boolean partieterminee) {
        this.partieterminee = partieterminee;
    }

    public SensJeu getSensJeu() {
        return sensJeu;
    }

    public void setSensJeu(SensJeu sensJeu) {
        this.sensJeu = sensJeu;
    }
}
