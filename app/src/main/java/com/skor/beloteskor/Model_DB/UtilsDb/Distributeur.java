package com.skor.beloteskor.Model_DB.UtilsDb;

import com.skor.beloteskor.Model_DB.MainDb.Joueur;

/**
 * Created by thierrycouilleault on 13/11/2017.
 */

public class Distributeur extends Joueur {
    //Variables d'instance
    private Joueur premierDistributeur;
    private Joueur distributeurCourant;






    //getter et setter
    public Joueur getPremierDistributeur() {
        return premierDistributeur;
    }

    public void setPremierDistributeur(Joueur premierDistributeur) {
        this.premierDistributeur = premierDistributeur;
    }

    public Joueur getDistributeurCourant() {
        return distributeurCourant;
    }

    public void setDistributeurCourant(Joueur distributeurCourant) {
        this.distributeurCourant = distributeurCourant;
    }
}
