package com.skor.beloteskor.Model;


public class DonneScore {

    private int scoreDonneA, scoreDonneB, numDonne;
    private boolean isExpanded;
    private String title;
    private DonneScoreDetails donneScoreDetails;

    public DonneScore(int numDonne, int scoreDonneA, int scoreDonneB) {

        this.numDonne = numDonne;
        this.scoreDonneA = scoreDonneA;
        this.scoreDonneB = scoreDonneB;

    }

    public DonneScore (int numDonne) {

        this.numDonne = numDonne;
    }

    //todo voir si utile
    @Override
    public String toString() {
        return title + String.valueOf(donneScoreDetails.getScoreDonneEquipeA());
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public int getScoreDonneA() {
        return scoreDonneA;
    }

    public void setScoreDonneA(int scoreDonneA) {
        this.scoreDonneA = scoreDonneA;
    }

    public int getScoreDonneB() {
        return scoreDonneB;
    }

    public void setScoreDonneB(int scoreDonneB) {
        this.scoreDonneB = scoreDonneB;
    }

    public int getNumDonne() {
        return numDonne;
    }

    public void setNumDonne(int numDonne) {
        this.numDonne = numDonne;
    }
}
