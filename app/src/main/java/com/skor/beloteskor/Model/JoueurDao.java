package com.skor.beloteskor.Model;

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
    List<Joueur> getAllJoueurs();


    @Query("SELECT * FROM joueur WHERE joueurId IN (:joueurId)")
    Joueur loadJoueurById(int joueurId);


    @Query("SELECT * FROM joueur WHERE nom_joueur IN (:nomJoueur)")
    Joueur loadJoueurByName(String nomJoueur);

    @Insert
    void insertAll(Joueur joueur);
}
