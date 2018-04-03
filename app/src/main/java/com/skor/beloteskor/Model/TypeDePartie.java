package com.skor.beloteskor.Model;

import android.arch.persistence.room.Ignore;

/**
 * Created by thierrycouilleault on 16/11/2017.
 */

public class TypeDePartie {

    // Variables d'instance

    private String typeJeu=TypeJeu.POINTS.toString();
    private String typeAnnonce = TypeAnnonce.SANS_ANNONCE.toString();
    private int nbPoints = 1001;
    private int nbDonnes;


    //Variables statiques


    //Méthodes constructeurs


     public TypeDePartie() {

    }

    @Ignore
    public TypeDePartie(String typeJeu, String typeAnnonce, int nbPoints, int nbDonnes) {
        this.typeJeu = typeJeu;
        this.typeAnnonce = typeAnnonce;
        this.nbPoints = nbPoints;
        this.nbDonnes = nbDonnes;
    }

    //Autres méthodes

    //Getter et Setter


    public String getTypeJeu() {
        return typeJeu;
    }

    public void setTypeJeu(String typeJeu) {
        this.typeJeu = typeJeu;
    }

    public String getTypeAnnonce() {
        return typeAnnonce;
    }

    public void setTypeAnnonce(String typeAnnonce) {
        this.typeAnnonce = typeAnnonce;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public int getNbDonnes() {
        return nbDonnes;
    }

    public void setNbDonnes(int nbDonnes) {
        this.nbDonnes = nbDonnes;
    }
}
