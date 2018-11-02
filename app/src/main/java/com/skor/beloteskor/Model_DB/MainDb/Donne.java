package com.skor.beloteskor.Model_DB.MainDb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.skor.beloteskor.Model_DB.UtilsDb.AnnoncesDonne;
import com.skor.beloteskor.Model_DB.UtilsDb.Couleur;

/**
 * Created by thierrycouilleault on 13/11/2017.
 */

@Entity (foreignKeys = @ForeignKey(entity = Partie.class,
        parentColumns = "partieId",
        childColumns = "num_partie"),indices = {@Index(value = {"num_partie"})})
public class Donne  {

    @PrimaryKey (autoGenerate = true)
    private int donneId;

    @ColumnInfo (name ="num_partie")
    private int partieId;


    @ColumnInfo (name = "num_donne_partie")
    private int numDonne;

    @Embedded
    private Joueur preneur;

    @ColumnInfo(name="couleur")
    private Couleur couleur;

    @Embedded(prefix = "belote_")
    private Equipe belote;

    @Embedded(prefix = "capot_")
    private Equipe capot;

    @Embedded(prefix ="annonces_")
    private AnnoncesDonne annoncesDonne;

    @ColumnInfo (name = "score_equipeA")
    private int score1;

    @ColumnInfo (name = "score_equipeB")
    private int score2;



    public Donne() {
    }


    public Donne(int partieId, int numDonne, Joueur preneur, Couleur couleur, Equipe belote, Equipe capot, int score1, int score2) {
        this.partieId = partieId;
        this.numDonne = numDonne;
        this.preneur = preneur;
        this.couleur = couleur;
        this.belote = belote;
        this.capot = capot;
        this.score1 = score1;
        this.score2 = score2;
    }


    public Donne(int partieId, int numDonne, Couleur couleur) {
        this.partieId = partieId;
        this.numDonne = numDonne;
        this.couleur = couleur;
    }


    public Donne(int partieId, int numDonne) {
        this.partieId = partieId;
        this.numDonne = numDonne;
    }

    public Donne(int partieId, int numDonne, Joueur preneur, int score1, int score2) {
        this.partieId = partieId;
        this.numDonne = numDonne;
        this.preneur = preneur;
        this.score1 = score1;
        this.score2 = score2;
    }


    //getter et setter


    public int getDonneId() {
        return donneId;
    }

    public void setDonneId(int donneId) {
        this.donneId = donneId;
    }

    public int getPartieId() {
        return partieId;
    }

    public void setPartieId(int partieId) {
        this.partieId = partieId;
    }

    public int getNumDonne() {
        return numDonne;
    }

    public void setNumDonne(int numDonne) {
        this.numDonne = numDonne;
    }

    public Joueur getPreneur() {
        return preneur;
    }

    public void setPreneur(Joueur preneur) {
        this.preneur = preneur;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public Equipe getBelote() {
        return belote;
    }

    public void setBelote(Equipe belote) {
        this.belote = belote;
    }

    public Equipe getCapot() {
        return capot;
    }

    public void setCapot(Equipe capot) {
        this.capot = capot;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }
}
