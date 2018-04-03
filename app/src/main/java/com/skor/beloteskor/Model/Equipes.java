package com.skor.beloteskor.Model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;

/**
 * Created by thierrycouilleault on 22/01/2018.
 */

public class Equipes {

    @Embedded(prefix = "A_")
    Equipe equipeA;

    @Embedded(prefix = "B_")
    Equipe equipeB;





// Constructeurs

    public Equipes(){
    }

    @Ignore
    public Equipes(Equipe equipeA, Equipe equipeB) {
        this.equipeA = equipeA;
        this.equipeB = equipeB;
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
