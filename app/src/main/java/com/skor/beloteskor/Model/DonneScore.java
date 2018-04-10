package com.skor.beloteskor.Model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class DonneScore extends ExpandableGroup {

    private int scoreDonneA, scoreDonneB, numDonne;

    public DonneScore(String title, List items) {
        super(title, items);

        this.scoreDonneA =scoreDonneA;
        this.scoreDonneB=scoreDonneB;
        this.numDonne = numDonne;

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
