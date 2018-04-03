package com.skor.beloteskor.Model;

import android.arch.persistence.room.Embedded;

/**
 * Created by thierrycouilleault on 22/01/2018.
 */

public class Equipes {


    @Embedded(prefix = "A_")
    private Equipe equipeA;

    @Embedded(prefix = "B_")
    private Equipe equipeB;


// Constructeurs


    public Equipes(Equipe equipeA, Equipe equipeB) {
        this.equipeA = equipeA;
        this.equipeB = equipeB;
    }

    public Equipes(){
    }



    //Getter et setter


    public Equipe getEquipeA() {
        return equipeA;
    }

    public void setEquipeA(Equipe equipeA) {
        this.equipeA = equipeA;
    }

    public Equipe getEquipeB() {
        return equipeB;
    }

    public void setEquipeB(Equipe equipeB) {
        this.equipeB = equipeB;
    }
}
