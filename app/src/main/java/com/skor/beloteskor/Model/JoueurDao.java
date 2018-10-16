package com.skor.beloteskor.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by thierrycouilleault on 23/01/2018.
 */

@Dao
public interface JoueurDao {

    @Query("SELECT * FROM joueur")
    LiveData<List<Joueur>> getAllJoueurs();


    @Query("SELECT * FROM joueur WHERE joueurId IN (:joueurId)")
    LiveData<Joueur> loadJoueurById(int joueurId);


    @Query("SELECT * FROM joueur WHERE nom_joueur IN (:nomJoueur)")
    LiveData<Joueur> loadJoueurByName(String nomJoueur);

    @Insert
    void insertAll(Joueur joueur);
}
