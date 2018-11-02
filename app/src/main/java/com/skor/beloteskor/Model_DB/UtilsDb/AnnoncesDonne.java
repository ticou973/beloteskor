package com.skor.beloteskor.Model_DB.UtilsDb;

import com.skor.beloteskor.Model_DB.MainDb.Equipe;

public class AnnoncesDonne {

    // Variables d'instance

    private Equipe equipeAnnonces;
    private int nbTierce;
    private int nbCinquante;
    private int nbCent;
    private int nbCarreAutre;
    private boolean carreValet;
    private boolean carre9;

    //Méthodes constructeurs

    public AnnoncesDonne(){

    }

    public AnnoncesDonne(Equipe equipeAnnonces, int nbTierce, int nbCinquante, int nbCent, int nbCarreAutre, boolean carreValet, boolean carre9) {
        this.equipeAnnonces = equipeAnnonces;
        this.nbTierce = nbTierce;
        this.nbCinquante = nbCinquante;
        this.nbCent = nbCent;
        this.nbCarreAutre = nbCarreAutre;
        this.carreValet = carreValet;
        this.carre9 = carre9;
    }

    //Getter et Setter

    public Equipe getEquipeAnnonces() {
        return equipeAnnonces;
    }

    public void setEquipeAnnonces(Equipe equipeAnnonces) {
        this.equipeAnnonces = equipeAnnonces;
    }

    public int getNbTierce() {
        return nbTierce;
    }

    public void setNbTierce(int nbTierce) {
        this.nbTierce = nbTierce;
    }

    public int getNbCinquante() {
        return nbCinquante;
    }

    public void setNbCinquante(int nbCinquante) {
        this.nbCinquante = nbCinquante;
    }

    public int getNbCent() {
        return nbCent;
    }

    public void setNbCent(int nbCent) {
        this.nbCent = nbCent;
    }

    public int getNbCarreAutre() {
        return nbCarreAutre;
    }

    public void setNbCarreAutre(int nbCarreAutre) {
        this.nbCarreAutre = nbCarreAutre;
    }

    public boolean isCarreValet() {
        return carreValet;
    }

    public void setCarreValet(boolean carreValet) {
        this.carreValet = carreValet;
    }

    public boolean isCarre9() {
        return carre9;
    }

    public void setCarre9(boolean carre9) {
        this.carre9 = carre9;
    }
}
