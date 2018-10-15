package com.skor.beloteskor.Model;

public class DonneScoreDetails {

    private String preneurName ="";
    private String player1Name, player2Name, player3Name, player4Name, belote="";
    private int scoreDonneEquipeA=0, scoreDonneEquipeB=0;

    public DonneScoreDetails(String preneurName, String player1Name, String player2Name, String player3Name, String player4Name, String belote, int scoreDonneEquipeA, int scoreDonneEquipeB) {
        this.preneurName = preneurName;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player3Name = player3Name;
        this.player4Name = player4Name;
        this.belote = belote;
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

    public String getBelote() {
        return belote;
    }

    public void setBelote(String belote) {
        this.belote = belote;
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