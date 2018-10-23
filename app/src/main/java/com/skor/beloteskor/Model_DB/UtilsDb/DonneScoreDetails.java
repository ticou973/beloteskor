package com.skor.beloteskor.Model_DB.UtilsDb;

public class DonneScoreDetails {

    private String preneurName;
    private String player1Name, player2Name, player3Name, player4Name;
    private boolean belote, capot;
    private int scoreDonneEquipeA, scoreDonneEquipeB;

    public DonneScoreDetails(String preneurName, String player1Name, String player2Name, String player3Name, String player4Name, boolean belote, boolean capot, int scoreDonneEquipeA, int scoreDonneEquipeB) {
        this.preneurName = preneurName;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player3Name = player3Name;
        this.player4Name = player4Name;
        this.belote = belote;
        this.capot = capot;
        this.scoreDonneEquipeA = scoreDonneEquipeA;
        this.scoreDonneEquipeB = scoreDonneEquipeB;
    }

    //todo voir si utile
    @Override
    public String toString() {
        return String.valueOf(getScoreDonneEquipeA()) + " " + String.valueOf(getScoreDonneEquipeB());
    }

    public String getPreneurName() {
        return preneurName;
    }

    public void setPreneurName(String preneurName) {
        this.preneurName = preneurName;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getPlayer3Name() {
        return player3Name;
    }

    public void setPlayer3Name(String player3Name) {
        this.player3Name = player3Name;
    }

    public String getPlayer4Name() {
        return player4Name;
    }

    public void setPlayer4Name(String player4Name) {
        this.player4Name = player4Name;
    }

    public boolean isBelote() {
        return belote;
    }

    public void setBelote(boolean belote) {
        this.belote = belote;
    }

    public boolean isCapot() {
        return capot;
    }

    public void setCapot(boolean capot) {
        this.capot = capot;
    }

    public int getScoreDonneEquipeA() {
        return scoreDonneEquipeA;
    }

    public void setScoreDonneEquipeA(int scoreDonneEquipeA) {
        this.scoreDonneEquipeA = scoreDonneEquipeA;
    }

    public int getScoreDonneEquipeB() {
        return scoreDonneEquipeB;
    }

    public void setScoreDonneEquipeB(int scoreDonneEquipeB) {
        this.scoreDonneEquipeB = scoreDonneEquipeB;
    }
}